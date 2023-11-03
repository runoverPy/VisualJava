package com.visualjava.vm;

import com.visualjava.invoke.ExecutionContext;
import com.visualjava.invoke.Executor;
import com.visualjava.types.VMReference;
import com.visualjava.types.VMType;
import com.visualjava.vm.events.ThreadEventsListener;

import java.util.LinkedList;
import java.util.Queue;

public class VMThread implements Runnable {
    private final String name;
    private final boolean daemon;
    private final VMStack stack;
    private final VMRuntime runtime;
    private final ThreadEventsListener threadListener;

    private volatile int cycleFrequency = 1;
    private final ThreadTimer timer = new ThreadTimer();

    public VMThread(VMRuntime runtime, String name, VMMethod runMethod, VMType[] args, boolean daemon) {
        this.runtime = runtime;
        this.daemon = daemon;
        this.stack = new VMStack();
        this.name = name;
        this.threadListener = runtime.getRuntimeListener().makeThreadListener(this);
        VMFrame rootFrame = new VMFrame(runMethod, args, this);
        stack.pushFrame(rootFrame);
        threadListener.onFramePush(rootFrame);
        Thread innerThread = new Thread(this);
        innerThread.start();
    }

    public ThreadEventsListener getThreadListener() {
        return threadListener;
    }

    @Override
    public void run() {
        runtime.onThreadStart(this);
        while (!stack.isEmpty() && timer.waitMillis(1000 / this.cycleFrequency)) doCycle();
        runtime.onThreadDeath(this);
    }

    public void togglePause() {
        timer.togglePause();
    }

    public void killThread() {
        timer.killThread();
    }

    public void setCycleFrequency(int cycleFrequency) {
        System.out.println("setting cycle frequency");
        this.cycleFrequency = cycleFrequency;
        threadListener.onFreqChange(cycleFrequency);
    }

    public int getCycleFrequency() {
        return this.cycleFrequency;
    }

    public void doCycle() {
        VMFrame currentFrame = stack.peekFrame();

        VMReference throwable;
        if ((throwable = currentFrame.checkForThrowable()) != null) {
            threadListener.onFramePop(currentFrame);
            stack.popFrame();
            stack.peekFrame().setThrowable(throwable);
            return; // exception found and propagated. No more actions will be taken this cycle
        }

        ExecutionContext context = new ExecutionContext(runtime, currentFrame);
        Executor.execute(context);
        currentFrame.onInstrExec(context);
        threadListener.onInstrExec(context);

        if (currentFrame.checkForReturnValue()) {
            VMType returnValue = currentFrame.getReturnValue();
            threadListener.onFramePop(currentFrame);
            stack.popFrame();
            if (returnValue != null) {
                stack.peekFrame().pshStack(returnValue);
            } else return;
        }

        VMMethod invokedMethod;
        if ((invokedMethod = currentFrame.getInvokedMethod()) != null) {
            VMType[] args = new VMType[invokedMethod.getNArgs()];
            for (int i = args.length - 1; i >= 0; i--) {
                args[i] = currentFrame.popStack();
            }
            VMFrame nextFrame = new VMFrame(invokedMethod, args, this);
            threadListener.onFramePush(nextFrame);
            stack.pushFrame(nextFrame);
        }
    }

    public void initClass(String className) { // TODO: 29.04.23 replace className with appropriate type

    }

    public boolean isDaemon() {
        return daemon;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "VMThread \"" + name + "\"";
    }

    private static class ThreadTimer {
        private final Queue<Event> eventsQueue;
        private volatile boolean killed;
        private volatile boolean paused;

        public ThreadTimer() {
            this.eventsQueue = new LinkedList<>();
            this.killed = false;
        }

        /**
         * This method pauses the Thread in such a way, that the total time blocked is equal to the sum of time the
         * thread was paused plus the time to be awaited.
         */
        public void togglePause() {
            synchronized (eventsQueue) {
                eventsQueue.add(Event.TOGGLE);
                eventsQueue.notify();
            }
        }

        public void killThread() {
            synchronized (eventsQueue) {
                eventsQueue.add(Event.KILL);
                eventsQueue.notify();
            }
        }

        /**
         * Method blocks the thread, and waits at until the timer is given an additional input, but at most `millis` milliseconds.
         * @param millis the maximum amount of time this method may block thread execution
         * @return if the thread resumed execution normally, if false: thread was signalled to be killed
         */
        public boolean waitMillis(long millis) {
            if (killed) return false;
            long elapsed = 0;
            long start = System.currentTimeMillis();
            long timeAtPause = 0;

            synchronized (eventsQueue) {
                while (!eventsQueue.isEmpty()) {
                    Event pastEvent = eventsQueue.remove();
                    switch (pastEvent) {
                        case KILL -> {
                            killed = true;
                            return false;
                        }
                        case TOGGLE -> paused = !paused;
                    }
                }
                while (true) {
                    try {
                        if (paused) {
                            eventsQueue.wait();
                        } else {
                            if (elapsed > millis) return true;
                            eventsQueue.wait(millis - elapsed);
                        }
                    } catch (InterruptedException ie) {
                        return true;
                    }
                    if (eventsQueue.isEmpty()) return true;
                    Event event = eventsQueue.remove();
                    switch (event) {
                        case KILL -> {
                            return false;
                        }
                        case TOGGLE -> {
                            if (paused) {
                                start = timeAtPause;
                            } else {
                                timeAtPause = System.currentTimeMillis();
                                elapsed += timeAtPause - start;
                            }
                            paused = !paused;
                        }
                    }
                }
            }
        }

        public enum Event {
            KILL, TOGGLE
        }
    }

    static class MemLeak {
        public static void main(String[] args) {
            try {
                new MemLeak(0.0f, 1.0f, 2.0f, 3.0f);
            } catch (Leaker leaker) {
                MemLeak brokenObject = leaker.getLeak();
                System.out.println(brokenObject);
            }
        }

        final Float a, b, c, d;

        MemLeak(float a, float b, float c, float d) throws Leaker {
            this.a = a;
            this.b = b;
            if (Math.floor(0.0) == 0.0) throw new Leaker(this);
            this.c = c;
            this.d = d;
        }

        @Override
        public String toString() {
            return "MemLeak {\n\ta: " + a +
                    ",\n\tb: " + b +
                    ",\n\tc: " + c +
                    ",\n\td: " + c +
                    "\n}";
        }
    }


    static class Leaker extends Exception {
        private MemLeak leak;

        public Leaker(MemLeak leak) {
            this.leak = leak;
        }

        public MemLeak getLeak() {
            return leak;
        }
    }
}
