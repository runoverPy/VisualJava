package com.visualjava.data.constants;

import com.visualjava.data.CopyingDataInputStream;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class ConstantPool {
    private final Map<Integer, Constant> constants;
    private byte[] backend;

    private ConstantPool() {
        this.constants = new HashMap<>();
    }

    public static ConstantPool read(DataInputStream inputStream) throws IOException {
        ConstantPool constantPool = new ConstantPool();
        ConstPoolBuilder builder = constantPool.new ConstPoolBuilder();
        CopyingDataInputStream dis = new CopyingDataInputStream(inputStream);
        int constCount = inputStream.readUnsignedShort();
        while (builder.getIndex() < constCount) {
            int tag = dis.readUnsignedByte();
            if (builder.index == 37) System.out.println("Spoilers its " + tag);
            switch (tag) {
                case  1 -> ConstUTF8.read(dis, builder);
                case  3 -> ConstInt.read(dis, builder);
                case  4 -> ConstFloat.read(dis, builder);
                case  5 -> ConstLong.read(dis, builder);
                case  6 -> ConstDouble.read(dis, builder);
                case  7 -> ConstClass.read(dis, builder);
                case  8 -> ConstString.read(dis, builder);
                case  9 -> ConstFieldRef.read(dis, builder);
                case 10 -> ConstMethodRef.read(dis, builder);
                case 11 -> ConstInterfaceMethodRef.read(dis, builder);
                case 12 -> ConstNameType.read(dis, builder);
                case 15 -> ConstMethodHandle.read(dis, builder);
                case 16 -> ConstMethodType.read(dis, builder);
                case 17 -> ConstDynamic.read(dis, builder);
                case 18 -> ConstInvokeDynamic.read(dis, builder);
                case 19 -> ConstModule.read(dis, builder);
                case 20 -> ConstPackage.read(dis, builder);
            }
        }
        constantPool.backend = dis.getCopiedBytes();
        return constantPool;
    }

    public Constant getConstant(int index) {
        return constants.get(index);
    }

    public <T extends Constant> T getConstant(int index, Class<T> _class) {
        return (T) getConstant(index);
    }

    @Override
    public String toString() {
        String constValues = "";

        for (Map.Entry<Integer, Constant> entry : constants.entrySet()) {
            constValues += "\t" + entry.getKey() + ": " + entry.getValue() + "\n";
        }

        return "ConstantPool {\n" + constValues + '}';
    }

    public String toString(int depth) {
        StringBuilder constValues = new StringBuilder();

        for (Map.Entry<Integer, Constant> entry : constants.entrySet()) {
            constValues
              .append("\t".repeat(depth + 1))
              .append(entry.getKey())
              .append(": ")
              .append(entry.getValue())
              .append("\n");
        }

        return "ConstantPool {\n" + constValues + "\t".repeat(depth) + '}';
    }

    public class ConstPoolBuilder {
        private int index = 1;

        private ConstPoolBuilder() {}

        public ConstantPool getConstPool() {
            return ConstantPool.this;
        }

        public void submitConstant(Constant constant, int count) {
            constants.put(index, constant);
            index += count;
        }

        public void submitConstant(Constant constant) {
            submitConstant(constant, 1);
        }

        public int getIndex() {
            return index;
        }
    }
}
