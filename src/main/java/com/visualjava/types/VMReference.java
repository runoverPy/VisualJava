package com.visualjava.types;

public abstract class VMReference extends VMType {
    Object deref() throws InstantiationException {
        return null;
    }

    @Override
    public int getCompType() {
        return 1;
    }

    public static boolean refEq(VMReference ref1, VMReference ref2) {
        return false;
    }
}
