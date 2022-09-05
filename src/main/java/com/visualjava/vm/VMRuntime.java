package com.visualjava.vm;

import com.visualjava.types.VMInt;
import com.visualjava.types.VMNullReference;
import com.visualjava.types.VMType;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class VMRuntime {
    private static VMRuntime instance;

    public static VMRuntime getInstance() {
        if (instance == null) instance = new VMRuntime();
        return instance;
    }

    private static Path classPath;

    static {
        URL classPathURL = VMRuntime.class.getResource("/com/visualjava");
        if (classPathURL == null) throw new RuntimeException("could not find classpath");
        classPath = Path.of(classPathURL.getPath());
    }

    public static Path getClassPath() {
        return classPath;
    }

    private final Map<String, VMThread> threads;
    private final VMMethodPool methodPool;
    private final VMMemory memory;
    private final VMClassLoader loader;

    private VMRuntime() {
        this.threads = new HashMap<>();
        this.methodPool = new VMMethodPool();
        this.memory = new VMMemoryImpl();
        this.loader = new VMClassLoader();
        loader.registerClassLoadListener(methodPool);
    }

    public static void main(String[] args) {
        VMRuntime runtime = VMRuntime.getInstance();
        runtime.init("Fibonacci");
        //runtime.init("fibonacci", "(I)I", "Fibonacci", new VMType[] {new VMInt(10)});
    }

    public void init(String className) {
        init("main", "([Ljava/lang/String;)V", className, new VMType[] {new VMNullReference()});
    }

    public void init(String methodName, String methodDesc, String methodClassName, VMType[] args) {
        loader.loadIfNotAlready(methodClassName);
        VMMethod mainMethod = methodPool.resolve(methodName, methodDesc, methodClassName);
        VMThread main = new VMThread("main", mainMethod, args);
        threads.put("main", main);
    }

    public VMMemory getMemory() {
        return memory;
    }

    public VMMethodPool getMethodPool() {
        return methodPool;
    }

    public VMClassLoader getLoader() {
        return loader;
    }

    public void onThreadDeath(String threadName) {
        threads.remove(threadName);
        if (!threads.values().stream().allMatch(VMThread::isVMThreadDaemon)) System.exit(0);
    }
}
