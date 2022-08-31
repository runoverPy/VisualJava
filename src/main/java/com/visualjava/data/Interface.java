package com.visualjava.data;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Interface {
    private final ClassData classData;

    private Interface(ClassData classData) {
        this.classData = classData;
    }

    private int name_index;

    public static List<Interface> readInterfaces(ClassData classData, DataInputStream dis, int count) throws IOException {
        List<Interface> interfaces = new LinkedList<>();
        for (int i = 0; i < count; i++) interfaces.add(read(classData, dis));
        return interfaces;
    }

    public static List<Interface> readInterfaces(ClassData classData, DataInputStream dis) throws IOException {
        return readInterfaces(classData, dis, dis.readUnsignedShort());
    }

    public static Interface read(ClassData classData, DataInputStream dis) throws IOException {
        Interface _interface = new Interface(classData);
        _interface.name_index = dis.readUnsignedShort();
        return _interface;
    }

    @Override
    public String toString() {
        return "Interface " + classData.resolveConstPoolIndex(name_index);
    }
}
