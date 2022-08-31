package com.visualjava.types;

public class VMReturnAddress extends VMType {
    private int address;

    public VMReturnAddress(int address) {
        this.address = address;
    }

    public int getAddress() {
        return address;
    }

    @Override
    public int getCompType() {
        return 1;
    }
}
