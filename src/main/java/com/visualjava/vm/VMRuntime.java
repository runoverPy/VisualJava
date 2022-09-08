package com.visualjava.vm;

import com.visualjava.types.VMNullReference;
import com.visualjava.types.VMType;
import com.visualjava.ui.VisualJavaController;
import org.apache.commons.cli.*;

import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class VMRuntime {
    private static VMRuntime instance;

    public synchronized static VMRuntime getInstance() {
        if (instance == null) instance = new VMRuntime();
        return instance;
    }

    private static Path classPath;

    static {
        URL classPathURL = VMRuntime.class.getResource("/com/visualjava");
        if (classPathURL == null) throw new RuntimeException("could not find classpath");
        classPath = Path.of(classPathURL.getPath());
    }

    public static void main(String[] args) {
        launch(args);
        System.out.println("exiting main");
    }

    public static void launch(String[] args) {
        String relativeClassPath = "/com/visualjava";

        Options cliOptions = new Options();

        Option classPathOption = new Option("cp", "class-path", true, "path to class files");
        classPathOption.setRequired(false);
        cliOptions.addOption(classPathOption);

        DefaultParser parser = new DefaultParser();
        try {
            CommandLine cmdLine = parser.parse(cliOptions, args);
            String[] remainingArgs = cmdLine.getArgs();
            String mainFile = remainingArgs[0];

            VMRuntime runtime = VMRuntime.getInstance();
            runtime.init(mainFile);
        } catch (ParseException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Illegal combination of arguments.");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("%command%", "", cliOptions, "", true);
        }
        //runtime.init("fibonacci", "(I)I", "Fibonacci", new VMType[] {new VMInt(10)});
    }

    public static Path getClassPath() {
        return classPath;
    }
    private final Map<String, VMThread> threads;
    private final VMMethodPool methodPool;
    private final VMMemory memory;
    private final VMClassLoader loader;

    private RuntimeEventsListener runtimeListener = RuntimeEventsListener.NULL;

    public void setRuntimeListener(VisualJavaController runtimeListener) {
        if (runtimeListener == null) throw new NullPointerException();
        this.runtimeListener = runtimeListener;
    }

    private VMRuntime() {
        this.threads = new HashMap<>();
        this.methodPool = new VMMethodPool();
        this.memory = new VMMemoryImpl();
        this.loader = new VMClassLoader();
        loader.registerClassLoadListener(methodPool);
    }

    public void init(String className) {
        init("main", "([Ljava/lang/String;)V", className, new VMType[] {new VMNullReference()});
    }

    public void init(String methodName, String methodDesc, String methodClassName, VMType[] args) {
        loader.loadIfNotAlready(methodClassName);
        VMMethod mainMethod = methodPool.resolve(methodName, methodDesc, methodClassName);
        VMThread main = new VMThread("main", mainMethod, args, false);
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
