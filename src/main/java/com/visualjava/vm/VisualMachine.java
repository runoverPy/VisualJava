package com.visualjava.vm;

public class VisualMachine {
    private final VMStack stack;

    public VisualMachine() {
        this.stack = new VMStack();
    }

    private void doCycle() {
        Thread t = new Thread();
    }
}
