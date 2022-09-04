package com.visualjava.data.attributes;

import com.visualjava.data.ClassData;

import java.io.DataInputStream;
import java.io.IOException;

public class UnknownAttrib extends Attribute {
    private byte[] data;
    private final String attrName;

    private UnknownAttrib(ClassData classData, String attrName) {
        super(classData);
        this.attrName = attrName;
    }

    public static UnknownAttrib read(ClassData classData, DataInputStream dis, int length, String attrName) throws IOException {
        UnknownAttrib attribute = new UnknownAttrib(classData, attrName);
        attribute.data = new byte[length];
        dis.read(attribute.data);
        return attribute;
    }

    @Override
    public String toString() {
        return super.toString() + ": " + this.attrName;
    }
}
