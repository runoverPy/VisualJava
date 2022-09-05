package com.visualjava.data.constants;

import java.io.DataInputStream;
import java.io.IOException;

public class ConstMethodRef extends Constant {
    private final int classIndex;
    private final int nameTypeIndex;

    protected ConstMethodRef(ConstantPool pool, int classIndex, int nameTypeIndex) {
        super(pool);
        this.classIndex = classIndex;
        this.nameTypeIndex = nameTypeIndex;
    }

    public static void read(DataInputStream dis, ConstantPool.ConstPoolBuilder poolBuilder) throws IOException {
        int class_index = dis.readUnsignedShort();
        int name_type_index = dis.readUnsignedShort();
        poolBuilder.submitConstant(new ConstMethodRef(poolBuilder.getConstPool(), class_index, name_type_index));
    }

    public ConstClass getClazz() {
        return getPool().getConstant(classIndex, ConstClass.class);
    }

    public ConstNameType getNameType() {
        return getPool().getConstant(nameTypeIndex, ConstNameType.class);
    }

    public String toString(int depth, boolean onNewLine) {
        return (onNewLine ? "\t".repeat(depth) : "") + "MethodRef {\n" +
          "\t".repeat(depth+1) + "class: " + getClazz().toString(depth+1, false) + ",\n" +
          "\t".repeat(depth+1) + "nameType: " + getNameType().toString(depth+1, false) + "\n" +
          "\t".repeat(depth) + "}";
    }
}
