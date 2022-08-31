package com.visualjava.data.constants;

import com.visualjava.types.VMReference;

import java.io.DataInputStream;
import java.io.IOException;

public class ConstClass extends Constant implements LoadableConst {
    private final ConstUTF8 name;

    private ConstClass(ConstUTF8 name) {
        this.name = name;
    }

    public static void read(DataInputStream dis, ConstantPool.ConstPoolBuilder poolBuilder) throws IOException {
        int name_index = dis.readUnsignedShort();
        ConstUTF8 name = poolBuilder.getConstPool().getConstant(name_index, ConstUTF8.class);
        poolBuilder.submitConstant(new ConstClass(name));
    }

    public String getName() {
        return name.getValue();
    }

    @Override
    public VMReference load() {
        return null;
    }
}
