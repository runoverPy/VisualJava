package com.visualjava.data.constants;

import com.visualjava.types.VMInt;

import java.io.DataInputStream;
import java.io.IOException;

public class ConstInt extends Constant implements LoadableConst {
    private final int value;

    private ConstInt(int value) {
        this.value = value;
    }

    public static void read(DataInputStream dis, ConstantPool.ConstPoolBuilder poolBuilder) throws IOException {
        int value = dis.readInt();
        poolBuilder.submitConstant(new ConstInt(value));
    }

    public int getValue() {
        return value;
    }

    @Override
    public VMInt load() {
        return new VMInt(value);
    }
}
