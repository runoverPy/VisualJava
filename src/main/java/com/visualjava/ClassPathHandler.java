package com.visualjava;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ClassPathHandler {
    private final List<Path> paths;

    public ClassPathHandler() {
        this.paths = new ArrayList<>();
    }

    public ClassPathHandler(String classPath) {
        this();
        for (String pathname : classPath.split(":")) {
            try {
                Path path = Paths.get(pathname);
                this.paths.add(path);
            } catch (InvalidPathException ipe) {

            }
        }
    }

    public void addPath(Path path) {

    }

    public void delPath(Path path) {

    }
}
