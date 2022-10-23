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
    private final ThreadEventsListener threadListener;

    public VMThread(VMRuntime runtime, String name, VMMethod runMethod, VMType[] args, boolean daemon) {
        this.runtime = runtime;
        this.daemon = daemon;
        this.stack = new VMStack();
        this.executor = new Executor();
        this.name = name;
        this.threadListener = runtime.getRuntimeListener().makeThreadListener(this);
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

        while (!stack.isEmpty() && !killed.get()) {
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
        else throw new IllegalStateException("cannot kill thread, thread already dead");
    }

    public void setCycleFrequency(int cycleFrequency) {
        this.cycleFrequency.set(cycleFrequency);
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
        executor.execute(context);

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
