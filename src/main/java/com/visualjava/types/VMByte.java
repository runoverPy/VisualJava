package com.visualjava.types;

public class VMByte extends VMPrimitive {
    private byte value;

    public VMByte(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    @Override
    public int getCompType() {
        return 1;
    }
}
