package com.visualjava.data.constants;

import com.visualjava.types.VMLong;

import java.io.DataInputStream;
import java.io.IOException;

public class ConstLong extends Constant implements LoadableConst {
    private final long value;

    private ConstLong(long value) {
        this.value = value;
    }

    public static void read(DataInputStream dis, ConstantPool.ConstPoolBuilder poolBuilder) throws IOException {
        long value = dis.readLong();
        poolBuilder.submitConstant(new ConstLong(value), 2);
    }

    public long getValue() {
        return value;
    }

    @Override
    public VMLong load() {
        return new VMLong(value);
    }
}
