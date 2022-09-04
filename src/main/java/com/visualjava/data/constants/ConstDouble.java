package com.visualjava.data.constants;

import com.visualjava.types.VMDouble;

import java.io.DataInputStream;
import java.io.IOException;

public class ConstDouble extends Constant implements LoadableConst {
    private final double value;

    private ConstDouble(ConstantPool pool, double value) {
        super(pool);
        this.value = value;
    }

    public static void read(DataInputStream dis, ConstantPool.ConstPoolBuilder poolBuilder) throws IOException {
        double value = dis.readDouble();
        poolBuilder.submitConstant(new ConstDouble(poolBuilder.getConstPool(), value), 2);
    }

    public double getValue() {
        return value;
    }

    @Override
    public VMDouble load() {
        return new VMDouble(value);
    }
}
