package com.visualjava.data.constants;

import com.visualjava.types.VMReference;

import java.io.DataInputStream;
import java.io.IOException;

public class ConstString extends Constant implements LoadableConst {
    private final ConstUTF8 string;

    private ConstString(ConstUTF8 string) {
        this.string = string;
    }

    public static void read(DataInputStream dis, ConstantPool.ConstPoolBuilder poolBuilder) throws IOException {
        int string_index = dis.readUnsignedShort();
        ConstUTF8 string = poolBuilder.getConstPool().getConstant(string_index, ConstUTF8.class);
        poolBuilder.submitConstant(new ConstString(string));
    }

    public String getString() {
        return string.getValue();
    }

    @Override
    public VMReference load() {
        return null;
    }
}
