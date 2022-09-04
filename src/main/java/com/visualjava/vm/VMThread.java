package com.visualjava.vm;

import com.visualjava.Executor;

public class VMThread extends Thread {
    private final VMStack stack;

    private final Executor executor;

    public VMThread(Thread thread) {
        this.stack = new VMStack();
        Runnable runnable = thread;
        executor = new Executor();
    }

    public void doCycle() {

    }
}
