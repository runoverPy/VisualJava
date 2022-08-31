package com.visualjava;

import com.visualjava.data.Instruction;
import com.visualjava.vm.VMFrame;
import com.visualjava.vm.VMStack;
import com.visualjava.vm.VMThread;

public class ExecutionContext {
    private final VMThread thread;
    private final VMStack stack;
    private final VMFrame topFrame;
    private final Instruction instr;

    public ExecutionContext(VMThread thread, VMStack stack, VMFrame frame, Instruction instr) {
        this.thread = thread;
        this.stack = stack;
        this.topFrame = frame;
        this.instr = instr;
    }

    public VMThread getThread() {
        return thread;
    }

    public VMStack getStack() {
        return stack;
    }

    public VMFrame getFrame() {
        return topFrame;
    }

    public Instruction getInstr() {
        return instr;
    }

    public <T extends Instruction> T getInstr(Class<T> _class) {
        return (T) instr;
    }
}
