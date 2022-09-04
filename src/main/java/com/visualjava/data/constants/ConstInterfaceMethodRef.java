package com.visualjava.data.constants;

import java.io.DataInputStream;
import java.io.IOException;

public class ConstInterfaceMethodRef extends Constant {
    private final int classIndex;
    private final int nameTypeIndex;

    private ConstInterfaceMethodRef(ConstantPool pool, int classIndex, int nameTypeIndex) {
        super(pool);
        this.classIndex = classIndex;
        this.nameTypeIndex = nameTypeIndex;
    }

    public static void read(DataInputStream dis, ConstantPool.ConstPoolBuilder poolBuilder) throws IOException {
        int class_index = dis.readUnsignedShort();
        int name_type_index = dis.readUnsignedShort();
        poolBuilder.submitConstant(new ConstInterfaceMethodRef(poolBuilder.getConstPool(), class_index, name_type_index));
    }

    public ConstClass getClazz() {
        return getPool().getConstant(classIndex, ConstClass.class);
    }

    public ConstNameType getNameType() {
        return getPool().getConstant(nameTypeIndex, ConstNameType.class);
    }
}
