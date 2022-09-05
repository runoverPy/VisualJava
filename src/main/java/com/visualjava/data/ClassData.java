package com.visualjava.data;

import com.visualjava.data.attributes.AttribSourceFile;
import com.visualjava.data.attributes.Attribute;
import com.visualjava.data.constants.ConstClass;
import com.visualjava.data.constants.Constant;
import com.visualjava.data.constants.ConstantPool;
import com.visualjava.vm.VMMethod;
import com.visualjava.vm.VMMethodPool;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
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

    public ConstantPool getConstPool() {
        return constant_pool;
    }

    public Constant resolveConstPoolIndex(int index) {
        return constant_pool.getConstant(index);
    }

    public <T extends Constant> T resolveConstPoolIndex(int index, Class<T> _class) {
        return (T) resolveConstPoolIndex(index);
    }

    @Override
    public String toString() {
        StringBuilder methodsString = new StringBuilder();
        if (methods.isEmpty()) methodsString.append("[]");
        else {
            methodsString.append("[\n");
            for (Method method : methods) {
                methodsString.append(method.toString(2)).append(",\n");
            }
            methodsString.append("\t]");
        }

        StringBuilder attribString = new StringBuilder();
        if (attributes.isEmpty()) attribString.append("[]");
        else {
            attribString.append("[\n");
            for (Attribute attrib : attributes) {
                attribString.append("\t".repeat(2)).append(attrib.toString(2, false)).append(",\n");
            }
            attribString.append("\t]");
        }

        return "ClassData {\n" +
                "\tversion_minor: " + version_minor + ",\n" +
                "\tversion_major: " + version_major + ",\n" +
                "\tconstant_pool: " + constant_pool.toString(1) + ",\n" +
                "\taccess_flags: " + access_flags + ",\n" +
                "\tclass_name: " + getClassName() + ",\n" +
                "\tsuper_name: " + getSuperName() + ",\n" +
                "\tinterfaces: " + interfaces + ",\n" +
                "\tfields: " + fields + ",\n" +
                "\tmethods: " + methodsString + ",\n" +
                "\tattributes: " + attribString + "\n" +
                '}';
    }

    public int getVersionMinor() {
        return version_minor;
    }

    public int getVersionMajor() {
        return version_major;
    }

    public ConstantPool getConstantPool() {
        return constant_pool;
    }

    public int getAccessFlags() {
        return access_flags;
    }

    public String getClassName() {
        return resolveConstPoolIndex(class_name, ConstClass.class).toString();
    }

    public String getSuperName() {
        return resolveConstPoolIndex(super_name, ConstClass.class).toString();
    }

    public String getClassFileName() {
        for (Attribute attribute : attributes) {
            if (attribute instanceof AttribSourceFile) return ((AttribSourceFile) attribute).getSourceFile().getValue();
        }
        return "";
    }

    public List<Interface> getInterfaces() {
        return interfaces;
    }

    public List<Field> getFields() {
        return fields;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }
}
