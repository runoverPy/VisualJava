package com.visualjava.vm;

import com.visualjava.data.ClassData;
import com.visualjava.data.ExceptionInfo;
import com.visualjava.data.Instruction;
import com.visualjava.data.Method;
import com.visualjava.data.attributes.AttribCode;
import com.visualjava.data.attributes.AttribLineNumberTable;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VMMethod {
    private final String declaringClass;
    private final String classFileName;
    private final String methodName;
    private final String methodDesc;
    private final int maxLocals;
    private final int stackSize;

    private final byte[] bytecode;


    public VMMethod(ClassData classData, Method methodData) {
        declaringClass = classData.getClassName();
        classFileName = classData.getClassFileName();
        methodName = methodData.getName();
        methodDesc = methodData.getDesc();

        AttribCode attribCode = methodData.getAttributeOrNull(AttribCode.class);
        maxLocals = attribCode.getMaxLocals();
        stackSize = attribCode.getStackSize();
        bytecode = attribCode.getBytecode();
    }

    public String getDeclaringClass() {
        return declaringClass;
    }

    public String getClassFileName() {
        return classFileName;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getMethodDesc() {
        return methodDesc;
    }

    public String getReturn() {
        Pattern p = Pattern.compile("\\((\\S*)\\)(\\S+)");
        Matcher m = p.matcher(methodDesc);
        m.matches();
        return m.group(2);
    }

    public String[] getParams() {
        Pattern p = Pattern.compile("\\((\\S+|)\\)(\\S+)");
        Matcher m = p.matcher(methodDesc);
        m.matches();
        String args = m.group(1);
        if (Objects.equals(args, "")) return new String[0];
        Pattern p2 = Pattern.compile("(\\[*?(?:[BCDFIJSZ]|L[a-zA-Z/]+;))");
        Matcher m2 = p2.matcher(args);
        ArrayList<String> argList = new ArrayList<>();
        while (m2.find()) argList.add(m2.group());
        return argList.toArray(String[]::new);
    }

    public int getNArgs() {
        return getParams().length;
    }

    public int getMaxLocals() {
        return maxLocals;
    }

    public int getStackSize() {
        return stackSize;
    }

    public AttribLineNumberTable getLineNumberTable() {
        return null;
    }

    public ExceptionInfo[] getExceptionInfo() {
        return null;
    }

    public Map<Integer, Instruction> getAllInstructions() {
        return Instruction.readAll(new DataInputStream(new ByteArrayInputStream(bytecode)));
    }

    public Instruction getInstruction(int pc) {
        return Instruction.read(
                new DataInputStream(new ByteArrayInputStream(bytecode, pc, bytecode.length - pc)), pc
        );
    }

    @Override
    public String toString() {
        return getMethodName() + ":" + getMethodDesc();
    }
}
