package com.visualjava.data.constants;

import com.visualjava.types.VMReference;

import java.io.DataInputStream;
import java.io.IOException;

public class ConstMethodHandle extends Constant implements LoadableConst {
    private final Class<Constant> refKind;
    private final Constant reference;

    private ConstMethodHandle(Class<Constant> refKind, Constant reference) {
        this.refKind = refKind;
        this.reference = reference;
    }

    public static void read(DataInputStream dis, ConstantPool.ConstPoolBuilder poolBuilder) throws IOException {
        int reference_kind = dis.readUnsignedByte();
        int reference_index = dis.readUnsignedShort();
        poolBuilder.submitConstant(null);
    }

    @Override
    public VMReference load() {
        return null;
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
