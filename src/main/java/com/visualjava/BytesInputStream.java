package com.visualjava;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class BytesInputStream extends InputStream {
    private final InputStream innerInputStream;

    public BytesInputStream(InputStream innerInputStream) {
        this.innerInputStream = innerInputStream;
    }

    @Override
    public int read() throws IOException {
        return innerInputStream.read();
    }

    public byte[] read(int bytes) throws IOException {
        byte[] byteArray = new byte[bytes];
        if (read(byteArray) == -1) throw new EOFException();
        return byteArray;
    }

    public int readInt(int bytes) throws IOException {
        int out = 0;
        for (byte b : read(bytes)) {
            out <<= 8;
            out |= b;
        }
        return out;
    }
}
