package com.visualjava.data;

import com.visualjava.data.attributes.Attribute;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Field {
    private final ClassData classData;

    private Field(ClassData classData) {
        this.classData = classData;
    }

    private int access_flags;
    private int name_index;
    private int desc_index;
    private int attr_count;
    private List<Attribute> attributes;

    public static List<Field> readFields(ClassData classData, DataInputStream dis, int count) throws IOException {
        List<Field> fields = new LinkedList<>();
        for (int i = 0; i < count; i++) fields.add(read(classData, dis));
        return fields;
    }

    public static List<Field> readFields(ClassData classData, DataInputStream dis) throws IOException {
        return readFields(classData, dis, dis.readUnsignedShort());
    }

    public static Field read(ClassData classData, DataInputStream dis) throws IOException {
        Field field = new Field(classData);
        field.access_flags = dis.readUnsignedShort();
        field.name_index = dis.readUnsignedShort();
        field.desc_index = dis.readUnsignedShort();
        field.attr_count = dis.readUnsignedShort();
        System.out.println(field);
        field.attributes = Attribute.readAttributes(classData, dis, field.attr_count);
        return field;
    }

    @Override
    public String toString() {
        return "Field{" +
                "access_flags=" + access_flags +
                ", name=" + classData.resolveConstPoolIndex(name_index) +
                ", desc=" + classData.resolveConstPoolIndex(desc_index) +
                ", attributes=" + attributes +
                '}';
    }
}
