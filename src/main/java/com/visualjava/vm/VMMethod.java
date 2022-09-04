package com.visualjava.vm;

import com.visualjava.data.ExceptionInfo;
import com.visualjava.data.Instruction;
import com.visualjava.data.attributes.AttribLineNumberTable;

import java.util.HashMap;
import java.util.Map;

public class VMMethod {
    private final String methodRepr;

    public VMMethod(String methodRepr) {
        this.methodRepr = methodRepr;
    }

    public String getDeclaringClass() {return null;}
    public String getMethodName() {return null;}
    public String getClassFileName() {return null;}
    public int getMaxLocals() {return 0;}
    public int getStackSize() {return 0;}

    public AttribLineNumberTable getLineNumberTable() {
        return null;
    }

    public ExceptionInfo[] getExceptionInfo() {
        return null;
    }

    public Map<Integer, Instruction> getAllInstructions() {
        return new HashMap<>();
    }

    public Instruction getInstructionAt(int pc) {
        return null;
    }
}
