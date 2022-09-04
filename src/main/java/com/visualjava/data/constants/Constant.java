package com.visualjava.data.constants;

public abstract class Constant {
    private final ConstantPool pool;

    protected Constant(ConstantPool pool) {
        this.pool = pool;
    }

    public ConstantPool getPool() {
        return pool;
    }

    public String toString(int depth) {
        return "\t".repeat(depth) + this;
    }

    public String toString(int depth, boolean onNewLine) {
        return onNewLine ? toString(depth) : toString();
    }
}
