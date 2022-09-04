package com.visualjava.data.attributes;

import com.visualjava.data.ClassData;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AttribLineNumberTable extends Attribute {

    private AttribLineNumberTable(ClassData classData) {
        super(classData);
    }

    private List<CodeLineNumber> lineNumberTable;

    static AttribLineNumberTable read(ClassData classData, DataInputStream dis) throws IOException {
        AttribLineNumberTable lineNumberTable = new AttribLineNumberTable(classData);
        lineNumberTable.lineNumberTable = CodeLineNumber.readCodeLineNumbers(dis, dis.readUnsignedShort());
        return lineNumberTable;
    }

    public CodeLineNumber[] getTable() {
        return lineNumberTable.toArray(CodeLineNumber[]::new);
    }

    public static class CodeLineNumber {
        private CodeLineNumber() {}

        private int start_pc;
        private int line_num;

        public static List<CodeLineNumber> readCodeLineNumbers(DataInputStream dis, int count) throws IOException {
            List<CodeLineNumber> output = new ArrayList<>(count);
            for (int i = 0; i < count; i++) output.add(read(dis));
            return output;
        }

        private static CodeLineNumber read(DataInputStream dis) throws IOException {
            CodeLineNumber codeLineNumber = new CodeLineNumber();
            codeLineNumber.start_pc = dis.readUnsignedShort();
            codeLineNumber.line_num = dis.readUnsignedShort();
            return codeLineNumber;
        }

        public int getStartPC() {
            return start_pc;
        }

        public int getLineNum() {
            return line_num;
        }
    }
}
