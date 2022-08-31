package com.visualjava.data.constants;

import com.visualjava.types.VMReference;

import java.io.DataInputStream;
import java.io.IOException;

public class ConstDynamic extends Constant implements LoadableConst {
    private final int bootstrap_method_attr_index;
    private final ConstNameType nameType;

    private ConstDynamic(int bootstrap_method_attr_index, ConstNameType nameType) {
        this.bootstrap_method_attr_index = bootstrap_method_attr_index;
        this.nameType = nameType;
    }

    public static void read(DataInputStream dis, ConstantPool.ConstPoolBuilder poolBuilder) throws IOException {
        int bmai = dis.readUnsignedShort();
        int nameTypeIndex = dis.readUnsignedShort();
        ConstNameType nameType = poolBuilder.getConstPool().getConstant(nameTypeIndex, ConstNameType.class);
        poolBuilder.submitConstant(new ConstDynamic(bmai, nameType));
    }

    public int getBMAI() {
        return bootstrap_method_attr_index;
    }

    public ConstNameType getNameType() {
        return nameType;
    }

    @Override
    public VMReference load() {
        return null;
    }
}
