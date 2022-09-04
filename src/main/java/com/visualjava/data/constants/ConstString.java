package com.visualjava.data.constants;

import com.visualjava.types.VMReference;

import java.io.DataInputStream;
import java.io.IOException;

public class ConstString extends Constant implements LoadableConst {
    private final int string_index;

    private ConstString(ConstantPool pool, int string_index) {
        super(pool);
        this.string_index = string_index;
    }

    public static void read(DataInputStream dis, ConstantPool.ConstPoolBuilder poolBuilder) throws IOException {
        int string_index = dis.readUnsignedShort();
        poolBuilder.submitConstant(new ConstString(poolBuilder.getConstPool(), string_index));
    }

    public String getString() {
        return getPool().getConstant(string_index, ConstUTF8.class).getValue();
    }

    @Override
    public VMReference load() {
        return null;
    }

    @Override
    public String toString() {
        return super.toString() + ": \"" + getString() + "\"";
    }
}
