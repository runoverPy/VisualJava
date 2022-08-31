package com.visualjava.types;

public class VMShort extends VMPrimitive {
    private short value;

    public VMShort(short value) {
        this.value = value;
    }

    public short getValue() {
        return value;
    }

    @Override
    public int getCompType() {
        return 1;
    }
}
