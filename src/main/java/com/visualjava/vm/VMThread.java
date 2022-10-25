package com.visualjava.vm;

import com.visualjava.invoke.ExecutionContext;
import com.visualjava.invoke.Executor;
import com.visualjava.types.VMReference;
import com.visualjava.types.VMType;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class VMThread implements Runnable {
    private final String name;
    private final boolean daemon;
    private final VMStack stack;
    private final VMRuntime runtime;

    private final AtomicInteger cycleFrequency = new AtomicInteger(1);
    private boolean paused = false;
    private final AtomicBoolean killed = new AtomicBoolean(false);

    private final ThreadEventsListener threadListener;

    public VMThread(VMRuntime runtime, String name, VMMethod runMethod, VMType[] args, boolean daemon) {
        this.runtime = runtime;
        this.daemon = daemon;
        this.stack = new VMStack();
        this.name = name;
        this.threadListener = runtime.getRuntimeListener().makeThreadListener(this);
        VMFrame rootFrame = new VMFrame(runMethod, args, threadListener.makeFrameListener());
        stack.pushFrame(rootFrame);
        threadListener.onFramePush(rootFrame);
        Thread innerThread = new Thread(this);
        innerThread.start();
    }

    @Override
    public void run() {
        runtime.onThreadStart(this);
        int cycleFrequency = this.cycleFrequency.get();
        long beginTime = System.nanoTime();
        boolean isPaused = false;
        long timeAtPause = 0L;

        // here lie the bodies of innumerable lost core cycles.
        while (!stack.isEmpty() && !killed.get()) {
            long presentTime = System.nanoTime();
            if ((presentTime - beginTime) / 1e9 >= 1d / cycleFrequency && !isPaused) {
                doCycle();
                beginTime = presentTime;
                cycleFrequency = this.cycleFrequency.get();
            }
            if (this.paused && !isPaused) {
                isPaused = true;
                timeAtPause = presentTime - beginTime;
            }
            if (!this.paused && isPaused) {
                isPaused = false;
                beginTime = System.nanoTime() + timeAtPause;
            }
        }

        if (!killed.get()) killed.set(true);
        runtime.onThreadDeath(this);
    }

    public void togglePause() {
        paused = !paused;
    }

    public void killThread() {
        if (!killed.get()) killed.set(true);
        else throw new IllegalStateException("cannot kill thread, thread already dead");
    }

    public void setCycleFrequency(int cycleFrequency) {
        System.out.println("setting cycle frequency");
        this.cycleFrequency.set(cycleFrequency);
        threadListener.onFreqChange(cycleFrequency);
    }

    public int getCycleFrequency() {
        return this.cycleFrequency.get();
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
        threadListener.onInstrExec(context);
        Executor.execute(context);

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
            VMFrame nextFrame = new VMFrame(invokedMethod, args, threadListener.makeFrameListener());
            threadListener.onFramePush(nextFrame);
            stack.pushFrame(nextFrame);
        }
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

        public ThreadTimer() {
            this.eventsQueue = new LinkedList<>();
        }

        public void pause() {
            synchronized (eventsQueue) {
                eventsQueue.add(Event.PAUSE);
                eventsQueue.notify();
            }
        }

        public void kill() {
            synchronized (eventsQueue) {
                eventsQueue.add(Event.KILL);
                eventsQueue.notify();
            }
        }

        /**
         * Method blocks the thread, and waits at until the timer is given an additional input, but at most `millis` milliseconds.
         * @param millis the maximum amount of time this method may block thread execution
         * @return The Event that caused the thread to unblock, or `Event.CONTINUE` if the time runs out
         * @throws InterruptedException if the thread is interrupted
         */
        public Event await(long millis) throws InterruptedException {
            synchronized (eventsQueue) {
                eventsQueue.wait(millis);
                Event event = eventsQueue.poll();
                if (event == null) return Event.CONTINUE;
                else return event;
            }
        }

        public enum Event {
            KILL, PAUSE, RESUME, CONTINUE
        }
    }
}
