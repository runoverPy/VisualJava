package com.visualjava.data;

import java.io.InputStream;

public class Instruction {
    private int opcode;
    private String mnemonic;

    public Instruction(InputStream dataStream) {

    }

    public String getMnemonic() {
        return mnemonic;
    }

    public Object getParam(String paramName) {
        return null;
    }

    public <T> T getParam(String paramName, Class<T> _class) {
        return (T) getParam(paramName);
    }
}
