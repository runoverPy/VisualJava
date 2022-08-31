package com.visualjava.data;

import com.visualjava.ListUtil;
import com.visualjava.data.constants.ConstString;
import com.visualjava.data.constants.ConstUTF8;
import com.visualjava.data.constants.Constant;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public abstract class Attribute {
    private final ClassData classData;

    private Attribute(ClassData classData) {
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
            case "Code" -> Code.read(classData, dis);
            case "LineNumberTable" -> LineNumberTable.read(classData, dis);
            default -> Default.read(classData, dis, length);
        };
    }

    public static class Default extends Attribute {
        private byte[] data;

        private Default(ClassData classData) {
            super(classData);
        }

        public static Default read(ClassData classData, DataInputStream dis, int length) throws IOException {
            Default attribute = new Default(classData);
            attribute.data = new byte[length];
            dis.read(attribute.data);
            return attribute;
        }
    }

    public static class Code extends Attribute {
        private int max_stack;
        private int max_locals;
        private byte[] bytecode;
        private List<ExceptionInfo> exc_table;
        private List<Attribute> attributes;

        private Code(ClassData classData) {
            super(classData);
        }

        public static Code read(ClassData classData, DataInputStream dis) throws IOException {
            Code code = new Code(classData);
            code.max_stack = dis.readUnsignedShort();
            code.max_locals = dis.readUnsignedShort();
            code.bytecode = new byte[dis.readInt()];
            dis.read(code.bytecode);
            code.exc_table = ExceptionInfo.readExceptions(classData, dis, dis.readUnsignedShort());
            code.attributes = Attribute.readAttributes(classData, dis, dis.readUnsignedShort());
            return code;
        }
    }

    public static class LineNumberTable extends Attribute {

        private LineNumberTable(ClassData classData) {
            super(classData);
        }

        private List<CodeLineNumber> lineNumberTable;

        static LineNumberTable read(ClassData classData, DataInputStream dis) throws IOException {
            LineNumberTable lineNumberTable = new LineNumberTable(classData);
            try {
                lineNumberTable.lineNumberTable = ListUtil.listComp(LinkedList::new, () -> CodeLineNumber.read(classData, dis), dis.readUnsignedShort());
            } catch (Exception e) {
                throw new IOException(e);
            }
            return lineNumberTable;
        }
    }
}
