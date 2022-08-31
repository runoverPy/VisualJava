package com.visualjava.data.constants;

import com.visualjava.types.VMReference;

import java.io.DataInputStream;
import java.io.IOException;

public class ConstMethodType extends Constant implements LoadableConst {
    private final ConstUTF8 desc;

    private ConstMethodType(ConstUTF8 desc) {
        this.desc = desc;
    }

    public static void read(DataInputStream dis, ConstantPool.ConstPoolBuilder poolBuilder) throws IOException {
        int desc_index = dis.readUnsignedShort();
        ConstUTF8 desc = poolBuilder.getConstPool().getConstant(desc_index, ConstUTF8.class);
        poolBuilder.submitConstant(new ConstMethodType(desc));
    }

    public String getDesc() {
        return desc.getValue();
    }

    @Override
    public VMReference load() {
        return null;
    }
}
