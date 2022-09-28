package com.visualjava.vm;

import com.visualjava.data.Method;
import com.visualjava.types.VMType;

public class MethodInvoker {
    public void invoke(Method method) {}
    public void invokeStatic(Method method) {}
    public void invokeVirtual(Method method) {}
    public void invokeDynamic(Method method) {}

    private VMType callNative(Method method, VMType[] args) {
        return null;
    }
}
