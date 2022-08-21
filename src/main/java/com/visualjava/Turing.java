package com.visualjava;

import com.visualjava.parser.BytesStream;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class Turing {
    private byte[] instr;
    private int index;

    public Turing(ByteArrayInputStream innerStream) {
    }

    public void jump(int reloffset) {
        index += reloffset;
    }
}
