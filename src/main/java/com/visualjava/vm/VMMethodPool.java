package com.visualjava.vm;

import com.visualjava.data.ClassData;

public class VMMethodPool implements ClassLoadListener {
    public boolean loadClass(ClassData classData) {
        return false;
    }

    public VMMethod resolve(String methodName, String methodDesc, String methodClassName) {
        return null;
    }

    @Override
    public void onClassLoad(ClassData classData) {
        loadClass(classData);
    }
}
