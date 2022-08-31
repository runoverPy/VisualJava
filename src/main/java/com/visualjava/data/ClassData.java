package com.visualjava.data;

import com.visualjava.data.constants.Constant;
import com.visualjava.data.constants.ConstantPool;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

public class ClassData {
    private int version_minor;
    private int version_major;
    private ConstantPool constant_pool;
    private int access_flags;
    private int class_name;
    private int super_name;
    private List<Interface> interfaces;
    private List<Field> fields;
    private List<Method> methods;
    private List<Attribute> attributes;

    private ClassData() {}

    public static ClassData read(DataInputStream dis) {
        try {
            byte[] magicNumber = new byte[4];
            if (dis.readInt() != 0xCAFEBABE) throw new ClassFormatError();

            ClassData classdata = new ClassData();
            classdata.version_minor = dis.readUnsignedShort();
            classdata.version_major = dis.readUnsignedShort();
            classdata.constant_pool = ConstantPool.read(dis);
            classdata.access_flags = dis.readUnsignedShort();
            classdata.class_name = dis.readUnsignedShort();
            classdata.super_name = dis.readUnsignedShort();
            classdata.interfaces = Interface.readInterfaces(classdata, dis);
            classdata.fields = Field.readFields(classdata, dis);
            classdata.methods = Method.readMethods(classdata, dis);
            classdata.attributes = Attribute.readAttributes(classdata, dis);
            return classdata;
        } catch (IOException ioe) {
            ioe.printStackTrace(System.err);
            return null;
        }
    }

    public Constant resolveConstPoolIndex(int index) {
        return constant_pool.getConstant(index);
    }

    public <T extends Constant> T resolveConstPoolIndex(int index, Class<T> _class) {
        return (T) resolveConstPoolIndex(index);
    }

    @Override
    public String toString() {
        return "ClassData {\n" +
                "\tversion_minor=" + version_minor + ",\n" +
                "\tversion_major=" + version_major + ",\n" +
                "\tconstant_pool=" + constant_pool + ",\n" +
                "\taccess_flags=" + access_flags + ",\n" +
                "\tclass_name=" + class_name + ",\n" +
                "\tsuper_name=" + super_name + ",\n" +
                "\tinterfaces=" + interfaces + ",\n" +
                "\tfields=" + fields + ",\n" +
                "\tmethods=" + methods + ",\n" +
                "\tattributes=" + attributes + "\n" +
                '}';
    }

    public static void main(String[] args) {
        System.out.println(ClassData.class.getResource("/com/visualjava/Fibonacci.cls"));
        try (InputStream stream = ClassData.class.getResourceAsStream("/com/visualjava/Fibonacci.cls")) {
            if (stream == null) throw new IOException();
            ClassData classData = ClassData.read(new DataInputStream(stream));
            System.out.println(classData);
        } catch (IOException ioe) {
            System.out.println("The file does not exist!");
            System.exit(1);
        }
    }
}
