package com.visualjava.vm;

import com.visualjava.types.VMNullReference;
import com.visualjava.types.VMType;
import com.visualjava.ui.VisualJavaController;
import org.apache.commons.cli.*;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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

    public static void main(String[] args) {
        new VMRuntime("~/IdeaProjects/VisualJava/src/main/resources/com/visualjava/:~/IdeaProjects/VisualJava/testfiles/").init("Fibonacci", 4);
    }

    private final Map<String, VMThread> threads;
    private final VMMethodPool methodPool;
    private final VMMemory memory;
    private final VMClassLoader loader;
    private final List<Path> classPath;

    private RuntimeEventsListener runtimeListener = RuntimeEventsListener.NULL;

    public void setRuntimeListener(VisualJavaController runtimeListener) {
        if (runtimeListener == null) throw new NullPointerException();
        this.runtimeListener = runtimeListener;
    }

    public VMRuntime(String classPath) {
        this(parseClassPaths(classPath));
    }

    public VMRuntime(List<Path> classPath) {
        this.threads = new HashMap<>();
        this.methodPool = new VMMethodPool();
        this.memory = new VMMemoryImpl();
        this.loader = new VMClassLoader(this);
        this.classPath = classPath;
        loader.registerClassLoadListener(methodPool);
    }

    public void init(String className) throws NoSuchMethodError {
        init(className, 1);
    }

    public void init(String className, int frequency) {
        loader.loadIfNotAlready(className);
        VMMethod mainMethod = methodPool.resolve("main", "([Ljava/lang/String;)V", className);
        if (mainMethod == null) throw new NoSuchMethodError("Specified class has no main method, or the signature is wrong");
        VMThread main = new VMThread(this, "main", mainMethod, new VMType[] { new VMNullReference() }, false);
        main.setCycleFrequency(frequency);
        threads.put("main", main);
    }

    public Path resolveClassPath(String className) {
        if (!className.contains(".class")) className = className + ".class";
        for (Path path : classPath) {
            if (!Files.isDirectory(path)) continue;
            Path classPath = path.resolve(className);
            if (Files.exists(classPath)) return classPath;
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
        if (threads.values().stream().allMatch(VMThread::isVMThreadDaemon)) endRuntime();
    }

    private void endRuntime() {
        threads.values().forEach(VMThread::killThread);
        runtimeListener.onRuntimeExit();
    }
}
