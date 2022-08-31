package com.visualjava.data;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Method {
    private final ClassData parent;

    private Method(ClassData parent) {
        this.parent = parent;
    }

    int access_flags;
    int name_index;
    int desc_index;
    List<Attribute> attributes;

    public static List<Method> readMethods(ClassData parent, DataInputStream dis, int count) throws IOException {
        List<Method> methods = new LinkedList<>();
        for (int i = 0; i < count; i++) methods.add(read(parent, dis));
        return methods;
    }

    public static List<Method> readMethods(ClassData classData, DataInputStream dis) throws IOException {
        return readMethods(classData, dis, dis.readUnsignedShort());
    }

    private static Method read(ClassData parent, DataInputStream dis) throws IOException {
        Method method = new Method(parent);
        method.access_flags = dis.readUnsignedShort();
        method.name_index = dis.readUnsignedShort();
        method.desc_index = dis.readUnsignedShort();
        method.attributes = Attribute.readAttributes(parent, dis, dis.readUnsignedShort());
        return method;
    }
}
