package com.visualjava.vm;

import com.visualjava.invoke.ExecutionContext;
import com.visualjava.invoke.Executor;
import com.visualjava.types.VMReference;
import com.visualjava.types.VMType;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class VMThread {
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
        Thread innerThread = new Thread(() -> {
            VMFrame rootFrame = new VMFrame(runMethod, args);
            runtime.onThreadStart(this);
            threadListener.onFramePush(rootFrame);
            stack.pushFrame(rootFrame);
            int cycleFrequency = this.cycleFrequency.get();
            long beginTime = System.nanoTime();
            boolean isPaused = false;
            long timeAtPause = 0L;


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
//                    wait();
                }
                if (!this.paused && isPaused) {
                    isPaused = false;
                    beginTime = System.nanoTime() + timeAtPause;
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (!killed.get()) killed.set(true);
            runtime.onThreadDeath(this);
        });
        innerThread.start();
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
            VMFrame nextFrame = new VMFrame(invokedMethod, args);
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
}
