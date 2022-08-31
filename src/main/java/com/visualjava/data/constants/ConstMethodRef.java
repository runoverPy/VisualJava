package com.visualjava.data.constants;

import java.io.DataInputStream;
import java.io.IOException;

public class ConstMethodRef extends Constant {
    private final ConstClass clazz;
    private final ConstNameType nameType;

    private ConstMethodRef(ConstClass clazz, ConstNameType nameType) {
        this.clazz = clazz;
        this.nameType = nameType;
    }

    public static void read(DataInputStream dis, ConstantPool.ConstPoolBuilder poolBuilder) throws IOException {
        int class_index = dis.readUnsignedShort();
        ConstClass clazz = poolBuilder.getConstPool().getConstant(class_index, ConstClass.class);
        int name_type_index = dis.readUnsignedShort();
        ConstNameType nameType = poolBuilder.getConstPool().getConstant(name_type_index, ConstNameType.class);
        poolBuilder.submitConstant(new ConstMethodRef(clazz, nameType));
    }

    public ConstClass getClazz() {
        return clazz;
    }

    public ConstNameType getNameType() {
        return nameType;
    }
}
