package com.visualjava.data;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ExceptionInfo {
    private final ClassData classData;

    private ExceptionInfo(ClassData classData) {
        this.classData = classData;
    }

    private int start_pc;
    private int end_pc;
    private int handler_pc;
    private int catch_type;

    public static ExceptionInfo read(ClassData classData, DataInputStream dis) throws IOException {
        ExceptionInfo exc = new ExceptionInfo(classData);
        exc.start_pc = dis.readUnsignedShort();
        exc.end_pc = dis.readUnsignedShort();
        exc.handler_pc = dis.readUnsignedShort();
        exc.catch_type = dis.readUnsignedShort();
        return exc;
    }

    public static List<ExceptionInfo> readExceptions(ClassData classData, DataInputStream dis, int count) throws IOException {
        List<ExceptionInfo> exceptions = new LinkedList<>();
        for (int i = 0; i < count; i++) exceptions.add(read(classData, dis));
        return exceptions;
    }

    public boolean containsPC(int pc) {
        return start_pc <= pc && pc <= end_pc;
    }

    public int getHandlerPC() {
        return handler_pc;
    }

    public int getCatchType() {
        return catch_type;
    }
}
