package com.visualjava.data.attributes;

import com.visualjava.data.ClassData;
import com.visualjava.data.ExceptionInfo;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;

public class AttribCode extends Attribute {
    private int max_stack;
    private int max_locals;
    private byte[] bytecode;
    private List<ExceptionInfo> exc_table;
    private List<Attribute> attributes;

    private AttribCode(ClassData classData) {
        super(classData);
    }

    public static AttribCode read(ClassData classData, DataInputStream dis) throws IOException {
        AttribCode code = new AttribCode(classData);
        code.max_stack = dis.readUnsignedShort();
        code.max_locals = dis.readUnsignedShort();
        code.bytecode = new byte[dis.readInt()];
        dis.read(code.bytecode);
        code.exc_table = ExceptionInfo.readExceptions(classData, dis, dis.readUnsignedShort());
        code.attributes = Attribute.readAttributes(classData, dis, dis.readUnsignedShort());
        return code;
    }
}
