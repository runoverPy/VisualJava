package com.visualjava.vm;

import com.visualjava.CodeLineMapper;
import com.visualjava.ExceptionMapper;
import com.visualjava.data.Instruction;
import com.visualjava.types.VMReference;
import com.visualjava.types.VMType;

import java.util.Arrays;
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
    private VMType returnValue;
    private boolean isReturning;
    private VMMethod invokedMethod;

    private final VMType[] locals;
    private final Stack<VMType> stack;

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

    public boolean setThrowable(VMReference throwable) {
        if (!holdsThrowable) {
            holdsThrowable = true;
            emptyStack();
            pshStack(throwable);
            return true;
        } else return false;
    }

    public VMReference checkForThrowable() {
        if (holdsThrowable) {
            VMReference thowable = popStack(VMReference.class);
            Integer handlerPC = excMapper.getHandlerPC(pc);
            if (handlerPC != null) {
                setPC(handlerPC);
                holdsThrowable = false;
            } else {
                return thowable;
            }
        }
        return null;
    }

    public boolean setReturnValue(VMType returnValue) {
        if (!isReturning) {
            isReturning = true;
            this.returnValue = returnValue;
            return true;
        } else return false;
    }

    public boolean checkForReturnValue() {
        return isReturning;
    }

    public VMType getReturnValue() {
        return returnValue;
    }

    public boolean setInvokedMethod(VMMethod method) {
        if (this.invokedMethod != null) return false;
        this.invokedMethod = method;
        return true;
    }

    public VMMethod getInvokedMethod() {
        VMMethod invokedMethod = this.invokedMethod;
        if (invokedMethod != null) this.invokedMethod = null;
        return invokedMethod;
    }

    public VMMethod getMethod() {
        return method;
    }

    public Instruction getCurrentInstruction() {
        return method.getInstruction(pc);
    }

    public void emptyStack() {
        synchronized (stack) {
            stack.empty();
        }
    }

    public void pshStack(VMType value) {
        synchronized (stack) {
            stack.push(value);
        }
    }

    public VMType popStack() {
        synchronized (stack) {
            return stack.pop();
        }
    }

    public <T extends VMType> T popStack(Class<T> _class) {
        return (T) popStack();
    }

    public void putLocal(int index, VMType value) {
        synchronized (locals) {
            locals[index] = value;
        }
    }

    public VMType getLocal(int index) {
        synchronized (locals) {
            return locals[index];
        }
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

    public String frameState() {
        String arr, loc;
        synchronized (stack) {
            VMType[] stackArr = stack.toArray(VMType[]::new);
            arr = Arrays.toString(stackArr);
        }
        synchronized (locals) {
            loc = Arrays.toString(locals);
        }

        return "stack: " + arr + ", locals: " + loc;
    }

    public class PCHandler {
        private static PCHandler activeHandler;

        private Offset offset;

        public PCHandler() {
            if (activeHandler != null) throw new IllegalStateException("Cannot create new PCHandler, the previous one has not been executed yet");
            this.offset = new RelativeOffset(getCurrentInstruction().getArgc());
            activeHandler = this;
        }

        public void setRelativeOffset(int offset) {
            this.offset = new RelativeOffset(offset);
        }

        public void setAbsoluteOffset(int offset) {
            this.offset = new AbsoluteOffset(offset);
        }

        public void execute() {
            if (activeHandler == this) {
                setPC(offset.getOffset());
                activeHandler = null;
            } else throw new IllegalStateException("Cannot execute PCHandler");
        }

        private abstract static class Offset {
            protected final int offset;

            public Offset(int offset) {
                this.offset = offset;
            }

            abstract int getOffset();
        }

        private static class AbsoluteOffset extends Offset {
            public AbsoluteOffset(int offset) {
                super(offset);
            }

            @Override
            int getOffset() {
                return offset;
            }
        }

        private class RelativeOffset extends Offset {
            public RelativeOffset(int offset) {
                super(offset);
            }

            @Override
            int getOffset() {
                return pc + offset;
            }
        }
    }


}
