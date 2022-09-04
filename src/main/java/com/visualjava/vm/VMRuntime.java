package com.visualjava.vm;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class VMRuntime {
    private static Path classPath;

    public static Path getClassPath() {
        return classPath;
    }

    private final Map<String, VMThread> threads;
    private final VMMethodPool methodPool;
    private final VMClassLoader loader;

    private VMRuntime() {
        this.threads = new HashMap<>();
        this.methodPool = new VMMethodPool();
        this.loader = new VMClassLoader();
        loader.registerClassLoadListener(methodPool);
    }

    public void init(String className) {
        init("main", "([Ljava/lang/String;)V", className);
    }

    public void init(String methodName, String methodDesc, String methodClassName) {
        loader.loadIfNotAlready(methodClassName);
        VMMethod mainMethod = methodPool.resolve(methodName, methodDesc, methodClassName);
        VMThread main = new VMThread(mainMethod);
        threads.put("main", main);
    }

    public static void main(String[] args) {
        new Exception().printStackTrace();
    }
}
