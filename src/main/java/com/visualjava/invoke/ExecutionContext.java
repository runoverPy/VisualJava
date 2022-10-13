package com.visualjava.invoke;

import com.visualjava.data.Instruction;
import com.visualjava.data.constants.ConstantPool;
import com.visualjava.vm.*;

/**
 *
 */
public final class ExecutionContext {
    private final VMRuntime runtime;
    private final VMFrame frame;
    private final Instruction instr;
    private final VMFrame.PCHandler pcHandler;

    /**
     */
    public ExecutionContext(VMRuntime runtime, VMFrame frame) {
        this.runtime = runtime;
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
        return runtime
                .getLoader()
                .getLoadedClassOrNull(getMethod().getDeclaringClass())
                .getConstPool();
    }

    public VMMethodPool getMethodPool() {
        return runtime.getMethodPool();
    }

    public VMMemory getVMMemory() {
        return runtime.getMemory();
    }

    public VMFrame.PCHandler getPCHandler() {
        return pcHandler;
    }
}
