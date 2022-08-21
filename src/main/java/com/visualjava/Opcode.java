package com.visualjava;

import com.visualjava.parser.Instruction;

import java.util.HashMap;
import java.util.Map;

public enum Opcode {
    nop (0x00),
    aconst_null (0x01),
    iconst_m1 (0x02),
    iconst_0 (0x03),
    iconst_1 (0x04),
    iconst_2 (0x05),
    iconst_3 (0x06),
    iconst_4 (0x07),
    iconst_5 (0x08),
    lconst_0 (0x09),
    lconst_1 (0x0A),
    fconst_0 (0x0B),
    fconst_1 (0x0C),
    fconst_2 (0x0D),
    dconst_0 (0x0E),
    dconst_1 (0x0F),
    bipush (0x10),
    sipush (0x11),
    ldc (0x12),
    ldc_w (0x13),
    ldc2_w (0x14),
    iload (0x15),
    lload (0x16),
    fload (0x17),
    dload (0x18),
    aload (0x19),
    iload_0 (0x1A),
    iload_1 (0x1B),
    iload_2 (0x1C),
    iload_3 (0x1D),
    lload_0 (0x1E),
    lload_1 (0x1F),
    lload_2 (0x20),
    lload_3 (0x21),
    fload_0 (0x22),
    fload_1 (0x23),
    fload_2 (0x24),
    fload_3 (0x25),
    dload_0 (0x26),
    dload_1 (0x27),
    dload_2 (0x28),
    dload_3 (0x29),
    aload_0 (0x2A),
    aload_1 (0x2B),
    aload_2 (0x2C),
    aload_3 (0x2D),
    iaload (0x2E),
    laload (0x2F),
    faload (0x30),
    daload (0x31),
    aaload (0x32),
    baload (0x33),
    caload (0x34),
    saload (0x35),
    istore (0x36),
    lstore (0x37),
    fstore (0x38),
    dstore (0x39),
    astore (0x3A),
    istore_0 (0x3B),
    istore_1 (0x3C),
    istore_2 (0x3D),
    istore_3 (0x3E),
    lstore_0 (0x3F),
    lstore_1 (0x40),
    lstore_2 (0x41),
    lstore_3 (0x42),
    fstore_0 (0x43),
    fstore_1 (0x44),
    fstore_2 (0x45);

    private final Map<Integer, Opcode> _byteHandles = new HashMap<>(256);
    private final int code;

    Opcode(int code) {
        this.code = code;
        _byteHandles.put(code, this);

    }

    private interface OP {
        void operate(Executor.InstructionExecutor executor, Instruction instruction);
    }
}
