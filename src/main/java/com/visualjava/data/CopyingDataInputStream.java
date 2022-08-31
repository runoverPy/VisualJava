package com.visualjava.data;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CopyingDataInputStream extends DataInputStream {
    private ByteArrayOutputStream copyStream;

    /**
     * Creates a DataInputStream that uses the specified
     * underlying InputStream.
     *
     * @param in the specified input stream
     */
    public CopyingDataInputStream(InputStream in) {
        super(in);
        copyStream = new ByteArrayOutputStream();
    }

    public byte[] getCopiedBytes() {
        return copyStream.toByteArray();
    }

    @Override
    public int read() throws IOException {
        int read = super.read();
        copyStream.write(read);
        return read;
    }
}
