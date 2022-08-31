package com.visualjava.types;

public class VMChar extends VMType {
    private char value;

    public VMChar(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    @Override
    public int getCompType() {
        return 1;
    }
}
