package com.visualjava.types;

public class VMChar extends VMPrimitive {
    private char value;

    public VMChar(char value) {
        this.value = value;
    }

    public char getCharValue() {
        return value;
    }

    @Override
    public int getCompType() {
        return 1;
    }
}
