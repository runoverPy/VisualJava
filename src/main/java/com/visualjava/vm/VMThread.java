package com.visualjava.vm;

import com.visualjava.AbstractExecutor;

public class VMThread extends Thread {
    private final VMStack stack;

    private final AbstractExecutor executor;

    public VMThread(Thread thread) {
        this.stack = new VMStack();
        Runnable runnable = thread;
        executor = new AbstractExecutor();
    }

    public void doCycle() {

    }
}
