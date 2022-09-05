package com.visualjava.vm;

import com.visualjava.ExecutionContext;
import com.visualjava.Executor;
import com.visualjava.types.VMNullReference;
import com.visualjava.types.VMReference;
import com.visualjava.types.VMType;

public class VMThread extends Thread {
    private String name;
    private final VMStack stack;
    private final Executor executor;

    private volatile int cycleFrequency = 64;
    private volatile boolean paused = false;
    private volatile boolean killed = false;

    public VMThread(String name, VMMethod runMethod, VMType[] args) {
        super("VMThread \"" + name + "\"");
        this.stack = new VMStack();
        this.executor = new Executor();
        this.name = name;
        stack.pushFrame(new VMFrame(runMethod, args));
        start();
    }

    @Override
    public void run() {
        int cycleFrequency = this.cycleFrequency;
        long beginTime = System.nanoTime();
        boolean isPaused = false;
        long timeAtPause = 0L;

        while (!stack.isComplete() && !killed) {
            long presentTime = System.nanoTime();
            if ((presentTime - beginTime) / 1e9 >= 1d / cycleFrequency && !isPaused) {
                doCycle();
                beginTime = presentTime;
                cycleFrequency = this.cycleFrequency;
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

        VMRuntime runtime = VMRuntime.getInstance();
        runtime.onThreadDeath(name);
    }

    public void togglePause() {}

    public void killThread() {}

    public void doCycle() {
        VMFrame topFrame = stack.popFrame();
        VMReference throwable;
        if ((throwable = topFrame.checkForThrowable()) != null) {
            topFrame = stack.popFrame();
            topFrame.setThrowable(throwable);
        } else {
            executor.execute(new ExecutionContext(topFrame));

            if (topFrame.checkForReturnValue()) {
                VMType returnValue = topFrame.getReturnValue();
                if (!stack.isComplete()) {
                    topFrame = stack.popFrame();
                    if (returnValue != null) topFrame.pshStack(returnValue);
                } else {
                    System.out.println();
                    if (returnValue != null) System.out.println("return value: " + returnValue);
                    System.out.println("execution complete, thread closing");
                    return;
                }
            }
            VMMethod invokedMethod;
            if ((invokedMethod = topFrame.getInvokedMethod()) != null) {
                stack.pushFrame(topFrame);
                VMType[] args = new VMType[invokedMethod.getNArgs()];
                for (int i = args.length - 1; i >= 0; i--) {
                    VMType arg = topFrame.popStack();
                    args[i] = arg;
                }
                topFrame = new VMFrame(invokedMethod, args);
            }
        }
        stack.pushFrame(topFrame);
    }

    public boolean isVMThreadDaemon() {
        return false;
    }

    @Override
    public String toString() {
        return "VMThread \"" + name + "\"";
    }
}
