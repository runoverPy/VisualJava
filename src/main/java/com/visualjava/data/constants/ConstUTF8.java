package com.visualjava.data.constants;

import java.io.DataInputStream;
import java.io.IOException;

public class ConstUTF8 extends Constant {
    private final String value;

    private ConstUTF8(ConstantPool pool, String value) {
        super(pool);
        this.value = value;
    }

    public static void read(DataInputStream dis, ConstantPool.ConstPoolBuilder poolBuilder) throws IOException {
        String value = dis.readUTF();
        poolBuilder.submitConstant(new ConstUTF8(poolBuilder.getConstPool(), value));
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return super.toString() + ": " + getValue();
    }
}
