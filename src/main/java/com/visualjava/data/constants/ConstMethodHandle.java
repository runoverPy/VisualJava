package com.visualjava.data.constants;

import com.visualjava.types.VMReference;

import java.io.DataInputStream;
import java.io.IOException;

public class ConstMethodHandle extends Constant implements LoadableConst {
    private final int reference_kind;
    private final int reference_index;

    private ConstMethodHandle(ConstantPool pool, int reference_kind, int reference_index) {
        super(pool);
        this.reference_kind = reference_kind;
        this.reference_index = reference_index;
    }

    public static void read(DataInputStream dis, ConstantPool.ConstPoolBuilder poolBuilder) throws IOException {
        int reference_kind = dis.readUnsignedByte();
        int reference_index = dis.readUnsignedShort();
        poolBuilder.submitConstant(new ConstMethodHandle(poolBuilder.getConstPool(), reference_kind, reference_index));
    }

    public int getReferenceKind() {
        return reference_kind;
    }

    public Constant getReference() {
        return getPool().getConstant(reference_index);
    }

    @Override
    public VMReference load() {
        return null;
    }

    public String toString(int depth, boolean onNewLine) {
        return (onNewLine ? "\t".repeat(depth) : "") + "{\n" +
          "\t".repeat(depth + 1) + "reference_kind: " + reference_kind + "\n" +
          "\t".repeat(depth + 1) + "reference: " + getReference().toString(depth+1, false) + "\n" +
          "\t".repeat(depth) + "}";
    }

    public enum RefKind {
        REF_getField (ConstFieldRef.class),
        REF_getStatic(ConstFieldRef.class),
        REF_putField (ConstFieldRef.class),
        REF_putStatic(ConstFieldRef.class),
        REF_invokeVirtual (ConstMethodRef.class),
        REF_invokeStatic (null),
        REF_invokeSpecial (null),
        REF_newInvokeSpecial (ConstMethodRef.class),
        REF_invokeInterface (null);

        public final Class<? extends Constant> refKindClass;

        RefKind(Class<? extends Constant> refKindClass) {
            this.refKindClass = refKindClass;
        }
    }
}
