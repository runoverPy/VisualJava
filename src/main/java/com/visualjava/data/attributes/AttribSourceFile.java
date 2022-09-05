package com.visualjava.data.attributes;

import com.visualjava.data.ClassData;
import com.visualjava.data.constants.ConstUTF8;

import java.io.DataInputStream;
import java.io.IOException;

public class AttribSourceFile extends Attribute {
    private ConstUTF8 sourceFile;

    protected AttribSourceFile(ClassData classData) {
        super(classData);
    }

    public static AttribSourceFile read(ClassData classData, DataInputStream dis) throws IOException {
        AttribSourceFile sourceFile = new AttribSourceFile(classData);
        int sourceFileIndex = dis.readUnsignedShort();
        sourceFile.sourceFile = classData.resolveConstPoolIndex(sourceFileIndex, ConstUTF8.class);
        return sourceFile;
    }

    public ConstUTF8 getSourceFile() {
        return sourceFile;
    }
}
