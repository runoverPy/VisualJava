package com.visualjava.invoke;

import com.visualjava.data.constants.*;
import com.visualjava.types.*;
import com.visualjava.vm.VMMethod;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public final class Executor {
    public static void execute(ExecutionContext context) {
        try {
            InstructionExecutor.class.getDeclaredMethod(
              "impl_" + context.getInstr().getMnemonic(),
              ExecutionContext.class
            ).invoke(null, context);
            context.getPCHandler().execute();
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    
    private Executor() {
    }

    @SuppressWarnings("unused")
    private static class InstructionExecutor {
        public static void impl_aconst_null(ExecutionContext context) {
            context.frame().pshStack(new VMNullReference());
        }


        public static void impl_iconst_m1(ExecutionContext context) {
            context.frame().pshStack(new VMInt(-1));
        }


        public static void impl_iconst_0(ExecutionContext context) {
            context.frame().pshStack(new VMInt(0));
        }


        public static void impl_iconst_1(ExecutionContext context) {
            context.frame().pshStack(new VMInt(1));
        }


        public static void impl_iconst_2(ExecutionContext context) {
            context.frame().pshStack(new VMInt(2));
        }


        public static void impl_iconst_3(ExecutionContext context) {
            context.frame().pshStack(new VMInt(3));
        }


        public static void impl_iconst_4(ExecutionContext context) {
            context.frame().pshStack(new VMInt(4));
        }


        public static void impl_iconst_5(ExecutionContext context) {
            context.frame().pshStack(new VMInt(5));
        }


        public static void impl_lconst_0(ExecutionContext context) {
            context.frame().pshStack(new VMLong(0));
        }


        public static void impl_lconst_1(ExecutionContext context) {
            context.frame().pshStack(new VMLong(1));
        }


        public static void impl_fconst_0(ExecutionContext context) {
            context.frame().pshStack(new VMFloat(0));
        }


        public static void impl_fconst_1(ExecutionContext context) {
            context.frame().pshStack(new VMFloat(1));
        }


        public static void impl_fconst_2(ExecutionContext context) {
            context.frame().pshStack(new VMFloat(2));
        }


        public static void impl_dconst_0(ExecutionContext context) {
            context.frame().pshStack(new VMDouble(0));
        }


        public static void impl_dconst_1(ExecutionContext context) {
            context.frame().pshStack(new VMDouble(1));
        }


        public static void impl_bipush(ExecutionContext context) {
            int value = context.getInstr().getParam("byte");
            context.frame().pshStack(new VMInt(value));
        }


        public static void impl_sipush(ExecutionContext context) {
            int value = context.getInstr().getParam("short");
            context.frame().pshStack(new VMInt(value));
        }


        public static void impl_ldc(ExecutionContext context) {
            int index = context.getInstr().getParam("index");
            LoadableConst constValue = (LoadableConst) context.getConstPool().getConstant(index);
            assert !(constValue instanceof ConstLong || constValue instanceof ConstDouble);
            VMType value = constValue.load();
            context.frame().pshStack(value);
        }


        public static void impl_ldc_w(ExecutionContext context) {
            int index = context.getInstr().getParam("index");
            LoadableConst constValue = (LoadableConst) context.getConstPool().getConstant(index);
            assert !(constValue instanceof ConstLong || constValue instanceof ConstDouble);
            VMType value = constValue.load();
            context.frame().pshStack(value);
        }


        public static void impl_ldc2_w(ExecutionContext context) {
            int index = context.getInstr().getParam("index");
            LoadableConst constValue = (LoadableConst) context.getConstPool().getConstant(index);
            assert !(constValue instanceof ConstInt || constValue instanceof ConstFloat);
            VMType value = constValue.load();
            context.frame().pshStack(value);
        }


        public static void impl_iload(ExecutionContext context) {
            int index = context.getInstr().getParam("index");
            VMType value = context.frame().getLocal(index);
            assert value instanceof VMInt;
            context.frame().pshStack(value);
        }


        public static void impl_lload(ExecutionContext context) {
            int index = context.getInstr().getParam("index");
            VMType value = context.frame().getLocal(index);
            assert value instanceof VMLong;
            context.frame().pshStack(value);
        }


        public static void impl_fload(ExecutionContext context) {
            int index = context.getInstr().getParam("index");
            VMType value = context.frame().getLocal(index);
            assert value instanceof VMFloat;
            context.frame().pshStack(value);
        }


        public static void impl_dload(ExecutionContext context) {
            int index = context.getInstr().getParam("index");
            VMType value = context.frame().getLocal(index);
            assert value instanceof VMDouble;
            context.frame().pshStack(value);
        }


        public static void impl_aload(ExecutionContext context) {
            int index = context.getInstr().getParam("index");
            VMType value = context.frame().getLocal(index);
            assert value instanceof VMReference;
            context.frame().pshStack(value);
        }


        public static void impl_iload_0(ExecutionContext context) {
            VMType value = context.frame().getLocal(0);
            assert value instanceof VMInt;
            context.frame().pshStack(value);
        }


        public static void impl_iload_1(ExecutionContext context) {
            VMType value = context.frame().getLocal(1);
            assert value instanceof VMInt;
            context.frame().pshStack(value);
        }


        public static void impl_iload_2(ExecutionContext context) {
            VMType value = context.frame().getLocal(2);
            assert value instanceof VMInt;
            context.frame().pshStack(value);
        }


        public static void impl_iload_3(ExecutionContext context) {
            VMType value = context.frame().getLocal(3);
            assert value instanceof VMInt;
            context.frame().pshStack(value);
        }


        public static void impl_lload_0(ExecutionContext context) {
            VMType value = context.frame().getLocal(0);
            assert value instanceof VMLong;
            context.frame().pshStack(value);
        }


        public static void impl_lload_1(ExecutionContext context) {
            VMType value = context.frame().getLocal(1);
            assert value instanceof VMLong;
            context.frame().pshStack(value);
        }


        public static void impl_lload_2(ExecutionContext context) {
            VMType value = context.frame().getLocal(2);
            assert value instanceof VMLong;
            context.frame().pshStack(value);
        }


        public static void impl_lload_3(ExecutionContext context) {
            VMType value = context.frame().getLocal(3);
            assert value instanceof VMLong;
            context.frame().pshStack(value);
        }


        public static void impl_fload_0(ExecutionContext context) {
            VMType value = context.frame().getLocal(0);
            assert value instanceof VMFloat;
            context.frame().pshStack(value);
        }


        public static void impl_fload_1(ExecutionContext context) {
            VMType value = context.frame().getLocal(1);
            assert value instanceof VMFloat;
            context.frame().pshStack(value);
        }


        public static void impl_fload_2(ExecutionContext context) {
            VMType value = context.frame().getLocal(2);
            assert value instanceof VMFloat;
            context.frame().pshStack(value);
        }


        public static void impl_fload_3(ExecutionContext context) {
            VMType value = context.frame().getLocal(3);
            assert value instanceof VMFloat;
            context.frame().pshStack(value);
        }


        public static void impl_dload_0(ExecutionContext context) {
            VMType value = context.frame().getLocal(0);
            assert value instanceof VMDouble;
            context.frame().pshStack(value);
        }


        public static void impl_dload_1(ExecutionContext context) {
            VMType value = context.frame().getLocal(1);
            assert value instanceof VMDouble;
            context.frame().pshStack(value);
        }


        public static void impl_dload_2(ExecutionContext context) {
            VMType value = context.frame().getLocal(2);
            assert value instanceof VMDouble;
            context.frame().pshStack(value);
        }


        public static void impl_dload_3(ExecutionContext context) {
            VMType value = context.frame().getLocal(3);
            assert value instanceof VMDouble;
            context.frame().pshStack(value);
        }


        public static void impl_aload_0(ExecutionContext context) {
            VMType value = context.frame().getLocal(0);
            assert value instanceof VMReference;
            context.frame().pshStack(value);
        }


        public static void impl_aload_1(ExecutionContext context) {
            VMType value = context.frame().getLocal(1);
            assert value instanceof VMReference;
            context.frame().pshStack(value);
        }


        public static void impl_aload_2(ExecutionContext context) {
            VMType value = context.frame().getLocal(2);
            assert value instanceof VMReference;
            context.frame().pshStack(value);
        }


        public static void impl_aload_3(ExecutionContext context) {
            VMType value = context.frame().getLocal(3);
            assert value instanceof VMReference;
            context.frame().pshStack(value);
        }


        public static void impl_iaload(ExecutionContext context) {
            VMInt index = context.frame().popStack();
            VMArrayReference arrayRef = context.frame().popStack();
            VMType value = context.getVMMemory().getArrayField(arrayRef, index.getValue());
            assert value instanceof VMInt;
            context.frame().pshStack(value);
        }


        public static void impl_laload(ExecutionContext context) {
            VMInt index = context.frame().popStack();
            VMArrayReference arrayRef = context.frame().popStack();
            VMType value = context.getVMMemory().getArrayField(arrayRef, index.getValue());
            assert value instanceof VMLong;
            context.frame().pshStack(value);
        }


        public static void impl_faload(ExecutionContext context) {
            VMInt index = context.frame().popStack();
            VMArrayReference arrayRef = context.frame().popStack();
            VMType value = context.getVMMemory().getArrayField(arrayRef, index.getValue());
            assert value instanceof VMFloat;
            context.frame().pshStack(value);
        }


        public static void impl_daload(ExecutionContext context) {
            VMInt index = context.frame().popStack();
            VMArrayReference arrayRef = context.frame().popStack();
            VMType value = context.getVMMemory().getArrayField(arrayRef, index.getValue());
            assert value instanceof VMDouble;
            context.frame().pshStack(value);
        }


        public static void impl_aaload(ExecutionContext context) {
            VMInt index = context.frame().popStack();
            VMArrayReference arrayRef = context.frame().popStack();
            VMType value = context.getVMMemory().getArrayField(arrayRef, index.getValue());
            assert value instanceof VMReference;
            context.frame().pshStack(value);
        }


        public static void impl_baload(ExecutionContext context) {
            VMInt index = context.frame().popStack();
            VMArrayReference arrayRef = context.frame().popStack();
            VMType value = context.getVMMemory().getArrayField(arrayRef, index.getValue());
            assert value instanceof VMByte;
            context.frame().pshStack(value);
        }


        public static void impl_caload(ExecutionContext context) {
            VMInt index = context.frame().popStack();
            VMArrayReference arrayRef = context.frame().popStack();
            VMType value = context.getVMMemory().getArrayField(arrayRef, index.getValue());
            assert value instanceof VMChar;
            context.frame().pshStack(value);
        }


        public static void impl_saload(ExecutionContext context) {
            VMInt index = context.frame().popStack();
            VMArrayReference arrayRef = context.frame().popStack();
            VMType value = context.getVMMemory().getArrayField(arrayRef, index.getValue());
            assert value instanceof VMShort;
            context.frame().pshStack(value);
        }


        public static void impl_istore(ExecutionContext context) {
            int index = context.getInstr().getParam("index");
            VMType value = context.frame().popStack();
            assert value instanceof VMInt;
            context.frame().putLocal(index, value);
        }


        public static void impl_lstore(ExecutionContext context) {
            int index = context.getInstr().getParam("index");
            VMType value = context.frame().popStack();
            assert value instanceof VMLong;
            context.frame().putLocal(index, value);
        }


        public static void impl_fstore(ExecutionContext context) {
            int index = context.getInstr().getParam("index");
            VMType value = context.frame().popStack();
            assert value instanceof VMFloat;
            context.frame().putLocal(index, value);
        }


        public static void impl_dstore(ExecutionContext context) {
            int index = context.getInstr().getParam("index");
            VMType value = context.frame().popStack();
            assert value instanceof VMDouble;
            context.frame().putLocal(index, value);
        }


        public static void impl_astore(ExecutionContext context) {
            int index = context.getInstr().getParam("index");
            VMType value = context.frame().popStack();
            assert value instanceof VMReference;
            context.frame().putLocal(index, value);
        }


        public static void impl_istore_0(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMInt;
            context.frame().putLocal(0, value);
        }


        public static void impl_istore_1(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMInt;
            context.frame().putLocal(1, value);
        }


        public static void impl_istore_2(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMInt;
            context.frame().putLocal(2, value);
        }


        public static void impl_istore_3(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMInt;
            context.frame().putLocal(3, value);
        }


        public static void impl_lstore_0(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMLong;
            context.frame().putLocal(0, value);
        }


        public static void impl_lstore_1(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMLong;
            context.frame().putLocal(1, value);
        }


        public static void impl_lstore_2(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMLong;
            context.frame().putLocal(2, value);
        }


        public static void impl_lstore_3(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMLong;
            context.frame().putLocal(3, value);
        }


        public static void impl_fstore_0(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMFloat;
            context.frame().putLocal(0, value);
        }


        public static void impl_fstore_1(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMFloat;
            context.frame().putLocal(1, value);
        }


        public static void impl_fstore_2(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMFloat;
            context.frame().putLocal(2, value);
        }


        public static void impl_fstore_3(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMFloat;
            context.frame().putLocal(3, value);
        }


        public static void impl_dstore_0(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMDouble;
            context.frame().putLocal(0, value);
        }


        public static void impl_dstore_1(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMDouble;
            context.frame().putLocal(1, value);
        }


        public static void impl_dstore_2(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMDouble;
            context.frame().putLocal(2, value);
        }


        public static void impl_dstore_3(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMDouble;
            context.frame().putLocal(3, value);
        }


        public static void impl_astore_0(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMReference;
            context.frame().putLocal(0, value);
        }


        public static void impl_astore_1(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMReference;
            context.frame().putLocal(1, value);
        }


        public static void impl_astore_2(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMReference;
            context.frame().putLocal(2, value);
        }


        public static void impl_astore_3(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMReference;
            context.frame().putLocal(3, value);
        }


        public static void impl_iastore(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMInt;
            VMInt index = context.frame().popStack();
            VMArrayReference arrayRef = context.frame().popStack();
            context.getVMMemory().putArrayField(arrayRef, index.getValue(), value);
        }


        public static void impl_lastore(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMLong;
            VMInt index = context.frame().popStack();
            VMArrayReference arrayRef = context.frame().popStack();
            context.getVMMemory().putArrayField(arrayRef, index.getValue(), value);
        }


        public static void impl_fastore(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMFloat;
            VMInt index = context.frame().popStack();
            VMArrayReference arrayRef = context.frame().popStack();
            context.getVMMemory().putArrayField(arrayRef, index.getValue(), value);
        }


        public static void impl_dastore(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMDouble;
            VMInt index = context.frame().popStack();
            VMArrayReference arrayRef = context.frame().popStack();
            context.getVMMemory().putArrayField(arrayRef, index.getValue(), value);
        }


        public static void impl_aastore(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMReference;
            VMInt index = context.frame().popStack();
            VMArrayReference arrayRef = context.frame().popStack();
            context.getVMMemory().putArrayField(arrayRef, index.getValue(), value);
        }


        public static void impl_bastore(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMByte;
            VMInt index = context.frame().popStack();
            VMArrayReference arrayRef = context.frame().popStack();
            context.getVMMemory().putArrayField(arrayRef, index.getValue(), value);
        }


        public static void impl_castore(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMChar;
            VMInt index = context.frame().popStack();
            VMArrayReference arrayRef = context.frame().popStack();
            context.getVMMemory().putArrayField(arrayRef, index.getValue(), value);
        }


        public static void impl_sastore(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMShort;
            VMInt index = context.frame().popStack();
            VMArrayReference arrayRef = context.frame().popStack();
            context.getVMMemory().putArrayField(arrayRef, index.getValue(), value);
        }


        public static void impl_pop(ExecutionContext context) {
            context.frame().popStack();
        }


        public static void impl_pop2(ExecutionContext context) {
            VMType value1 = context.frame().popStack();
            if (value1.getCompType() == 1)
                context.frame().popStack();
        }


        public static void impl_dup(ExecutionContext context) {
            VMType value = context.frame().popStack();
            context.frame().pshStack(value);
            context.frame().pshStack(value);
        }


        public static void impl_dup_x1(ExecutionContext context) {
            VMType value1 = context.frame().popStack();
            VMType value2 = context.frame().popStack();
            context.frame().pshStack(value1);
            context.frame().pshStack(value2);
            context.frame().pshStack(value1);
        }


        public static void impl_dup_x2(ExecutionContext context) {
            VMType value1 = context.frame().popStack();
            VMType value2 = context.frame().popStack();
            if (value2.getCompType() == 1) {
                VMType value3 = context.frame().popStack();
                context.frame().pshStack(value1);
                context.frame().pshStack(value3);
            } else {
                context.frame().pshStack(value1);
            }
            context.frame().pshStack(value2);
            context.frame().pshStack(value1);
        }


        public static void impl_dup2(ExecutionContext context) {
            VMType value1 = context.frame().popStack();
            VMType value2 = context.frame().popStack();
            context.frame().pshStack(value2);
            context.frame().pshStack(value1);
            context.frame().pshStack(value2);
            context.frame().pshStack(value1);
        }


        public static void impl_dup2_x1(ExecutionContext context) {
            VMType value1 = context.frame().popStack();
            VMType value2 = context.frame().popStack();
            if (value1.getCompType() == 1) {
                VMType value3 = context.frame().popStack();
                context.frame().pshStack(value2);
                context.frame().pshStack(value1);
                context.frame().pshStack(value3);
            } else {
                context.frame().pshStack(value1);
            }
            context.frame().pshStack(value2);
            context.frame().pshStack(value1);
        }


        public static void impl_dup2_x2(ExecutionContext context) {
            VMType value1 = context.frame().popStack();
            VMType value2 = context.frame().popStack();
            if (value2.getCompType() == 2) {
                context.frame().pshStack(value1);
            } else {
                VMType value3 = context.frame().popStack();
                if (value1.getCompType() == 2) {
                    context.frame().pshStack(value1);
                    context.frame().pshStack(value3);
                } else if (value3.getCompType() == 2) {
                    context.frame().pshStack(value2);
                    context.frame().pshStack(value1);
                    context.frame().pshStack(value3);
                } else {
                    VMType value4 = context.frame().popStack();
                    context.frame().pshStack(value2);
                    context.frame().pshStack(value1);
                    context.frame().pshStack(value4);
                }
                context.frame().pshStack(value3);
            }
            context.frame().pshStack(value2);
            context.frame().pshStack(value1);
        }


        public static void impl_swap(ExecutionContext context) {
            VMType value1 = context.frame().popStack();
            VMType value2 = context.frame().popStack();
            context.frame().pshStack(value1);
            context.frame().pshStack(value2);
        }


        public static void impl_iadd(ExecutionContext context) {
            VMInt value2 = context.frame().popStack();
            VMInt value1 = context.frame().popStack();
            VMInt result = new VMInt(value1.getValue() + value2.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_ladd(ExecutionContext context) {
            VMLong value2 = context.frame().popStack();
            VMLong value1 = context.frame().popStack();
            VMLong result = new VMLong(value1.getValue() + value2.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_fadd(ExecutionContext context) {
            VMFloat value2 = context.frame().popStack();
            VMFloat value1 = context.frame().popStack();
            VMFloat result = new VMFloat(value1.getValue() + value2.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_dadd(ExecutionContext context) {
            VMDouble value2 = context.frame().popStack();
            VMDouble value1 = context.frame().popStack();
            VMDouble result = new VMDouble(value1.getValue() + value2.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_isub(ExecutionContext context) {
            VMInt value2 = context.frame().popStack();
            VMInt value1 = context.frame().popStack();
            VMInt result = new VMInt(value1.getValue() - value2.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_lsub(ExecutionContext context) {
            VMLong value2 = context.frame().popStack();
            VMLong value1 = context.frame().popStack();
            VMLong result = new VMLong(value1.getValue() - value2.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_fsub(ExecutionContext context) {
            VMFloat value2 = context.frame().popStack();
            VMFloat value1 = context.frame().popStack();
            VMFloat result = new VMFloat(value1.getValue() - value2.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_dsub(ExecutionContext context) {
            VMDouble value2 = context.frame().popStack();
            VMDouble value1 = context.frame().popStack();
            VMDouble result = new VMDouble(value1.getValue() - value2.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_imul(ExecutionContext context) {
            VMInt value2 = context.frame().popStack();
            VMInt value1 = context.frame().popStack();
            VMInt result = new VMInt(value1.getValue() * value2.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_lmul(ExecutionContext context) {
            VMLong value2 = context.frame().popStack();
            VMLong value1 = context.frame().popStack();
            VMLong result = new VMLong(value1.getValue() * value2.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_fmul(ExecutionContext context) {
            VMFloat value2 = context.frame().popStack();
            VMFloat value1 = context.frame().popStack();
            VMFloat result = new VMFloat(value1.getValue() * value2.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_dmul(ExecutionContext context) {
            VMDouble value2 = context.frame().popStack();
            VMDouble value1 = context.frame().popStack();
            VMDouble result = new VMDouble(value1.getValue() * value2.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_idiv(ExecutionContext context) {
            VMInt value2 = context.frame().popStack();
            VMInt value1 = context.frame().popStack();
            VMInt result = new VMInt(value1.getValue() / value2.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_ldiv(ExecutionContext context) {
            VMLong value2 = context.frame().popStack();
            VMLong value1 = context.frame().popStack();
            VMLong result = new VMLong(value1.getValue() / value2.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_fdiv(ExecutionContext context) {
            VMFloat value2 = context.frame().popStack();
            VMFloat value1 = context.frame().popStack();
            VMFloat result = new VMFloat(value1.getValue() / value2.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_ddiv(ExecutionContext context) {
            VMDouble value2 = context.frame().popStack();
            VMDouble value1 = context.frame().popStack();
            VMDouble result = new VMDouble(value1.getValue() / value2.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_irem(ExecutionContext context) {
            VMInt value2 = context.frame().popStack();
            VMInt value1 = context.frame().popStack();
            VMInt result = new VMInt(value1.getValue() % value2.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_lrem(ExecutionContext context) {
            VMLong value2 = context.frame().popStack();
            VMLong value1 = context.frame().popStack();
            VMLong result = new VMLong(value1.getValue() % value2.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_frem(ExecutionContext context) {
            VMFloat value2 = context.frame().popStack();
            VMFloat value1 = context.frame().popStack();
            VMFloat result = new VMFloat(value1.getValue() % value2.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_drem(ExecutionContext context) {
            VMDouble value2 = context.frame().popStack();
            VMDouble value1 = context.frame().popStack();
            VMDouble result = new VMDouble(value1.getValue() % value2.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_ineg(ExecutionContext context) {
            VMInt value = context.frame().popStack();
            VMInt result = new VMInt(-value.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_lneg(ExecutionContext context) {
            VMLong value = context.frame().popStack();
            VMLong result = new VMLong(-value.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_fneg(ExecutionContext context) {
            VMFloat value = context.frame().popStack();
            VMFloat result = new VMFloat(-value.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_dneg(ExecutionContext context) {
            VMDouble value = context.frame().popStack();
            VMDouble result = new VMDouble(-value.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_ishl(ExecutionContext context) {
            VMInt value2 = context.frame().popStack();
            VMInt value1 = context.frame().popStack();
            VMInt result = new VMInt(value1.getValue() << (value2.getValue() & 0x1f));
            context.frame().pshStack(result);

        }


        public static void impl_lshl(ExecutionContext context) {
            VMInt value2 = context.frame().popStack();
            VMLong value1 = context.frame().popStack();
            VMLong result = new VMLong(value1.getValue() << (value2.getValue() & 0x3f));
            context.frame().pshStack(result);
        }


        public static void impl_ishr(ExecutionContext context) {
            VMInt value2 = context.frame().popStack();
            VMInt value1 = context.frame().popStack();
            VMInt result = new VMInt(value1.getValue() >> (value2.getValue() & 0x1f));
            context.frame().pshStack(result);
        }


        public static void impl_lshr(ExecutionContext context) {
            VMInt value2 = context.frame().popStack();
            VMLong value1 = context.frame().popStack();
            VMLong result = new VMLong(value1.getValue() >> (value2.getValue() & 0x3f));
            context.frame().pshStack(result);
        }


        public static void impl_iushr(ExecutionContext context) {
            VMInt value2 = context.frame().popStack();
            VMInt value1 = context.frame().popStack();
            int value = value1.getValue() >> (value2.getValue() & 0x1f);
            if (value < 0) value += 2 << ~(value2.getValue() & 0x1f);
            VMInt result = new VMInt(value);
            context.frame().pshStack(result);
        }


        public static void impl_lushr(ExecutionContext context) {
            VMInt value2 = context.frame().popStack();
            VMLong value1 = context.frame().popStack();
            long value = value1.getValue() >> (value2.getValue() & 0x3f);
            if (value < 0) value += 2L << ~(value2.getValue() & 0x3f);
            VMLong result = new VMLong(value);
            context.frame().pshStack(result);
        }


        public static void impl_iand(ExecutionContext context) {
            VMInt value2 = context.frame().popStack();
            VMInt value1 = context.frame().popStack();
            VMInt result = new VMInt(value1.getValue() & value2.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_land(ExecutionContext context) {
            VMLong value2 = context.frame().popStack();
            VMLong value1 = context.frame().popStack();
            VMLong result = new VMLong(value1.getValue() & value2.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_ior(ExecutionContext context) {
            VMInt value2 = context.frame().popStack();
            VMInt value1 = context.frame().popStack();
            VMInt result = new VMInt(value1.getValue() | value2.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_lor(ExecutionContext context) {
            VMLong value2 = context.frame().popStack();
            VMLong value1 = context.frame().popStack();
            VMLong result = new VMLong(value1.getValue() | value2.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_ixor(ExecutionContext context) {
            VMInt value2 = context.frame().popStack();
            VMInt value1 = context.frame().popStack();
            VMInt result = new VMInt(value1.getValue() ^ value2.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_lxor(ExecutionContext context) {
            VMLong value2 = context.frame().popStack();
            VMLong value1 = context.frame().popStack();
            VMLong result = new VMLong(value1.getValue() ^ value2.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_iinc(ExecutionContext context) {
            int index = context.getInstr().getParam("index");
            int incrv = context.getInstr().getParam("const");
            VMInt value = context.frame().getLocal(index);
            value = new VMInt(value.getValue() + incrv);
            context.frame().putLocal(index, value);
        }


        public static void impl_i2l(ExecutionContext context) {
            VMInt value = context.frame().popStack();
            VMLong result = new VMLong(value.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_i2f(ExecutionContext context) {
            VMInt value = context.frame().popStack();
            VMFloat result = new VMFloat((float) value.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_i2d(ExecutionContext context) {
            VMInt value = context.frame().popStack();
            VMDouble result = new VMDouble(value.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_l2i(ExecutionContext context) {
            VMLong value = context.frame().popStack();
            VMInt result = new VMInt((int) value.getValue());
            context.frame().pshStack(result);

        }


        public static void impl_l2f(ExecutionContext context) {
            VMLong value = context.frame().popStack();
            VMFloat result = new VMFloat((float) value.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_l2d(ExecutionContext context) {
            VMLong value = context.frame().popStack();
            VMDouble result = new VMDouble((double) value.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_f2i(ExecutionContext context) {
            VMFloat value = context.frame().popStack();
            VMInt result = new VMInt((int) value.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_f2l(ExecutionContext context) {
            VMFloat value = context.frame().popStack();
            VMLong result = new VMLong((long) value.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_f2d(ExecutionContext context) {
            VMFloat value = context.frame().popStack();
            VMDouble result = new VMDouble(value.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_d2i(ExecutionContext context) {
            VMDouble value = context.frame().popStack();
            VMInt result = new VMInt((int) value.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_d2l(ExecutionContext context) {
            VMDouble value = context.frame().popStack();
            VMLong result = new VMLong((long) value.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_d2f(ExecutionContext context) {
            VMDouble value = context.frame().popStack();
            VMFloat result = new VMFloat((float) value.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_i2b(ExecutionContext context) {
            VMInt value = context.frame().popStack();
            VMByte result = new VMByte((byte) value.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_i2c(ExecutionContext context) {
            VMInt value = context.frame().popStack();
            VMChar result = new VMChar((char) value.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_i2s(ExecutionContext context) {
            VMInt value = context.frame().popStack();
            VMShort result = new VMShort((short) value.getValue());
            context.frame().pshStack(result);
        }


        public static void impl_lcmp(ExecutionContext context) {
            VMLong value2 = context.frame().popStack();
            VMLong value1 = context.frame().popStack();
            VMInt result = new VMInt(Long.compare(value1.getValue(), value2.getValue()));
            context.frame().pshStack(result);
        }


        public static void impl_fcmpl(ExecutionContext context) {
            VMFloat value2 = context.frame().popStack();
            VMFloat value1 = context.frame().popStack();
            VMInt result;
            if (Float.isNaN(value1.getValue()) || Float.isNaN(value2.getValue())) {
                result = new VMInt(-1);
            } else {
                result = new VMInt(Float.compare(value1.getValue(), value2.getValue()));
            }
            context.frame().pshStack(result);
        }


        public static void impl_fcmpg(ExecutionContext context) {
            VMFloat value2 = context.frame().popStack();
            VMFloat value1 = context.frame().popStack();
            VMInt result;
            if (Float.isNaN(value1.getValue()) || Float.isNaN(value2.getValue())) {
                result = new VMInt(1);
            } else {
                result = new VMInt(Float.compare(value1.getValue(), value2.getValue()));
            }
            context.frame().pshStack(result);
        }


        public static void impl_dcmpl(ExecutionContext context) {
            VMDouble value2 = context.frame().popStack();
            VMDouble value1 = context.frame().popStack();
            VMInt result;
            if (Double.isNaN(value1.getValue()) || Double.isNaN(value2.getValue())) {
                result = new VMInt(-1);
            } else {
                result = new VMInt(Double.compare(value1.getValue(), value2.getValue()));
            }
            context.frame().pshStack(result);
        }


        public static void impl_dcmpg(ExecutionContext context) {
            VMDouble value2 = context.frame().popStack();
            VMDouble value1 = context.frame().popStack();
            VMInt result;
            if (Double.isNaN(value1.getValue()) || Double.isNaN(value2.getValue())) {
                result = new VMInt(1);
            } else {
                result = new VMInt(Double.compare(value1.getValue(), value2.getValue()));
            }
            context.frame().pshStack(result);
        }


        public static void impl_ifeq(ExecutionContext context) {
            VMInt value = context.frame().popStack();
            int branch = context.getInstr().getParam("branch");
            if (value.getValue() == 0)
                context.getPCHandler().setRelativeOffset(branch);
        }


        public static void impl_ifne(ExecutionContext context) {
            VMInt value = context.frame().popStack();
            int branch = context.getInstr().getParam("branch");
            if (value.getValue() != 0)
                context.getPCHandler().setRelativeOffset(branch);
        }


        public static void impl_iflt(ExecutionContext context) {
            VMInt value = context.frame().popStack();
            int branch = context.getInstr().getParam("branch");
            if (value.getValue() < 0)
                context.getPCHandler().setRelativeOffset(branch);
        }


        public static void impl_ifge(ExecutionContext context) {
            VMInt value = context.frame().popStack();
            int branch = context.getInstr().getParam("branch");
            if (value.getValue() >= 0)
                context.getPCHandler().setRelativeOffset(branch);
        }


        public static void impl_ifgt(ExecutionContext context) {
            VMInt value = context.frame().popStack();
            int branch = context.getInstr().getParam("branch");
            if (value.getValue() > 0)
                context.getPCHandler().setRelativeOffset(branch);
        }


        public static void impl_ifle(ExecutionContext context) {
            VMInt value = context.frame().popStack();
            int branch = context.getInstr().getParam("branch");
            if (value.getValue() <= 0)
                context.getPCHandler().setRelativeOffset(branch);
        }


        public static void impl_if_icmpeq(ExecutionContext context) {
            VMInt value2 = context.frame().popStack();
            VMInt value1 = context.frame().popStack();
            int branch = context.getInstr().getParam("branch");
            if (value1.getValue() == value2.getValue())
                context.getPCHandler().setRelativeOffset(branch);
        }


        public static void impl_if_icmpne(ExecutionContext context) {
            VMInt value2 = context.frame().popStack();
            VMInt value1 = context.frame().popStack();
            int branch = context.getInstr().getParam("branch");
            if (value1.getValue() != value2.getValue())
                context.getPCHandler().setRelativeOffset(branch);
        }


        public static void impl_if_icmplt(ExecutionContext context) {
            VMInt value2 = context.frame().popStack();
            VMInt value1 = context.frame().popStack();
            int branch = context.getInstr().getParam("branch");
            if (value1.getValue() < value2.getValue())
                context.getPCHandler().setRelativeOffset(branch);
        }


        public static void impl_if_icmpge(ExecutionContext context) {
            VMInt value2 = context.frame().popStack();
            VMInt value1 = context.frame().popStack();
            int branch = context.getInstr().getParam("branch");
            if (value1.getValue() >= value2.getValue())
                context.getPCHandler().setRelativeOffset(branch);
        }


        public static void impl_if_icmpgt(ExecutionContext context) {
            VMInt value2 = context.frame().popStack();
            VMInt value1 = context.frame().popStack();
            int branch = context.getInstr().getParam("branch");
            if (value1.getValue() > value2.getValue())
                context.getPCHandler().setRelativeOffset(branch);
        }


        public static void impl_if_icmple(ExecutionContext context) {
            VMInt value2 = context.frame().popStack();
            VMInt value1 = context.frame().popStack();
            int branch = context.getInstr().getParam("branch");
            if (value1.getValue() <= value2.getValue())
                context.getPCHandler().setRelativeOffset(branch);
        }


        public static void impl_if_acmpeq(ExecutionContext context) {
            VMReference value2 = context.frame().popStack();
            VMReference value1 = context.frame().popStack();
            int branch = context.getInstr().getParam("branch");
            if (VMReference.refEq(value1, value2))
                context.getPCHandler().setRelativeOffset(branch);
        }


        public static void impl_if_acmpne(ExecutionContext context) {
            VMReference value2 = context.frame().popStack();
            VMReference value1 = context.frame().popStack();
            int branch = context.getInstr().getParam("branch");
            if (!VMReference.refEq(value1, value2))
                context.getPCHandler().setRelativeOffset(branch);
        }


        public static void impl_goto(ExecutionContext context) {
            int branch = context.getInstr().getParam("branch");
            context.getPCHandler().setRelativeOffset(branch);
        }


        public static void impl_jsr(ExecutionContext context) {
            int address = context.frame().getPC() + 3;
            VMReturnAddress returnAddress = new VMReturnAddress(address);
            context.frame().pshStack(returnAddress);
            int branch = context.getInstr().getParam("branch");
            context.getPCHandler().setRelativeOffset(branch);
        }


        public static void impl_ret(ExecutionContext context) {
            int index = context.getInstr().getParam("index");
            VMReturnAddress address = context.frame().getLocal(index);
            context.getPCHandler().setAbsoluteOffset(address.getAddress());
        }


        public static void impl_tableswitch(ExecutionContext context) {
            int defaultBranch = context.getInstr().getParam("default");
            int low = context.getInstr().getParam("low");
            int high = context.getInstr().getParam("high");
            List<Map<String, Integer>> offsets = context.getInstr().getParam("jump_offsets");
            VMInt value = context.frame().popStack();
            int branch;
            if (low <= value.getValue() && value.getValue() <= high) {
                branch = offsets.get(value.getValue() - low).get("offset");
            } else {
                branch = defaultBranch;
            }
            context.getPCHandler().setRelativeOffset(branch);
        }


        public static void impl_lookupswitch(ExecutionContext context) {
            int defaultBranch = context.getInstr().getParam("default");
            List<Map<String, Integer>> matchOffsets = context.getInstr().getParam("match_offset_pairs");
            VMInt value = context.frame().popStack();
            int branch = defaultBranch;
            for (Map<String, Integer> pair : matchOffsets) {
                if (pair.get("match") == value.getValue()) {
                    branch = pair.get("offset");
                    break;
                }
            }
            context.getPCHandler().setRelativeOffset(branch);
        }


        public static void impl_ireturn(ExecutionContext context) {
            VMType returnValue = context.frame().popStack();
            assert returnValue instanceof VMInt;
            context.frame().setReturnValue(returnValue);
        }


        public static void impl_lreturn(ExecutionContext context) {
            VMType returnValue = context.frame().popStack();
            assert returnValue instanceof VMLong;
            context.frame().setReturnValue(returnValue);
        }


        public static void impl_freturn(ExecutionContext context) {
            VMType returnValue = context.frame().popStack();
            assert returnValue instanceof VMFloat;
            context.frame().setReturnValue(returnValue);
        }


        public static void impl_dreturn(ExecutionContext context) {
            VMType returnValue = context.frame().popStack();
            assert returnValue instanceof VMDouble;
            context.frame().setReturnValue(returnValue);
        }


        public static void impl_areturn(ExecutionContext context) {
            VMType returnValue = context.frame().popStack();
            assert returnValue instanceof VMReference;
            context.frame().setReturnValue(returnValue);
        }


        public static void impl_return(ExecutionContext context) {
            context.frame().setReturnValue(new VMNullReference());
        }


        public static void impl_getstatic(ExecutionContext context) {

        }


        public static void impl_putstatic(ExecutionContext context) {

        }


        public static void impl_getfield(ExecutionContext context) {

        }


        public static void impl_putfield(ExecutionContext context) {

        }


        public static void impl_invokevirtual(ExecutionContext context) {

        }


        public static void impl_invokespecial(ExecutionContext context) {

        }


        public static void impl_invokestatic(ExecutionContext context) {
            int index = context.getInstr().getParam("index");
            ConstMethodRef methodRef = context.getConstPool().getConstant(index, ConstMethodRef.class);
            VMMethod method = context.getMethodPool().resolve(methodRef);
            context.frame().setInvokedMethod(method);
        }


        public static void impl_invokeinterface(ExecutionContext context) {

        }


        public static void impl_invokedynamic(ExecutionContext context) {

        }


        public static void impl_new(ExecutionContext context) {

        }


        public static void impl_newarray(ExecutionContext context) {

        }


        public static void impl_anewarray(ExecutionContext context) {

        }


        public static void impl_arraylength(ExecutionContext context) {

        }


        public static void impl_athrow(ExecutionContext context) {

        }


        public static void impl_checkcast(ExecutionContext context) {
            VMReference objectRef = context.frame().popStack();

        }


        public static void impl_instanceof(ExecutionContext context) {
            VMReference objectRef = context.frame().popStack();
            int index = context.getInstr().getParam("index");
            ConstClass objectType = context.getConstPool().getConstant(index, ConstClass.class);
            VMInt result = context.getVMMemory().isInstance(objectRef, objectType) ?
              new VMInt(1) : new VMInt(0);
            context.frame().pshStack(result);
        }


        public static void impl_monitorenter(ExecutionContext context) {

        }


        public static void impl_monitorexit(ExecutionContext context) {

        }


        public static void impl_wide(ExecutionContext context) {
            int opcode = context.getInstr().getParam("opcode");
            int index = context.getInstr().getParam("index");
            switch (opcode) {
                case 0x15 -> {
                    VMType value = context.frame().getLocal(index);
                    assert value instanceof VMInt;
                    context.frame().pshStack(value);
                }
                case 0x16 -> {
                    VMType value = context.frame().getLocal(index);
                    assert value instanceof VMLong;
                    context.frame().pshStack(value);
                }
                case 0x17 -> {
                    VMType value = context.frame().getLocal(index);
                    assert value instanceof VMFloat;
                    context.frame().pshStack(value);
                }
                case 0x18 -> {
                    VMType value = context.frame().getLocal(index);
                    assert value instanceof VMDouble;
                    context.frame().pshStack(value);
                }
                case 0x19 -> {
                    VMType value = context.frame().getLocal(index);
                    assert value instanceof VMReference;
                    context.frame().pshStack(value);
                }
                case 0x36 -> {
                    VMType value = context.frame().popStack();
                    assert value instanceof VMInt;
                    context.frame().putLocal(index, value);
                }
                case 0x37 -> {
                    VMType value = context.frame().popStack();
                    assert value instanceof VMLong;
                    context.frame().putLocal(index, value);
                }
                case 0x38 -> {
                    VMType value = context.frame().popStack();
                    assert value instanceof VMFloat;
                    context.frame().putLocal(index, value);
                }
                case 0x39 -> {
                    VMType value = context.frame().popStack();
                    assert value instanceof VMDouble;
                    context.frame().putLocal(index, value);
                }
                case 0x3a -> {
                    VMType value = context.frame().popStack();
                    assert value instanceof VMReference;
                    context.frame().putLocal(index, value);
                }
                case 0xa9 -> {  // ret
                    VMReturnAddress address = context.frame().getLocal(index);
                    context.getPCHandler().setAbsoluteOffset(address.getAddress());
                }
                case 0xc6 -> {  // iinc
                    int inc_val = context.getInstr().getParam("const");
                    VMInt value = context.frame().getLocal(index);
                    value = new VMInt(value.getValue() + inc_val);
                    context.frame().putLocal(index, value);
                }
            }
        }


        public static void impl_multianewarray(ExecutionContext context) {

        }


        public static void impl_ifnull(ExecutionContext context) {
            int branch = context.getInstr().getParam("branch");
            VMReference value = context.frame().popStack();
            if (value instanceof VMNullReference)
                context.getPCHandler().setRelativeOffset(branch);
        }


        public static void impl_ifnonnull(ExecutionContext context) {
            int branch = context.getInstr().getParam("branch");
            VMReference value = context.frame().popStack();
            if (!(value instanceof VMNullReference))
                context.getPCHandler().setRelativeOffset(branch);
        }


        public static void impl_goto_w(ExecutionContext context) {
            int branch = context.getInstr().getParam("branch");
            context.getPCHandler().setRelativeOffset(branch);
        }


        public static void impl_jsr_w(ExecutionContext context) {
            int address = context.frame().getPC() + 3;
            VMReturnAddress returnAddress = new VMReturnAddress(address);
            context.frame().pshStack(returnAddress);
            int branch = context.getInstr().getParam("branch");
            context.getPCHandler().setRelativeOffset(branch);
        }
    }
}
