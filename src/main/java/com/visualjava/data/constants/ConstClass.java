package com.visualjava.data.constants;

import com.visualjava.types.VMReference;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;

public class ConstClass extends Constant implements LoadableConst {
    private final int name_index;

    private ConstClass(ConstantPool pool, int name_index) {
        super(pool);
        this.name_index = name_index;
    }

    public static void read(DataInputStream dis, ConstantPool.ConstPoolBuilder poolBuilder) throws IOException {
        int name_index = dis.readUnsignedShort();
        poolBuilder.submitConstant(new ConstClass(poolBuilder.getConstPool(), name_index));
    }

    public ConstUTF8 getName() {
        return getPool().getConstant(name_index, ConstUTF8.class);
    }

    @Override
    public VMReference load() {
        return null;
    }

    @Override
    public String toString() {
        return getName().getValue();
    }
}
