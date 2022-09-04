package com.visualjava.data.constants;

import java.io.DataInputStream;
import java.io.IOException;

public class ConstNameType extends Constant {
    private final int nameIndex;
    private final int descIndex;

    private ConstNameType(ConstantPool pool, int nameIndex, int descIndex) {
        super(pool);
        this.nameIndex = nameIndex;
        this.descIndex = descIndex;
    }

    public static void read(DataInputStream dis, ConstantPool.ConstPoolBuilder poolBuilder) throws IOException {
        int name_index = dis.readUnsignedShort();
        int desc_index = dis.readUnsignedShort();
        poolBuilder.submitConstant(new ConstNameType(poolBuilder.getConstPool(), name_index, desc_index));
    }

    public ConstUTF8 getName() {
        return getPool().getConstant(nameIndex, ConstUTF8.class);
    }

    public ConstUTF8 getDesc() {
        return getPool().getConstant(descIndex, ConstUTF8.class);
    }

    @Override
    public String toString(int depth, boolean onNewLine) {
        return (onNewLine ? "\t".repeat(depth) : "") + "NameType {\n" +
          "\t".repeat(depth+1) + "name: " + getName().getValue() + ",\n" +
          "\t".repeat(depth+1) + "type: " + getDesc().getValue() + "\n" +
          "\t".repeat(depth) + "}";
    }
}
