package com.visualjava.data.constants;

import com.visualjava.types.VMReference;

import java.io.DataInputStream;
import java.io.IOException;

public class ConstDynamic extends Constant implements LoadableConst {
    private final int bootstrap_method_attr_index;
    private final int nameTypeIndex;

    private ConstDynamic(ConstantPool pool, int bootstrap_method_attr_index, int nameTypeIndex) {
        super(pool);
        this.bootstrap_method_attr_index = bootstrap_method_attr_index;
        this.nameTypeIndex = nameTypeIndex;
    }

    public static void read(DataInputStream dis, ConstantPool.ConstPoolBuilder poolBuilder) throws IOException {
        int bmai = dis.readUnsignedShort();
        int nameTypeIndex = dis.readUnsignedShort();
        poolBuilder.submitConstant(new ConstDynamic(poolBuilder.getConstPool(), bmai, nameTypeIndex));
    }

    public int getBMAI() {
        return bootstrap_method_attr_index;
    }

    public ConstNameType getNameType() {
        return getPool().getConstant(nameTypeIndex, ConstNameType.class);
    }

    @Override
    public VMReference load() {
        return null;
    }
}
