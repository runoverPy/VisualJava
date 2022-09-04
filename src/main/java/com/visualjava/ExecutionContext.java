package com.visualjava;

import com.visualjava.data.Instruction;
import com.visualjava.data.constants.ConstantPool;
import com.visualjava.vm.*;

/**
 * @param stack     Invocation specific attributes
 * @param constPool Virtual Machine attributes
 */
public record ExecutionContext(VMStack stack, VMFrame frame, Instruction instr, ConstantPool constPool,
                               VMMethodPool methodPool, VMMemory memory) {

    public <T extends Instruction> T getInstr(Class<T> _class) {
        return (T) instr;
    }
}
