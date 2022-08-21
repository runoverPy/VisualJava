package com.visualjava.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BytesStream extends InputStream {


    public BytesStream(InputStream innerStream) {}

    @Override
    public int read() throws IOException {
        return 0;
    }
}
