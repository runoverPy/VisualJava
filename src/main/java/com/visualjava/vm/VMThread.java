package com.visualjava.vm;

import com.visualjava.Executor;
import com.visualjava.types.VMNullReference;
import com.visualjava.types.VMType;

public class VMThread extends Thread {
    private final VMStack stack;
    private final Executor executor;
    private VMMethod enterMethod;

    private int cycleFrequency = 4;

    public VMThread(Thread thread) {
        this.stack = new VMStack();
        Runnable runnable = thread;
        executor = new Executor();
    }

    public VMThread(VMMethod runMethod) {
        this.stack = new VMStack();
        this.executor = new Executor();
        this.enterMethod = runMethod;
        stack.pushFrame(new VMFrame(runMethod, new VMType[]{new VMNullReference()}));
    }

    @Override
    public void run() {

    }

    public void doCycle() {

    }
}
