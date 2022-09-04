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

    private final VMMethod method;

    private Throwable currentThrowable = null;
    private boolean holdsThrowable;

    private VMType[] locals;
    private Stack<VMType> stack;

    private int pc = 0;

    VMFrame(VMMethod method, VMType[] params) {
        locals = new VMType[method.getMaxLocals()];
        System.arraycopy(params, 0, locals, 0, params.length);
        stack = new Stack<>();
        this.method = method;
        this.declaringClass = method.getDeclaringClass();
        this.methodName = method.getMethodName();
        this.fileName = method.getClassFileName();
        this.lineMapper = new CodeLineMapper(method.getLineNumberTable());
        this.excMapper = new ExceptionMapper(method.getExceptionInfo());
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
