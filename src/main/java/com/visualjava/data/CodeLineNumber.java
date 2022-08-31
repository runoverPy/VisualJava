package com.visualjava.data;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;

public class CodeLineNumber {
    private final ClassData classData;

    private CodeLineNumber(ClassData classData) {
        this.classData = classData;
    }

    private int start_pc;
    private int line_num;

    static CodeLineNumber read(ClassData classData, DataInputStream dis) throws IOException {
        CodeLineNumber codeLineNumber = new CodeLineNumber(classData);
        codeLineNumber.start_pc = dis.readUnsignedShort();
        codeLineNumber.line_num = dis.readUnsignedShort();
        return codeLineNumber;
    }
}
