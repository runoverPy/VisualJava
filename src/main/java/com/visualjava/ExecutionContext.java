package com.visualjava;

import com.visualjava.data.Instruction;
import com.visualjava.data.constants.ConstantPool;
import com.visualjava.vm.*;

public class ExecutionContext {
    private final VMThread thread;
    private final VMStack stack;
    private final VMFrame topFrame;
    private final Instruction instr;
    private final ConstantPool pool;
    private final VMMemory memory;

    public ExecutionContext(VMThread thread, VMStack stack, VMFrame frame, Instruction instr, ConstantPool pool, VMMemory memory) {
        this.thread = thread;
        this.stack = stack;
        this.topFrame = frame;
        this.instr = instr;
        this.pool = pool;
        this.memory = memory;
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

    public ConstantPool getPool() {
        return pool;
    }

    public VMMemory getMemory() {
        return memory;
    }
}
