package com.visualjava.types;

public class VMInt extends VMPrimitive {
    private int value;

    public VMInt(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int getCompType() {
        return 1;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
