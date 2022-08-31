package com.visualjava.types;

public class VMDouble extends VMPrimitive {
    private double value;

    public VMDouble(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public int getCompType() {
        return 2;
    }
}
