package com.visualjava.vm;

import java.util.Stack;

public class VMStack {
    private final Stack<VMFrame> frameStack;

    public VMStack() {
        this.frameStack = new Stack<>();
        Thread.dumpStack();
    }

    private static StackTraceElement[] getStackTrace(Stack<VMFrame> stack, int depth) {
        if (stack.isEmpty()) {
            return new StackTraceElement[depth];
        } else {
            VMFrame curFrame = stack.pop();
            StackTraceElement frameDesc = curFrame.getStackTraceElement();
            StackTraceElement[] output = getStackTrace(stack, depth + 1);
            output[output.length - (depth + 1)] = frameDesc;
            stack.push(curFrame);
            return output;
        }
    }

    public void pushFrame(VMFrame frame) {
        frameStack.push(frame);
    }

    public VMFrame peekFrame() {
        return frameStack.peek();
    }

    public VMFrame popFrame() {
        return frameStack.pop();
    }

    public StackTraceElement[] getStackTrace() {
        return getStackTrace(frameStack, 0);
    }
}
