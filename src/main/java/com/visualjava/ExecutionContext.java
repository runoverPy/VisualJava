package com.visualjava;

import com.visualjava.data.Instruction;
import com.visualjava.data.constants.ConstantPool;
import com.visualjava.vm.*;

/**
 *
 */
public final class ExecutionContext {
    private final VMFrame frame;
    private final Instruction instr;
    private final VMFrame.PCHandler pcHandler;

    /**
     */
    public ExecutionContext(VMFrame frame) {
        this.frame = frame;
        this.instr = frame.getCurrentInstruction();
        this.pcHandler = frame.new PCHandler();
    }

    public VMFrame frame() {
        return frame;
    }

    public Instruction getInstr() {
        return instr;
    }

    public <T extends Instruction> T getInstr(Class<T> _class) {
        return (T) instr;
    }

    public VMMethod getMethod() {
        return frame.getMethod();
    }

    public ConstantPool getConstPool() {
        return VMRuntime.getInstance()
                .getLoader()
                .getLoadedClassOrNull(getMethod().getDeclaringClass())
                .getConstPool();
    }

    public VMMethodPool getMethodPool() {
        return VMRuntime.getInstance().getMethodPool();
    }

    public VMMemory getVMMemory() {
        return VMRuntime.getInstance().getMemory();
    }

    public VMFrame.PCHandler getPCHandler() {
        return pcHandler;
    }
}
