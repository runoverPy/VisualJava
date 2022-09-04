package com.visualjava.data;

import com.visualjava.data.attributes.Attribute;
import com.visualjava.data.constants.ConstUTF8;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Method {
    private final ClassData classData;

    private Method(ClassData parent) {
        this.classData = parent;
    }

    int access_flags;
    int name_index;
    int desc_index;
    List<Attribute> attributes;

    public static List<Method> readMethods(ClassData classData, DataInputStream dis, int count) throws IOException {
        List<Method> methods = new LinkedList<>();
        for (int i = 0; i < count; i++) methods.add(read(classData, dis));
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

    public String toString(int depth) {
        return "\t".repeat(depth) + this;
    }

    @Override
    public String toString() {
        return classData.resolveConstPoolIndex(name_index, ConstUTF8.class).getValue() + classData.resolveConstPoolIndex(desc_index, ConstUTF8.class).getValue();
    }
}
