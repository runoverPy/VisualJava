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
        code.bytecode = dis.readNBytes(dis.readInt());
        code.exc_table = ExceptionInfo.readExceptions(classData, dis, dis.readUnsignedShort());
        code.attributes = Attribute.readAttributes(classData, dis, dis.readUnsignedShort());
        return code;
    }

    public int getStackSize() {
        return max_stack;
    }

    public int getMaxLocals() {
        return max_locals;
    }

    public byte[] getBytecode() {
        return bytecode;
    }

    public List<ExceptionInfo> getExcTable() {
        return exc_table;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }
}
