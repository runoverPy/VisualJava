package com.visualjava.data.constants;

import java.io.DataInputStream;
import java.io.IOException;

public class ConstNameType extends Constant {
    private final ConstUTF8 name;
    private final ConstUTF8 desc;

    private ConstNameType(ConstUTF8 name, ConstUTF8 desc) {
        this.name = name;
        this.desc = desc;
    }

    public static void read(DataInputStream dis, ConstantPool.ConstPoolBuilder poolBuilder) throws IOException {
        int name_index = dis.readUnsignedShort();
        ConstUTF8 name = poolBuilder.getConstPool().getConstant(name_index, ConstUTF8.class);
        int desc_index = dis.readUnsignedShort();
        ConstUTF8 desc = poolBuilder.getConstPool().getConstant(name_index, ConstUTF8.class);
        poolBuilder.submitConstant(new ConstNameType(name, desc));
    }

    public String getName() {
        return name.getValue();
    }

    public String getDesc() {
        return desc.getValue();
    }
}
