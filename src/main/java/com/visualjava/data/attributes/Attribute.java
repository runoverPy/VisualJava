package com.visualjava.data.attributes;

import com.visualjava.data.ClassData;
import com.visualjava.data.constants.ConstUTF8;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public abstract class Attribute {
    private final ClassData classData;

    protected Attribute(ClassData classData) {
        this.classData = classData;
    }

    public static List<Attribute> readAttributes(ClassData classData, DataInputStream dis) throws IOException {
        return readAttributes(classData, dis, dis.readUnsignedShort());
    }

    public static List<Attribute> readAttributes(ClassData classData, DataInputStream dis, int count) throws IOException {
        List<Attribute> attributes = new LinkedList<>();
        for (int i = 0; i < count; i++) attributes.add(read(classData, dis));
        return attributes;
    }

    static Attribute read(ClassData classData, DataInputStream dis) throws IOException {
        int attrNameIndex = dis.readUnsignedShort();
        String attrName = classData.resolveConstPoolIndex(attrNameIndex, ConstUTF8.class).getValue();
        int length = dis.readInt();
        return switch (attrName) {
            case "Code" -> AttribCode.read(classData, dis);
            case "LineNumberTable" -> AttribLineNumberTable.read(classData, dis);
            case "SourceFile" -> AttribSourceFile.read(classData, dis);
            case "BootstrapMethods" -> AttribBootstrapMethods.read(classData, dis);
            case "InnerClasses" -> AttribInnerClasses.read(classData, dis);
            default -> UnknownAttrib.read(classData, dis, length, attrName);
        };
    }

    public String toString(int depth) {
        return "\t".repeat(depth) + this;
    }

    public String toString(int depth, boolean onNewLine) {
        return onNewLine ? toString(depth) : toString();
    }
}
