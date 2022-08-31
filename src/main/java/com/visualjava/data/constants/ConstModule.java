package com.visualjava.data.constants;

import java.io.DataInputStream;
import java.io.IOException;

public class ConstModule extends Constant {
    private final ConstUTF8 name;

    private ConstModule(ConstUTF8 name) {
        this.name = name;
    }

    public static void read(DataInputStream dis, ConstantPool.ConstPoolBuilder poolBuilder) throws IOException {
        int name_index = dis.readUnsignedShort();
        ConstUTF8 name = poolBuilder.getConstPool().getConstant(name_index, ConstUTF8.class);
        poolBuilder.submitConstant(new ConstModule(name));
    }

    public String getName() {
        return name.getValue();
    }
}
