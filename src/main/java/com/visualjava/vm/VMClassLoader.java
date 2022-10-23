package com.visualjava.vm;

import com.visualjava.data.ClassData;
import com.visualjava.data.constants.ConstUTF8;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.*;

public class VMClassLoader {
    private final VMRuntime runtime;
    private final Map<String, ClassData> loadedClasses;
    private final List<ClassLoadListener> listeners;

    VMClassLoader(VMRuntime runtime) {
        this.loadedClasses = new HashMap<>();
        this.listeners = new ArrayList<>();
        this.runtime = runtime;
    }

    public boolean isClassLoaded(String className) {
        return loadedClasses.containsKey(className);
    }

    public void loadIfNotAlready(String className) {
        if (!isClassLoaded(className)) loadClass(className);
    }

    public void loadClass(String className) {
        System.out.println("Loading class " + className);
        Path absolutePathToClass = runtime.resolveClassPath(className);
        try (InputStream stream = new FileInputStream(String.valueOf(absolutePathToClass))) {
            ClassData classData = ClassData.read(new DataInputStream(stream));
            loadedClasses.put(className, classData);
            informClassLoadListeners(classData);
        } catch (IOException ioe) {
            System.out.println("The file does not exist!");
        }
    }

    public ClassData getLoadedClassOrNull(String className) {
        return loadedClasses.getOrDefault(className, null);
    }

    private void informClassLoadListeners(ClassData classData) {
        synchronized (listeners) {
            for (ClassLoadListener listener : listeners) {
                listener.onClassLoad(classData);
            }
        }
    }

    public void registerClassLoadListener(ClassLoadListener listener) {
        synchronized (listeners) {
            listeners.add(listener);
        }
    }

    public void releaseClassLoadListener(ClassLoadListener listener) {
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }
}
