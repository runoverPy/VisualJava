package com.visualjava;

public interface OpcodeOperations {
    default void impl_nop(ExecutionContext context) {}
    void impl_aconst_null(ExecutionContext context);
    void impl_iconst_m1(ExecutionContext context);
    void impl_iconst_0(ExecutionContext context);
    void impl_iconst_1(ExecutionContext context);
    void impl_iconst_2(ExecutionContext context);
    void impl_iconst_3(ExecutionContext context);
    void impl_iconst_4(ExecutionContext context);
    void impl_iconst_5(ExecutionContext context);
    void impl_lconst_0(ExecutionContext context);
    void impl_lconst_1(ExecutionContext context);
    void impl_fconst_0(ExecutionContext context);
    void impl_fconst_1(ExecutionContext context);
    void impl_fconst_2(ExecutionContext context);
    void impl_dconst_0(ExecutionContext context);
    void impl_dconst_1(ExecutionContext context);
    void impl_bipush(ExecutionContext context);
    void impl_sipush(ExecutionContext context);
    void impl_ldc(ExecutionContext context);
    void impl_ldc_w(ExecutionContext context);
    void impl_ldc2_w(ExecutionContext context);
    void impl_iload(ExecutionContext context);
    void impl_lload(ExecutionContext context);
    void impl_fload(ExecutionContext context);
    void impl_dload(ExecutionContext context);
    void impl_aload(ExecutionContext context);
    void impl_iload_0(ExecutionContext context);
    void impl_iload_1(ExecutionContext context);
    void impl_iload_2(ExecutionContext context);
    void impl_iload_3(ExecutionContext context);
    void impl_lload_0(ExecutionContext context);
    void impl_lload_1(ExecutionContext context);
    void impl_lload_2(ExecutionContext context);
    void impl_lload_3(ExecutionContext context);
    void impl_fload_0(ExecutionContext context);
    void impl_fload_1(ExecutionContext context);
    void impl_fload_2(ExecutionContext context);
    void impl_fload_3(ExecutionContext context);
    void impl_dload_0(ExecutionContext context);
    void impl_dload_1(ExecutionContext context);
    void impl_dload_2(ExecutionContext context);
    void impl_dload_3(ExecutionContext context);
    void impl_aload_0(ExecutionContext context);
    void impl_aload_1(ExecutionContext context);
    void impl_aload_2(ExecutionContext context);
    void impl_aload_3(ExecutionContext context);
    void impl_iaload(ExecutionContext context);
    void impl_laload(ExecutionContext context);
    void impl_faload(ExecutionContext context);
    void impl_daload(ExecutionContext context);
    void impl_aaload(ExecutionContext context);
    void impl_baload(ExecutionContext context);
    void impl_caload(ExecutionContext context);
    void impl_saload(ExecutionContext context);
    void impl_istore(ExecutionContext context);
    void impl_lstore(ExecutionContext context);
    void impl_fstore(ExecutionContext context);
    void impl_dstore(ExecutionContext context);
    void impl_astore(ExecutionContext context);
    void impl_istore_0(ExecutionContext context);
    void impl_istore_1(ExecutionContext context);
    void impl_istore_2(ExecutionContext context);
    void impl_istore_3(ExecutionContext context);
    void impl_lstore_0(ExecutionContext context);
    void impl_lstore_1(ExecutionContext context);
    void impl_lstore_2(ExecutionContext context);
    void impl_lstore_3(ExecutionContext context);
    void impl_fstore_0(ExecutionContext context);
    void impl_fstore_1(ExecutionContext context);
    void impl_fstore_2(ExecutionContext context);
    void impl_fstore_3(ExecutionContext context);
    void impl_dstore_0(ExecutionContext context);
    void impl_dstore_1(ExecutionContext context);
    void impl_dstore_2(ExecutionContext context);
    void impl_dstore_3(ExecutionContext context);
    void impl_astore_0(ExecutionContext context);
    void impl_astore_1(ExecutionContext context);
    void impl_astore_2(ExecutionContext context);
    void impl_astore_3(ExecutionContext context);
    void impl_iastore(ExecutionContext context);
    void impl_lastore(ExecutionContext context);
    void impl_fastore(ExecutionContext context);
    void impl_dastore(ExecutionContext context);
    void impl_aastore(ExecutionContext context);
    void impl_bastore(ExecutionContext context);
    void impl_castore(ExecutionContext context);
    void impl_sastore(ExecutionContext context);
    void impl_pop(ExecutionContext context);
    void impl_pop2(ExecutionContext context);
    void impl_dup(ExecutionContext context);
    void impl_dup_x1(ExecutionContext context);
    void impl_dup_x2(ExecutionContext context);
    void impl_dup2(ExecutionContext context);
    void impl_dup2_x1(ExecutionContext context);
    void impl_dup2_x2(ExecutionContext context);
    void impl_swap(ExecutionContext context);
    void impl_iadd(ExecutionContext context);
    void impl_ladd(ExecutionContext context);
    void impl_fadd(ExecutionContext context);
    void impl_dadd(ExecutionContext context);
    void impl_isub(ExecutionContext context);
    void impl_lsub(ExecutionContext context);
    void impl_fsub(ExecutionContext context);
    void impl_dsub(ExecutionContext context);
    void impl_imul(ExecutionContext context);
    void impl_lmul(ExecutionContext context);
    void impl_fmul(ExecutionContext context);
    void impl_dmul(ExecutionContext context);
    void impl_idiv(ExecutionContext context);
    void impl_ldiv(ExecutionContext context);
    void impl_fdiv(ExecutionContext context);
    void impl_ddiv(ExecutionContext context);
    void impl_irem(ExecutionContext context);
    void impl_lrem(ExecutionContext context);
    void impl_frem(ExecutionContext context);
    void impl_drem(ExecutionContext context);
    void impl_ineg(ExecutionContext context);
    void impl_lneg(ExecutionContext context);
    void impl_fneg(ExecutionContext context);
    void impl_dneg(ExecutionContext context);
    void impl_ishl(ExecutionContext context);
    void impl_lshl(ExecutionContext context);
    void impl_ishr(ExecutionContext context);
    void impl_lshr(ExecutionContext context);
    void impl_iushr(ExecutionContext context);
    void impl_lushr(ExecutionContext context);
    void impl_iand(ExecutionContext context);
    void impl_land(ExecutionContext context);
    void impl_ior(ExecutionContext context);
    void impl_lor(ExecutionContext context);
    void impl_ixor(ExecutionContext context);
    void impl_lxor(ExecutionContext context);
    void impl_iinc(ExecutionContext context);
    void impl_i2l(ExecutionContext context);
    void impl_i2f(ExecutionContext context);
    void impl_i2d(ExecutionContext context);
    void impl_l2i(ExecutionContext context);
    void impl_l2f(ExecutionContext context);
    void impl_l2d(ExecutionContext context);
    void impl_f2i(ExecutionContext context);
    void impl_f2l(ExecutionContext context);
    void impl_f2d(ExecutionContext context);
    void impl_d2i(ExecutionContext context);
    void impl_d2l(ExecutionContext context);
    void impl_d2f(ExecutionContext context);
    void impl_i2b(ExecutionContext context);
    void impl_i2c(ExecutionContext context);
    void impl_i2s(ExecutionContext context);
    void impl_lcmp(ExecutionContext context);
    void impl_fcmpl(ExecutionContext context);
    void impl_fcmpg(ExecutionContext context);
    void impl_dcmpl(ExecutionContext context);
    void impl_dcmpg(ExecutionContext context);
    void impl_ifeq(ExecutionContext context);
    void impl_ifne(ExecutionContext context);
    void impl_iflt(ExecutionContext context);
    void impl_ifge(ExecutionContext context);
    void impl_ifgt(ExecutionContext context);
    void impl_ifle(ExecutionContext context);
    void impl_if_icmpeq(ExecutionContext context);
    void impl_if_icmpne(ExecutionContext context);
    void impl_if_icmplt(ExecutionContext context);
    void impl_if_icmpge(ExecutionContext context);
    void impl_if_icmpgt(ExecutionContext context);
    void impl_if_icmple(ExecutionContext context);
    void impl_if_acmpeq(ExecutionContext context);
    void impl_if_acmpne(ExecutionContext context);
    void impl_goto(ExecutionContext context);
    void impl_jsr(ExecutionContext context);
    void impl_ret(ExecutionContext context);
    void impl_tableswitch(ExecutionContext context);
    void impl_lookupswitch(ExecutionContext context);
    void impl_ireturn(ExecutionContext context);
    void impl_lreturn(ExecutionContext context);
    void impl_freturn(ExecutionContext context);
    void impl_dreturn(ExecutionContext context);
    void impl_areturn(ExecutionContext context);
    void impl_return(ExecutionContext context);
    void impl_getstatic(ExecutionContext context);
    void impl_putstatic(ExecutionContext context);
    void impl_getfield(ExecutionContext context);
    void impl_putfield(ExecutionContext context);
    void impl_invokevirtual(ExecutionContext context);
    void impl_invokespecial(ExecutionContext context);
    void impl_invokestatic(ExecutionContext context);
    void impl_invokeinterface(ExecutionContext context);
    void impl_invokedynamic(ExecutionContext context);
    void impl_new(ExecutionContext context);
    void impl_newarray(ExecutionContext context);
    void impl_anewarray(ExecutionContext context);
    void impl_arraylength(ExecutionContext context);
    void impl_athrow(ExecutionContext context);
    void impl_checkcast(ExecutionContext context);
    void impl_instanceof(ExecutionContext context);
    void impl_monitorenter(ExecutionContext context);
    void impl_monitorexit(ExecutionContext context);
    void impl_wide(ExecutionContext context);
    void impl_multianewarray(ExecutionContext context);
    void impl_ifnull(ExecutionContext context);
    void impl_ifnonnull(ExecutionContext context);
    void impl_goto_w(ExecutionContext context);
    void impl_jsr_w(ExecutionContext context);
    default void impl_breakpoint(ExecutionContext context) {}
    default void impl_impdep1(ExecutionContext context) {}
    default void impl_impdep2(ExecutionContext context) {}
}
