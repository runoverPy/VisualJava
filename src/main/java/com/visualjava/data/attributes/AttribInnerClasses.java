package com.visualjava.data.attributes;

import com.visualjava.data.ClassData;
import com.visualjava.data.constants.ConstClass;
import com.visualjava.data.constants.ConstUTF8;

import java.io.DataInputStream;
import java.io.IOException;

public class AttribInnerClasses extends Attribute {
    private InnerClass[] innerClasses;

    protected AttribInnerClasses(ClassData classData) {
        super(classData);
    }

    public static AttribInnerClasses read(ClassData classData, DataInputStream stream) throws IOException {
        AttribInnerClasses innerClasses = new AttribInnerClasses(classData);
        int numClasses = stream.readUnsignedShort();
        innerClasses.innerClasses = new InnerClass[numClasses];
        for (int i = 0; i < numClasses; i++) innerClasses.innerClasses[i] = InnerClass.read(classData, stream);
        return innerClasses;
    }

    public static class InnerClass {
        private ConstClass innerClass;
        private ConstClass outerClass;
        private String innerName;
        private int access_fields;

        private static InnerClass read(ClassData classData, DataInputStream stream) throws IOException {
            InnerClass innerClass = new InnerClass();
            int innerClassIndex = stream.readUnsignedShort();
            int outerClassIndex = stream.readUnsignedShort();
            int innerNameIndex = stream.readUnsignedShort();
            innerClass.innerClass = classData.resolveConstPoolIndex(innerClassIndex, ConstClass.class);
            innerClass.outerClass = classData.resolveConstPoolIndex(outerClassIndex, ConstClass.class);
            innerClass.innerName = classData.resolveConstPoolIndex(innerNameIndex, ConstUTF8.class).getValue();
            innerClass.access_fields = stream.readUnsignedShort();
            return innerClass;
        }
    }
}
