package com.visualjava.vm;

import com.visualjava.types.VMNullReference;
import com.visualjava.types.VMType;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VMRuntime {
    public static List<Path> parseClassPaths(String classPathsRaw) {
        String[] paths = classPathsRaw.split(":");
        List<Path> classPaths = new ArrayList<>(paths.length);
        for (String path : paths) try {
            classPaths.add(Path.of(path));
        } catch (InvalidPathException ignored) {
            System.err.println("\"" + path + "\" is not a valid path on this system");
        }
        return classPaths;
    }

    static {
//        URL classPathURL = VMRuntime.class.getResource("/com/visualjava");
//        if (classPathURL == null) throw new RuntimeException("could not find classpath");
//        classPath = Path.of(classPathURL.getPath());
    }

    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(5000);
        new VMRuntime("testfiles/", RuntimeEventsListener.makeRuntimePrinter()).init("Fibonacci", 16);
    }

    private final Map<String, VMThread> threads;
    private final VMMethodPool methodPool;
    private final VMMemory memory;
    private final VMClassLoader loader;
    private final List<Path> classPath;

    private final RuntimeEventsListener runtimeListener;

    public VMRuntime(String classPath, RuntimeEventsListener runtimeListener) {
        this(parseClassPaths(classPath), runtimeListener);
    }

    public VMRuntime(List<Path> classPath, RuntimeEventsListener runtimeListener) {
        this.threads = new HashMap<>();
        this.methodPool = new VMMethodPool();
        this.memory = new VMMemoryImpl();
        this.loader = new VMClassLoader(this);
        this.classPath = classPath;
        this.runtimeListener = runtimeListener;
        loader.registerClassLoadListener(methodPool);
    }

    public void init(String className, int mainFreq) {
        Thread runtimeThread = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Thread: " + Thread.currentThread());
            loader.loadIfNotAlready(className);
            VMMethod mainMethod = methodPool.resolve("main", "([Ljava/lang/String;)V", className);
            if (mainMethod == null) throw new NoSuchMethodError("Specified class has no main method, or the signature is wrong");
            VMThread main = new VMThread(this, "main", mainMethod, new VMType[] { new VMNullReference() }, false);
            main.setCycleFrequency(mainFreq);
            threads.put("main", main);
        }, "VisualJava Runtime Thread");
        runtimeThread.setDaemon(true);
        runtimeThread.start();
    }

    public Path resolveClassPath(String className) {
        if (!className.contains(".class")) className = className + ".class";
        for (Path path : classPath) {
            System.out.println("looking for class at " + path.toAbsolutePath());
            if (!Files.isDirectory(path)) continue;
            Path classPath = path.resolve(className);
            if (Files.exists(classPath)) {
                return classPath;
            }
        }
        return null;
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

    public void onThreadStart(VMThread thread) {
        runtimeListener.onThreadSpawn(thread);
    }

    public void onThreadDeath(VMThread thread) {
        threads.remove(thread.getName());
        runtimeListener.onThreadDeath(thread);
        if (threads.values().stream().allMatch(VMThread::isDaemon)) endRuntime();
    }

    private void endRuntime() {
        threads.values().forEach(VMThread::killThread);
        runtimeListener.onRuntimeExit();
    }

    public RuntimeEventsListener getRuntimeListener() {
        return runtimeListener;
    }
}
