package com.visualjava.data.constants;

import com.visualjava.types.VMFloat;

import java.io.DataInputStream;
import java.io.IOException;

public class ConstFloat extends Constant implements LoadableConst {
    private final float value;

    private ConstFloat(float value) {
        this.value = value;
    }

    public static void read(DataInputStream dis, ConstantPool.ConstPoolBuilder poolBuilder) throws IOException {
        float value = dis.readFloat();
        poolBuilder.submitConstant(new ConstFloat(value));
    }

    public float getValue() {
        return value;
    }

    @Override
    public VMFloat load() {
        return new VMFloat(value);
    }
}
