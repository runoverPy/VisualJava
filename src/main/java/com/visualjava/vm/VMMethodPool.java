package com.visualjava.vm;

import com.visualjava.data.ClassData;
import com.visualjava.data.Method;
import com.visualjava.data.constants.ConstMethodRef;

import java.util.HashMap;
import java.util.Map;

public class VMMethodPool implements ClassLoadListener {
    private final Map<String, Map<String, VMMethod>> loadedMethods;

    public VMMethodPool() {
        loadedMethods = new HashMap<>();
    }

    public boolean loadClass(ClassData classData) {
        String className = classData.getClassName();
        if (loadedMethods.containsKey(className)) return false;
        Method[] methods = classData.getMethods().toArray(Method[]::new);
        Map<String, VMMethod> classMethods = new HashMap<>();
        for (Method method : methods) {
            classMethods.put(method.toString(), new VMMethod(classData, method));
        }
        loadedMethods.put(className, classMethods);
        return true;
    }

    public VMMethod resolve(String methodName, String methodDesc, String methodClassName) {
        return loadedMethods.get(methodClassName).get(methodName + ":" + methodDesc);
    }

    public VMMethod resolve(ConstMethodRef methodRef) {
        return resolve(
                methodRef.getNameType().getName().getValue(),
                methodRef.getNameType().getDesc().getValue(),
                methodRef.getClazz().getName().getValue()
        );
    }

    @Override
    public void onClassLoad(ClassData classData) {
        loadClass(classData);
    }
}
