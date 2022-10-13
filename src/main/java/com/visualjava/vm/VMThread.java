package com.visualjava.vm;

import com.visualjava.invoke.ExecutionContext;
import com.visualjava.invoke.Executor;
import com.visualjava.types.VMReference;
import com.visualjava.types.VMType;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class VMThread implements Runnable {
    private final String name;
    private final boolean daemon;
    private final VMStack stack;
    private final VMRuntime runtime;
    private final Executor executor;

    private final AtomicInteger cycleFrequency = new AtomicInteger(1);
    private final AtomicBoolean paused = new AtomicBoolean(false);
    private final AtomicBoolean killed = new AtomicBoolean(false);

    private final Thread innerThread;

    public VMThread(VMRuntime runtime, String name, VMMethod runMethod, VMType[] args, boolean daemon) {
        this.runtime = runtime;
        this.daemon = daemon;
        this.stack = new VMStack();
        this.executor = new Executor();
        this.name = name;
        stack.pushFrame(new VMFrame(runMethod, args));
        this.innerThread = new Thread(this);
        innerThread.start();
    }

    @Override
    public void run() {
        runtime.onThreadStart(this);
        int cycleFrequency = this.cycleFrequency.get();
        long beginTime = System.nanoTime();
        boolean isPaused = false;
        long timeAtPause = 0L;

        while (!stack.isComplete() && !killed.get()) {
            long presentTime = System.nanoTime();
            if ((presentTime - beginTime) / 1e9 >= 1d / cycleFrequency && !isPaused) {
                doCycle();
                beginTime = presentTime;
                cycleFrequency = this.cycleFrequency.get();
            }
            if (this.paused.get() && !isPaused) {
                isPaused = true;
                timeAtPause = presentTime - beginTime;
            }
            if (!this.paused.get() && isPaused) {
                isPaused = false;
                beginTime = System.nanoTime() + timeAtPause;
            }
        }

        if (!killed.get()) killed.set(true);
        runtime.onThreadDeath(this);
    }

    public void togglePause() {
        paused.set(!paused.get());
    }

    public void killThread() {
        if (!killed.get()) killed.set(true);
        else throw new IllegalStateException("cannot kill thread, thread already killed");
    }

    public void setCycleFrequency(int cycleFrequency) {
        this.cycleFrequency.set(cycleFrequency);
    }

    public int getCycleFrequency() {
        return this.cycleFrequency.get();
    }

    public void doCycle() {
        VMFrame topFrame = stack.popFrame();
        VMReference throwable;
        if ((throwable = topFrame.checkForThrowable()) != null) {
            topFrame = stack.popFrame();
            topFrame.setThrowable(throwable);
        } else {
            executor.execute(new ExecutionContext(runtime, topFrame));

            if (topFrame.checkForReturnValue()) {
                VMType returnValue = topFrame.getReturnValue();
                if (!stack.isComplete()) {
                    topFrame = stack.popFrame();
                    if (returnValue != null) topFrame.pshStack(returnValue);
                } else {
                    System.out.println();
                    if (returnValue != null) System.out.println("return value: " + returnValue);
                    System.out.println("execution complete, thread \"" + name + "\" closing");
                    return;
                }
            }
            VMMethod invokedMethod;
            if ((invokedMethod = topFrame.getInvokedMethod()) != null) {
                stack.pushFrame(topFrame);
                VMType[] args = new VMType[invokedMethod.getNArgs()];
                for (int i = args.length - 1; i >= 0; i--) {
                    args[i] = topFrame.popStack();
                }
                topFrame = new VMFrame(invokedMethod, args);
            }
        }
        stack.pushFrame(topFrame);
    }

    public boolean isVMThreadDaemon() {
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
