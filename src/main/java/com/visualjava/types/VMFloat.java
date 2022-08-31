package com.visualjava.types;

public class VMFloat extends VMPrimitive {
    private float value;

    public VMFloat(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    @Override
    public int getCompType() {
        return 1;
    }
}
