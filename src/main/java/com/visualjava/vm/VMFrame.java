package com.visualjava.vm;

import com.visualjava.CodeLineMapper;
import com.visualjava.ExceptionMapper;
import com.visualjava.types.VMReference;
import com.visualjava.types.VMType;

import java.util.Stack;

public class VMFrame {
    private final String declaringClass;
    private final String methodName;
    private final String fileName;
    private final CodeLineMapper lineMapper;
    private final ExceptionMapper excMapper;

    private Throwable currentThrowable = null;
    private boolean holdsThrowable;

    private VMType[] locals;
    private Stack<VMType> stack;

    private int pc = 0;

    VMFrame(final VMType[] params, final int maxLocals, final int maxStack) {
        locals = new VMType[maxLocals];
        System.arraycopy(params, 0, locals, 0, params.length);
        stack = new Stack<>();
        this.declaringClass = "";
        this.methodName = "";
        this.fileName = "";
        this.lineMapper = null;
        this.excMapper = null;
    }

    public StackTraceElement getStackTraceElement() {
        return new StackTraceElement(
                this.declaringClass,
                this.methodName,
                this.fileName,
                lineMapper.getPCLineNumber(pc)
        );
    }

    public void checkForThrowable() {
        if (currentThrowable != null) {
            Integer handlerPC = excMapper.getHandlerPC(pc);
            if (handlerPC != null) {
                setPC(handlerPC);
                currentThrowable = null;
            } else {
                VMReference throwable = popStack(VMReference.class);
            }
        }
    }

    public boolean setThrowable(VMReference throwable) {
        if (!holdsThrowable) {
            holdsThrowable = true;
            emptyStack();
            pshStack(throwable);
            return true;
        } else return false;
    }

    public void emptyStack() {
        stack.empty();
    }

    public void pshStack(VMType value) {
        stack.push(value);
    }

    public VMType popStack() {
        return stack.pop();
    }

    public <T extends VMType> T popStack(Class<T> _class) {
        return (T) popStack();
    }

    public void putLocal(int index, VMType value) {
        locals[index] = value;
    }

    public VMType getLocal(int index) {
        return locals[index];
    }

    public <T extends VMType> T getLocal(int index, Class<T> _class) {
        return (T) getLocal(index);
    }

    public int getPC() {
        return pc;
    }

    public void incPC(int offset) {
        pc += offset;
    }

    public void setPC(int branch) {
        pc = branch;
    }
}
