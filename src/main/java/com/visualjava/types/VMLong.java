package com.visualjava.types;

public class VMLong extends VMPrimitive {
    private long value;

    public VMLong(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    @Override
    public int getCompType() {
        return 2;
    }
}
