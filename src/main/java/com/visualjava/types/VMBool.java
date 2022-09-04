package com.visualjava.types;

public class VMBool extends VMByte {
    public VMBool(boolean value) {
        super((byte) (value ? 1 : 0));
    }
}
