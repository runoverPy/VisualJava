package com.visualjava.data.constants;

import com.visualjava.types.VMReference;

import java.io.DataInputStream;
import java.io.IOException;

public class ConstMethodType extends Constant implements LoadableConst {
    private final int descIndex;

    private ConstMethodType(ConstantPool pool, int descIndex) {
        super(pool);
        this.descIndex = descIndex;
    }

    public static void read(DataInputStream dis, ConstantPool.ConstPoolBuilder poolBuilder) throws IOException {
        int desc_index = dis.readUnsignedShort();
        poolBuilder.submitConstant(new ConstMethodType(poolBuilder.getConstPool(), desc_index));
    }

    public ConstUTF8 getDesc() {
        return getPool().getConstant(descIndex, ConstUTF8.class);
    }

    @Override
    public VMReference load() {
        return null;
    }
}
