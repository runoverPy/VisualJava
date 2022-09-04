package com.visualjava.data.constants;

import java.io.DataInputStream;
import java.io.IOException;

public class ConstFieldRef extends Constant {
    private final int classIndex;
    private final int nameTypeIndex;

    private ConstFieldRef(ConstantPool pool, int classIndex, int nameTypeIndex) {
        super(pool);
        this.classIndex = classIndex;
        this.nameTypeIndex = nameTypeIndex;
    }

    public static void read(DataInputStream dis, ConstantPool.ConstPoolBuilder poolBuilder) throws IOException {
        int class_index = dis.readUnsignedShort();
        ConstClass clazz = poolBuilder.getConstPool().getConstant(class_index, ConstClass.class);
        int name_type_index = dis.readUnsignedShort();
        ConstNameType nameType = poolBuilder.getConstPool().getConstant(name_type_index, ConstNameType.class);
        poolBuilder.submitConstant(new ConstFieldRef(poolBuilder.getConstPool(), class_index, name_type_index));
    }

    public ConstClass getClazz() {
        return getPool().getConstant(classIndex, ConstClass.class);
    }

    public ConstNameType getNameType() {
        return getPool().getConstant(nameTypeIndex, ConstNameType.class);
    }
}
