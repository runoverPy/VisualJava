package com.visualjava.data.constants;

import java.io.DataInputStream;
import java.io.IOException;

public class ConstInterfaceMethodRef extends ConstMethodRef {

    private ConstInterfaceMethodRef(ConstantPool pool, int classIndex, int nameTypeIndex) {
        super(pool, classIndex, nameTypeIndex);
    }

    public static void read(DataInputStream dis, ConstantPool.ConstPoolBuilder poolBuilder) throws IOException {
        int class_index = dis.readUnsignedShort();
        int name_type_index = dis.readUnsignedShort();
        poolBuilder.submitConstant(new ConstInterfaceMethodRef(poolBuilder.getConstPool(), class_index, name_type_index));
    }
}
