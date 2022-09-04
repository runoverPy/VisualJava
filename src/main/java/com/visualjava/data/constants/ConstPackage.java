package com.visualjava.data.constants;

import java.io.DataInputStream;
import java.io.IOException;

public class ConstPackage extends Constant {
    private final int nameIndex;

    private ConstPackage(ConstantPool pool, int nameIndex) {
        super(pool);
        this.nameIndex = nameIndex;
    }

    public static void read(DataInputStream dis, ConstantPool.ConstPoolBuilder poolBuilder) throws IOException {
        int name_index = dis.readUnsignedShort();
        poolBuilder.submitConstant(new ConstPackage(poolBuilder.getConstPool(), name_index));
    }

    public ConstUTF8 getName() {
        return getPool().getConstant(nameIndex, ConstUTF8.class);
    }
}
