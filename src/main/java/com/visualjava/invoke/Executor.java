package com.visualjava.invoke;

import com.visualjava.data.constants.*;
import com.visualjava.types.*;
import com.visualjava.vm.VMMethod;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class Executor {
    private final InstructionExecutor instructionExecutor;

    public Executor() {
        this.instructionExecutor = new InstructionExecutor();
    }

    public final void execute(ExecutionContext context) {
        try {
            System.out.println("\t" + context.frame().frameState());
            System.out.println("executing [" + context.getInstr().getMnemonic() + "] for " + Thread.currentThread() + " in method " + context.getMethod());
            instructionExecutor.getClass().getDeclaredMethod(
                    "impl_" + context.getInstr().getMnemonic(),
                    ExecutionContext.class
            ).invoke(instructionExecutor, context);
            context.getPCHandler().execute();
        } catch (NoSuchMethodException e) {
            throw new InternalError("Error in instruction lookup. Erroneous instruction mnemonic: " + context.getInstr().getMnemonic(), e);
        } catch (InvocationTargetException e) {
            throw new InternalError("Error in instruction invocation. Erroneous instruction mnemonic: " + context.getInstr().getMnemonic(), e);
        } catch (IllegalAccessException e) {
            throw new InternalError("Error in instruction access. Erroneous instruction mnemonic: " + context.getInstr().getMnemonic(), e);
        }
    }

    protected static class InstructionExecutor implements OpcodeOperations {
        @Override
        public void impl_aconst_null(ExecutionContext context) {
            context.frame().pshStack(new VMNullReference());
        }

        @Override
        public void impl_iconst_m1(ExecutionContext context) {
            context.frame().pshStack(new VMInt(-1));
        }

        @Override
        public void impl_iconst_0(ExecutionContext context) {
            context.frame().pshStack(new VMInt(0));
        }

        @Override
        public void impl_iconst_1(ExecutionContext context) {
            context.frame().pshStack(new VMInt(1));
        }

        @Override
        public void impl_iconst_2(ExecutionContext context) {
            context.frame().pshStack(new VMInt(2));
        }

        @Override
        public void impl_iconst_3(ExecutionContext context) {
            context.frame().pshStack(new VMInt(3));
        }

        @Override
        public void impl_iconst_4(ExecutionContext context) {
            context.frame().pshStack(new VMInt(4));
        }

        @Override
        public void impl_iconst_5(ExecutionContext context) {
            context.frame().pshStack(new VMInt(5));
        }

        @Override
        public void impl_lconst_0(ExecutionContext context) {
            context.frame().pshStack(new VMLong(0));
        }

        @Override
        public void impl_lconst_1(ExecutionContext context) {
            context.frame().pshStack(new VMLong(1));
        }

        @Override
        public void impl_fconst_0(ExecutionContext context) {
            context.frame().pshStack(new VMFloat(0));
        }

        @Override
        public void impl_fconst_1(ExecutionContext context) {
            context.frame().pshStack(new VMFloat(1));
        }

        @Override
        public void impl_fconst_2(ExecutionContext context) {
            context.frame().pshStack(new VMFloat(2));
        }

        @Override
        public void impl_dconst_0(ExecutionContext context) {
            context.frame().pshStack(new VMDouble(0));
        }

        @Override
        public void impl_dconst_1(ExecutionContext context) {
            context.frame().pshStack(new VMDouble(1));
        }

        @Override
        public void impl_bipush(ExecutionContext context) {
            byte value = (byte) context.getInstr().getParam("byte");
            context.frame().pshStack(new VMByte(value));
        }

        @Override
        public void impl_sipush(ExecutionContext context) {
            short value = (short) context.getInstr().getParam("short");
            context.frame().pshStack(new VMShort(value));
        }

        @Override
        public void impl_ldc(ExecutionContext context) {
            int index = context.getInstr().getParam("index", int.class);
            LoadableConst constValue = (LoadableConst) context.getConstPool().getConstant(index);
            assert !(constValue instanceof ConstLong || constValue instanceof ConstDouble);
            VMType value = constValue.load();
            context.frame().pshStack(value);
        }

        @Override
        public void impl_ldc_w(ExecutionContext context) {
            int index = context.getInstr().getParam("index", int.class);
            LoadableConst constValue = (LoadableConst) context.getConstPool().getConstant(index);
            assert !(constValue instanceof ConstLong || constValue instanceof ConstDouble);
            VMType value = constValue.load();
            context.frame().pshStack(value);
        }

        @Override
        public void impl_ldc2_w(ExecutionContext context) {
            int index = context.getInstr().getParam("index", int.class);
            LoadableConst constValue = (LoadableConst) context.getConstPool().getConstant(index);
            assert !(constValue instanceof ConstInt || constValue instanceof ConstFloat);
            VMType value = constValue.load();
            context.frame().pshStack(value);
        }

        @Override
        public void impl_iload(ExecutionContext context) {
            int index = context.getInstr().getParam("index", Integer.class);
            VMType value = context.frame().getLocal(index);
            assert value instanceof VMInt;
            context.frame().pshStack(value);
        }

        @Override
        public void impl_lload(ExecutionContext context) {
            int index = context.getInstr().getParam("index", Integer.class);
            VMType value = context.frame().getLocal(index);
            assert value instanceof VMLong;
            context.frame().pshStack(value);
        }

        @Override
        public void impl_fload(ExecutionContext context) {
            int index = context.getInstr().getParam("index", Integer.class);
            VMType value = context.frame().getLocal(index);
            assert value instanceof VMFloat;
            context.frame().pshStack(value);
        }

        @Override
        public void impl_dload(ExecutionContext context) {
            int index = context.getInstr().getParam("index", Integer.class);
            VMType value = context.frame().getLocal(index);
            assert value instanceof VMDouble;
            context.frame().pshStack(value);
        }

        @Override
        public void impl_aload(ExecutionContext context) {
            int index = context.getInstr().getParam("index", Integer.class);
            VMType value = context.frame().getLocal(index);
            assert value instanceof VMReference;
            context.frame().pshStack(value);
        }

        @Override
        public void impl_iload_0(ExecutionContext context) {
            VMType value = context.frame().getLocal(0);
            assert value instanceof VMInt;
            context.frame().pshStack(value);
        }

        @Override
        public void impl_iload_1(ExecutionContext context) {
            VMType value = context.frame().getLocal(1);
            assert value instanceof VMInt;
            context.frame().pshStack(value);
        }

        @Override
        public void impl_iload_2(ExecutionContext context) {
            VMType value = context.frame().getLocal(2);
            assert value instanceof VMInt;
            context.frame().pshStack(value);
        }

        @Override
        public void impl_iload_3(ExecutionContext context) {
            VMType value = context.frame().getLocal(3);
            assert value instanceof VMInt;
            context.frame().pshStack(value);
        }

        @Override
        public void impl_lload_0(ExecutionContext context) {
            VMType value = context.frame().getLocal(0);
            assert value instanceof VMLong;
            context.frame().pshStack(value);
        }

        @Override
        public void impl_lload_1(ExecutionContext context) {
            VMType value = context.frame().getLocal(1);
            assert value instanceof VMLong;
            context.frame().pshStack(value);
        }

        @Override
        public void impl_lload_2(ExecutionContext context) {
            VMType value = context.frame().getLocal(2);
            assert value instanceof VMLong;
            context.frame().pshStack(value);
        }

        @Override
        public void impl_lload_3(ExecutionContext context) {
            VMType value = context.frame().getLocal(3);
            assert value instanceof VMLong;
            context.frame().pshStack(value);
        }

        @Override
        public void impl_fload_0(ExecutionContext context) {
            VMType value = context.frame().getLocal(0);
            assert value instanceof VMFloat;
            context.frame().pshStack(value);
        }

        @Override
        public void impl_fload_1(ExecutionContext context) {
            VMType value = context.frame().getLocal(1);
            assert value instanceof VMFloat;
            context.frame().pshStack(value);
        }

        @Override
        public void impl_fload_2(ExecutionContext context) {
            VMType value = context.frame().getLocal(2);
            assert value instanceof VMFloat;
            context.frame().pshStack(value);
        }

        @Override
        public void impl_fload_3(ExecutionContext context) {
            VMType value = context.frame().getLocal(3);
            assert value instanceof VMFloat;
            context.frame().pshStack(value);
        }

        @Override
        public void impl_dload_0(ExecutionContext context) {
            VMType value = context.frame().getLocal(0);
            assert value instanceof VMDouble;
            context.frame().pshStack(value);
        }

        @Override
        public void impl_dload_1(ExecutionContext context) {
            VMType value = context.frame().getLocal(1);
            assert value instanceof VMDouble;
            context.frame().pshStack(value);
        }

        @Override
        public void impl_dload_2(ExecutionContext context) {
            VMType value = context.frame().getLocal(2);
            assert value instanceof VMDouble;
            context.frame().pshStack(value);
        }

        @Override
        public void impl_dload_3(ExecutionContext context) {
            VMType value = context.frame().getLocal(3);
            assert value instanceof VMDouble;
            context.frame().pshStack(value);
        }

        @Override
        public void impl_aload_0(ExecutionContext context) {
            VMType value = context.frame().getLocal(0);
            assert value instanceof VMReference;
            context.frame().pshStack(value);
        }

        @Override
        public void impl_aload_1(ExecutionContext context) {
            VMType value = context.frame().getLocal(1);
            assert value instanceof VMReference;
            context.frame().pshStack(value);
        }

        @Override
        public void impl_aload_2(ExecutionContext context) {
            VMType value = context.frame().getLocal(2);
            assert value instanceof VMReference;
            context.frame().pshStack(value);
        }

        @Override
        public void impl_aload_3(ExecutionContext context) {
            VMType value = context.frame().getLocal(3);
            assert value instanceof VMReference;
            context.frame().pshStack(value);
        }

        @Override
        public void impl_iaload(ExecutionContext context) {
            VMInt index = context.frame().popStack(VMInt.class);
            VMArrayReference arrayRef = context.frame().popStack(VMArrayReference.class);
            VMType value = context.getVMMemory().getArrayField(arrayRef, index.getValue());
            assert value instanceof VMInt;
            context.frame().pshStack(value);
        }

        @Override
        public void impl_laload(ExecutionContext context) {
            VMInt index = context.frame().popStack(VMInt.class);
            VMArrayReference arrayRef = context.frame().popStack(VMArrayReference.class);
            VMType value = context.getVMMemory().getArrayField(arrayRef, index.getValue());
            assert value instanceof VMLong;
            context.frame().pshStack(value);
        }

        @Override
        public void impl_faload(ExecutionContext context) {
            VMInt index = context.frame().popStack(VMInt.class);
            VMArrayReference arrayRef = context.frame().popStack(VMArrayReference.class);
            VMType value = context.getVMMemory().getArrayField(arrayRef, index.getValue());
            assert value instanceof VMFloat;
            context.frame().pshStack(value);
        }

        @Override
        public void impl_daload(ExecutionContext context) {
            VMInt index = context.frame().popStack(VMInt.class);
            VMArrayReference arrayRef = context.frame().popStack(VMArrayReference.class);
            VMType value = context.getVMMemory().getArrayField(arrayRef, index.getValue());
            assert value instanceof VMDouble;
            context.frame().pshStack(value);
        }

        @Override
        public void impl_aaload(ExecutionContext context) {
            VMInt index = context.frame().popStack(VMInt.class);
            VMArrayReference arrayRef = context.frame().popStack(VMArrayReference.class);
            VMType value = context.getVMMemory().getArrayField(arrayRef, index.getValue());
            assert value instanceof VMReference;
            context.frame().pshStack(value);
        }

        @Override
        public void impl_baload(ExecutionContext context) {
            VMInt index = context.frame().popStack(VMInt.class);
            VMArrayReference arrayRef = context.frame().popStack(VMArrayReference.class);
            VMType value = context.getVMMemory().getArrayField(arrayRef, index.getValue());
            assert value instanceof VMByte;
            context.frame().pshStack(value);
        }

        @Override
        public void impl_caload(ExecutionContext context) {
            VMInt index = context.frame().popStack(VMInt.class);
            VMArrayReference arrayRef = context.frame().popStack(VMArrayReference.class);
            VMType value = context.getVMMemory().getArrayField(arrayRef, index.getValue());
            assert value instanceof VMChar;
            context.frame().pshStack(value);
        }

        @Override
        public void impl_saload(ExecutionContext context) {
            VMInt index = context.frame().popStack(VMInt.class);
            VMArrayReference arrayRef = context.frame().popStack(VMArrayReference.class);
            VMType value = context.getVMMemory().getArrayField(arrayRef, index.getValue());
            assert value instanceof VMShort;
            context.frame().pshStack(value);
        }

        @Override
        public void impl_istore(ExecutionContext context) {
            int index = context.getInstr().getParam("index", Integer.class);
            VMType value = context.frame().popStack();
            assert value instanceof VMInt;
            context.frame().putLocal(index, value);
        }

        @Override
        public void impl_lstore(ExecutionContext context) {
            int index = context.getInstr().getParam("index", Integer.class);
            VMType value = context.frame().popStack();
            assert value instanceof VMLong;
            context.frame().putLocal(index, value);
        }

        @Override
        public void impl_fstore(ExecutionContext context) {
            int index = context.getInstr().getParam("index", Integer.class);
            VMType value = context.frame().popStack();
            assert value instanceof VMFloat;
            context.frame().putLocal(index, value);
        }

        @Override
        public void impl_dstore(ExecutionContext context) {
            int index = context.getInstr().getParam("index", Integer.class);
            VMType value = context.frame().popStack();
            assert value instanceof VMDouble;
            context.frame().putLocal(index, value);
        }

        @Override
        public void impl_astore(ExecutionContext context) {
            int index = context.getInstr().getParam("index", Integer.class);
            VMType value = context.frame().popStack();
            assert value instanceof VMReference;
            context.frame().putLocal(index, value);
        }

        @Override
        public void impl_istore_0(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMInt;
            context.frame().putLocal(0, value);
        }

        @Override
        public void impl_istore_1(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMInt;
            context.frame().putLocal(1, value);
        }

        @Override
        public void impl_istore_2(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMInt;
            context.frame().putLocal(2, value);
        }

        @Override
        public void impl_istore_3(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMInt;
            context.frame().putLocal(3, value);
        }

        @Override
        public void impl_lstore_0(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMLong;
            context.frame().putLocal(0, value);
        }

        @Override
        public void impl_lstore_1(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMLong;
            context.frame().putLocal(1, value);
        }

        @Override
        public void impl_lstore_2(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMLong;
            context.frame().putLocal(2, value);
        }

        @Override
        public void impl_lstore_3(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMLong;
            context.frame().putLocal(3, value);
        }

        @Override
        public void impl_fstore_0(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMFloat;
            context.frame().putLocal(0, value);
        }

        @Override
        public void impl_fstore_1(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMFloat;
            context.frame().putLocal(1, value);
        }

        @Override
        public void impl_fstore_2(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMFloat;
            context.frame().putLocal(2, value);
        }

        @Override
        public void impl_fstore_3(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMFloat;
            context.frame().putLocal(3, value);
        }

        @Override
        public void impl_dstore_0(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMDouble;
            context.frame().putLocal(0, value);
        }

        @Override
        public void impl_dstore_1(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMDouble;
            context.frame().putLocal(1, value);
        }

        @Override
        public void impl_dstore_2(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMDouble;
            context.frame().putLocal(2, value);
        }

        @Override
        public void impl_dstore_3(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMDouble;
            context.frame().putLocal(3, value);
        }

        @Override
        public void impl_astore_0(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMReference;
            context.frame().putLocal(0, value);
        }

        @Override
        public void impl_astore_1(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMReference;
            context.frame().putLocal(1, value);
        }

        @Override
        public void impl_astore_2(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMReference;
            context.frame().putLocal(2, value);
        }

        @Override
        public void impl_astore_3(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMReference;
            context.frame().putLocal(3, value);
        }

        @Override
        public void impl_iastore(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMInt;
            VMInt index = context.frame().popStack(VMInt.class);
            VMArrayReference arrayRef = context.frame().popStack(VMArrayReference.class);
            context.getVMMemory().putArrayField(arrayRef, index.getValue(), value);
        }

        @Override
        public void impl_lastore(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMLong;
            VMInt index = context.frame().popStack(VMInt.class);
            VMArrayReference arrayRef = context.frame().popStack(VMArrayReference.class);
            context.getVMMemory().putArrayField(arrayRef, index.getValue(), value);
        }

        @Override
        public void impl_fastore(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMFloat;
            VMInt index = context.frame().popStack(VMInt.class);
            VMArrayReference arrayRef = context.frame().popStack(VMArrayReference.class);
            context.getVMMemory().putArrayField(arrayRef, index.getValue(), value);
        }

        @Override
        public void impl_dastore(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMDouble;
            VMInt index = context.frame().popStack(VMInt.class);
            VMArrayReference arrayRef = context.frame().popStack(VMArrayReference.class);
            context.getVMMemory().putArrayField(arrayRef, index.getValue(), value);
        }

        @Override
        public void impl_aastore(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMReference;
            VMInt index = context.frame().popStack(VMInt.class);
            VMArrayReference arrayRef = context.frame().popStack(VMArrayReference.class);
            context.getVMMemory().putArrayField(arrayRef, index.getValue(), value);
        }

        @Override
        public void impl_bastore(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMByte;
            VMInt index = context.frame().popStack(VMInt.class);
            VMArrayReference arrayRef = context.frame().popStack(VMArrayReference.class);
            context.getVMMemory().putArrayField(arrayRef, index.getValue(), value);
        }

        @Override
        public void impl_castore(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMChar;
            VMInt index = context.frame().popStack(VMInt.class);
            VMArrayReference arrayRef = context.frame().popStack(VMArrayReference.class);
            context.getVMMemory().putArrayField(arrayRef, index.getValue(), value);
        }

        @Override
        public void impl_sastore(ExecutionContext context) {
            VMType value = context.frame().popStack();
            assert value instanceof VMShort;
            VMInt index = context.frame().popStack(VMInt.class);
            VMArrayReference arrayRef = context.frame().popStack(VMArrayReference.class);
            context.getVMMemory().putArrayField(arrayRef, index.getValue(), value);
        }

        @Override
        public void impl_pop(ExecutionContext context) {
            context.frame().popStack();
        }

        @Override
        public void impl_pop2(ExecutionContext context) {
            VMType value1 = context.frame().popStack();
            if (value1.getCompType() == 1)
                context.frame().popStack();
        }

        @Override
        public void impl_dup(ExecutionContext context) {
            VMType value = context.frame().popStack();
            context.frame().pshStack(value);
            context.frame().pshStack(value);
        }

        @Override
        public void impl_dup_x1(ExecutionContext context) {
            VMType value1 = context.frame().popStack();
            VMType value2 = context.frame().popStack();
            context.frame().pshStack(value1);
            context.frame().pshStack(value2);
            context.frame().pshStack(value1);
        }

        @Override
        public void impl_dup_x2(ExecutionContext context) {
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

        @Override
        public void impl_dup2(ExecutionContext context) {
            VMType value1 = context.frame().popStack();
            VMType value2 = context.frame().popStack();
            context.frame().pshStack(value2);
            context.frame().pshStack(value1);
            context.frame().pshStack(value2);
            context.frame().pshStack(value1);
        }

        @Override
        public void impl_dup2_x1(ExecutionContext context) {
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

        @Override
        public void impl_dup2_x2(ExecutionContext context) {
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

        @Override
        public void impl_swap(ExecutionContext context) {
            VMType value1 = context.frame().popStack();
            VMType value2 = context.frame().popStack();
            context.frame().pshStack(value1);
            context.frame().pshStack(value2);
        }

        @Override
        public void impl_iadd(ExecutionContext context) {
            VMInt value2 = context.frame().popStack(VMInt.class);
            VMInt value1 = context.frame().popStack(VMInt.class);
            VMInt result = new VMInt(value1.getValue() + value2.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_ladd(ExecutionContext context) {
            VMLong value2 = context.frame().popStack(VMLong.class);
            VMLong value1 = context.frame().popStack(VMLong.class);
            VMLong result = new VMLong(value1.getValue() + value2.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_fadd(ExecutionContext context) {
            VMFloat value2 = context.frame().popStack(VMFloat.class);
            VMFloat value1 = context.frame().popStack(VMFloat.class);
            VMFloat result = new VMFloat(value1.getValue() + value2.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_dadd(ExecutionContext context) {
            VMDouble value2 = context.frame().popStack(VMDouble.class);
            VMDouble value1 = context.frame().popStack(VMDouble.class);
            VMDouble result = new VMDouble(value1.getValue() + value2.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_isub(ExecutionContext context) {
            VMInt value2 = context.frame().popStack(VMInt.class);
            VMInt value1 = context.frame().popStack(VMInt.class);
            VMInt result = new VMInt(value1.getValue() - value2.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_lsub(ExecutionContext context) {
            VMLong value2 = context.frame().popStack(VMLong.class);
            VMLong value1 = context.frame().popStack(VMLong.class);
            VMLong result = new VMLong(value1.getValue() - value2.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_fsub(ExecutionContext context) {
            VMFloat value2 = context.frame().popStack(VMFloat.class);
            VMFloat value1 = context.frame().popStack(VMFloat.class);
            VMFloat result = new VMFloat(value1.getValue() - value2.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_dsub(ExecutionContext context) {
            VMDouble value2 = context.frame().popStack(VMDouble.class);
            VMDouble value1 = context.frame().popStack(VMDouble.class);
            VMDouble result = new VMDouble(value1.getValue() - value2.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_imul(ExecutionContext context) {
            VMInt value2 = context.frame().popStack(VMInt.class);
            VMInt value1 = context.frame().popStack(VMInt.class);
            VMInt result = new VMInt(value1.getValue() * value2.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_lmul(ExecutionContext context) {
            VMLong value2 = context.frame().popStack(VMLong.class);
            VMLong value1 = context.frame().popStack(VMLong.class);
            VMLong result = new VMLong(value1.getValue() * value2.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_fmul(ExecutionContext context) {
            VMFloat value2 = context.frame().popStack(VMFloat.class);
            VMFloat value1 = context.frame().popStack(VMFloat.class);
            VMFloat result = new VMFloat(value1.getValue() * value2.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_dmul(ExecutionContext context) {
            VMDouble value2 = context.frame().popStack(VMDouble.class);
            VMDouble value1 = context.frame().popStack(VMDouble.class);
            VMDouble result = new VMDouble(value1.getValue() * value2.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_idiv(ExecutionContext context) {
            VMInt value2 = context.frame().popStack(VMInt.class);
            VMInt value1 = context.frame().popStack(VMInt.class);
            VMInt result = new VMInt(value1.getValue() / value2.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_ldiv(ExecutionContext context) {
            VMLong value2 = context.frame().popStack(VMLong.class);
            VMLong value1 = context.frame().popStack(VMLong.class);
            VMLong result = new VMLong(value1.getValue() / value2.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_fdiv(ExecutionContext context) {
            VMFloat value2 = context.frame().popStack(VMFloat.class);
            VMFloat value1 = context.frame().popStack(VMFloat.class);
            VMFloat result = new VMFloat(value1.getValue() / value2.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_ddiv(ExecutionContext context) {
            VMDouble value2 = context.frame().popStack(VMDouble.class);
            VMDouble value1 = context.frame().popStack(VMDouble.class);
            VMDouble result = new VMDouble(value1.getValue() / value2.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_irem(ExecutionContext context) {
            VMInt value2 = context.frame().popStack(VMInt.class);
            VMInt value1 = context.frame().popStack(VMInt.class);
            VMInt result = new VMInt(value1.getValue() % value2.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_lrem(ExecutionContext context) {
            VMLong value2 = context.frame().popStack(VMLong.class);
            VMLong value1 = context.frame().popStack(VMLong.class);
            VMLong result = new VMLong(value1.getValue() % value2.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_frem(ExecutionContext context) {
            VMFloat value2 = context.frame().popStack(VMFloat.class);
            VMFloat value1 = context.frame().popStack(VMFloat.class);
            VMFloat result = new VMFloat(value1.getValue() % value2.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_drem(ExecutionContext context) {
            VMDouble value2 = context.frame().popStack(VMDouble.class);
            VMDouble value1 = context.frame().popStack(VMDouble.class);
            VMDouble result = new VMDouble(value1.getValue() % value2.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_ineg(ExecutionContext context) {
            VMInt value = context.frame().popStack(VMInt.class);
            VMInt result = new VMInt(-value.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_lneg(ExecutionContext context) {
            VMLong value = context.frame().popStack(VMLong.class);
            VMLong result = new VMLong(-value.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_fneg(ExecutionContext context) {
            VMFloat value = context.frame().popStack(VMFloat.class);
            VMFloat result = new VMFloat(-value.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_dneg(ExecutionContext context) {
            VMDouble value = context.frame().popStack(VMDouble.class);
            VMDouble result = new VMDouble(-value.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_ishl(ExecutionContext context) {
            VMInt value2 = context.frame().popStack(VMInt.class);
            VMInt value1 = context.frame().popStack(VMInt.class);
            VMInt result = new VMInt(value1.getValue() << (value2.getValue() & 0x1f));
            context.frame().pshStack(result);

        }

        @Override
        public void impl_lshl(ExecutionContext context) {
            VMInt value2 = context.frame().popStack(VMInt.class);
            VMLong value1 = context.frame().popStack(VMLong.class);
            VMLong result = new VMLong(value1.getValue() << (value2.getValue() & 0x3f));
            context.frame().pshStack(result);
        }

        @Override
        public void impl_ishr(ExecutionContext context) {
            VMInt value2 = context.frame().popStack(VMInt.class);
            VMInt value1 = context.frame().popStack(VMInt.class);
            VMInt result = new VMInt(value1.getValue() >> (value2.getValue() & 0x1f));
            context.frame().pshStack(result);
        }

        @Override
        public void impl_lshr(ExecutionContext context) {
            VMInt value2 = context.frame().popStack(VMInt.class);
            VMLong value1 = context.frame().popStack(VMLong.class);
            VMLong result = new VMLong(value1.getValue() >> (value2.getValue() & 0x3f));
            context.frame().pshStack(result);
        }

        @Override
        public void impl_iushr(ExecutionContext context) {
            VMInt value2 = context.frame().popStack(VMInt.class);
            VMInt value1 = context.frame().popStack(VMInt.class);
            int value = value1.getValue() >> (value2.getValue() & 0x1f);
            if (value < 0) value += 2 << ~(value2.getValue() & 0x1f);
            VMInt result = new VMInt(value);
            context.frame().pshStack(result);
        }

        @Override
        public void impl_lushr(ExecutionContext context) {
            VMInt value2 = context.frame().popStack(VMInt.class);
            VMLong value1 = context.frame().popStack(VMLong.class);
            long value = value1.getValue() >> (value2.getValue() & 0x3f);
            if (value < 0) value += 2L << ~(value2.getValue() & 0x3f);
            VMLong result = new VMLong(value);
            context.frame().pshStack(result);
        }

        @Override
        public void impl_iand(ExecutionContext context) {
            VMInt value2 = context.frame().popStack(VMInt.class);
            VMInt value1 = context.frame().popStack(VMInt.class);
            VMInt result = new VMInt(value1.getValue() & value2.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_land(ExecutionContext context) {
            VMLong value2 = context.frame().popStack(VMLong.class);
            VMLong value1 = context.frame().popStack(VMLong.class);
            VMLong result = new VMLong(value1.getValue() & value2.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_ior(ExecutionContext context) {
            VMInt value2 = context.frame().popStack(VMInt.class);
            VMInt value1 = context.frame().popStack(VMInt.class);
            VMInt result = new VMInt(value1.getValue() | value2.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_lor(ExecutionContext context) {
            VMLong value2 = context.frame().popStack(VMLong.class);
            VMLong value1 = context.frame().popStack(VMLong.class);
            VMLong result = new VMLong(value1.getValue() | value2.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_ixor(ExecutionContext context) {
            VMInt value2 = context.frame().popStack(VMInt.class);
            VMInt value1 = context.frame().popStack(VMInt.class);
            VMInt result = new VMInt(value1.getValue() ^ value2.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_lxor(ExecutionContext context) {
            VMLong value2 = context.frame().popStack(VMLong.class);
            VMLong value1 = context.frame().popStack(VMLong.class);
            VMLong result = new VMLong(value1.getValue() ^ value2.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_iinc(ExecutionContext context) {
            int index = context.getInstr().getParam("index", Integer.class);
            int incrv = context.getInstr().getParam("const", Integer.class);
            VMInt value = context.frame().getLocal(index, VMInt.class);
            value = new VMInt(value.getValue() + incrv);
            context.frame().putLocal(index, value);
        }

        @Override
        public void impl_i2l(ExecutionContext context) {
            VMInt value = context.frame().popStack(VMInt.class);
            VMLong result = new VMLong(value.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_i2f(ExecutionContext context) {
            VMInt value = context.frame().popStack(VMInt.class);
            VMFloat result = new VMFloat((float) value.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_i2d(ExecutionContext context) {
            VMInt value = context.frame().popStack(VMInt.class);
            VMDouble result = new VMDouble(value.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_l2i(ExecutionContext context) {
            VMLong value = context.frame().popStack(VMLong.class);
            VMInt result = new VMInt((int) value.getValue());
            context.frame().pshStack(result);

        }

        @Override
        public void impl_l2f(ExecutionContext context) {
            VMLong value = context.frame().popStack(VMLong.class);
            VMFloat result = new VMFloat((float) value.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_l2d(ExecutionContext context) {
            VMLong value = context.frame().popStack(VMLong.class);
            VMDouble result = new VMDouble((double) value.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_f2i(ExecutionContext context) {
            VMFloat value = context.frame().popStack(VMFloat.class);
            VMInt result = new VMInt((int) value.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_f2l(ExecutionContext context) {
            VMFloat value = context.frame().popStack(VMFloat.class);
            VMLong result = new VMLong((long) value.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_f2d(ExecutionContext context) {
            VMFloat value = context.frame().popStack(VMFloat.class);
            VMDouble result = new VMDouble(value.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_d2i(ExecutionContext context) {
            VMDouble value = context.frame().popStack(VMDouble.class);
            VMInt result = new VMInt((int) value.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_d2l(ExecutionContext context) {
            VMDouble value = context.frame().popStack(VMDouble.class);
            VMLong result = new VMLong((long) value.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_d2f(ExecutionContext context) {
            VMDouble value = context.frame().popStack(VMDouble.class);
            VMFloat result = new VMFloat((float) value.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_i2b(ExecutionContext context) {
            VMInt value = context.frame().popStack(VMInt.class);
            VMByte result = new VMByte((byte) value.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_i2c(ExecutionContext context) {
            VMInt value = context.frame().popStack(VMInt.class);
            VMChar result = new VMChar((char) value.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_i2s(ExecutionContext context) {
            VMInt value = context.frame().popStack(VMInt.class);
            VMShort result = new VMShort((short) value.getValue());
            context.frame().pshStack(result);
        }

        @Override
        public void impl_lcmp(ExecutionContext context) {
            VMLong value2 = context.frame().popStack(VMLong.class);
            VMLong value1 = context.frame().popStack(VMLong.class);
            VMInt result = new VMInt(Long.compare(value1.getValue(), value2.getValue()));
            context.frame().pshStack(result);
        }

        @Override
        public void impl_fcmpl(ExecutionContext context) {
            VMFloat value2 = context.frame().popStack(VMFloat.class);
            VMFloat value1 = context.frame().popStack(VMFloat.class);
            VMInt result;
            if (Float.isNaN(value1.getValue()) || Float.isNaN(value2.getValue())) {
                result = new VMInt(-1);
            } else {
                result = new VMInt(Float.compare(value1.getValue(), value2.getValue()));
            }
            context.frame().pshStack(result);
        }

        @Override
        public void impl_fcmpg(ExecutionContext context) {
            VMFloat value2 = context.frame().popStack(VMFloat.class);
            VMFloat value1 = context.frame().popStack(VMFloat.class);
            VMInt result;
            if (Float.isNaN(value1.getValue()) || Float.isNaN(value2.getValue())) {
                result = new VMInt(1);
            } else {
                result = new VMInt(Float.compare(value1.getValue(), value2.getValue()));
            }
            context.frame().pshStack(result);
        }

        @Override
        public void impl_dcmpl(ExecutionContext context) {
            VMDouble value2 = context.frame().popStack(VMDouble.class);
            VMDouble value1 = context.frame().popStack(VMDouble.class);
            VMInt result;
            if (Double.isNaN(value1.getValue()) || Double.isNaN(value2.getValue())) {
                result = new VMInt(-1);
            } else {
                result = new VMInt(Double.compare(value1.getValue(), value2.getValue()));
            }
            context.frame().pshStack(result);
        }

        @Override
        public void impl_dcmpg(ExecutionContext context) {
            VMDouble value2 = context.frame().popStack(VMDouble.class);
            VMDouble value1 = context.frame().popStack(VMDouble.class);
            VMInt result;
            if (Double.isNaN(value1.getValue()) || Double.isNaN(value2.getValue())) {
                result = new VMInt(1);
            } else {
                result = new VMInt(Double.compare(value1.getValue(), value2.getValue()));
            }
            context.frame().pshStack(result);
        }

        @Override
        public void impl_ifeq(ExecutionContext context) {
            VMInt value = context.frame().popStack(VMInt.class);
            int branch = context.getInstr().getParam("branch", Integer.class);
            if (value.getValue() == 0)
                context.getPCHandler().setRelativeOffset(branch);
        }

        @Override
        public void impl_ifne(ExecutionContext context) {
            VMInt value = context.frame().popStack(VMInt.class);
            int branch = context.getInstr().getParam("branch", Integer.class);
            if (value.getValue() != 0)
                context.getPCHandler().setRelativeOffset(branch);
        }

        @Override
        public void impl_iflt(ExecutionContext context) {
            VMInt value = context.frame().popStack(VMInt.class);
            int branch = context.getInstr().getParam("branch", Integer.class);
            if (value.getValue() < 0)
                context.getPCHandler().setRelativeOffset(branch);
        }

        @Override
        public void impl_ifge(ExecutionContext context) {
            VMInt value = context.frame().popStack(VMInt.class);
            int branch = context.getInstr().getParam("branch", Integer.class);
            if (value.getValue() >= 0)
                context.getPCHandler().setRelativeOffset(branch);
        }

        @Override
        public void impl_ifgt(ExecutionContext context) {
            VMInt value = context.frame().popStack(VMInt.class);
            int branch = context.getInstr().getParam("branch", Integer.class);
            if (value.getValue() > 0)
                context.getPCHandler().setRelativeOffset(branch);
        }

        @Override
        public void impl_ifle(ExecutionContext context) {
            VMInt value = context.frame().popStack(VMInt.class);
            int branch = context.getInstr().getParam("branch", Integer.class);
            if (value.getValue() <= 0)
                context.getPCHandler().setRelativeOffset(branch);
        }

        @Override
        public void impl_if_icmpeq(ExecutionContext context) {
            VMInt value2 = context.frame().popStack(VMInt.class);
            VMInt value1 = context.frame().popStack(VMInt.class);
            int branch = context.getInstr().getParam("branch", Integer.class);
            if (value1.getValue() == value2.getValue())
                context.getPCHandler().setRelativeOffset(branch);
        }

        @Override
        public void impl_if_icmpne(ExecutionContext context) {
            VMInt value2 = context.frame().popStack(VMInt.class);
            VMInt value1 = context.frame().popStack(VMInt.class);
            int branch = context.getInstr().getParam("branch", Integer.class);
            if (value1.getValue() != value2.getValue())
                context.getPCHandler().setRelativeOffset(branch);
        }

        @Override
        public void impl_if_icmplt(ExecutionContext context) {
            VMInt value2 = context.frame().popStack(VMInt.class);
            VMInt value1 = context.frame().popStack(VMInt.class);
            int branch = context.getInstr().getParam("branch", Integer.class);
            if (value1.getValue() < value2.getValue())
                context.getPCHandler().setRelativeOffset(branch);
        }

        @Override
        public void impl_if_icmpge(ExecutionContext context) {
            VMInt value2 = context.frame().popStack(VMInt.class);
            VMInt value1 = context.frame().popStack(VMInt.class);
            int branch = context.getInstr().getParam("branch", Integer.class);
            if (value1.getValue() >= value2.getValue())
                context.getPCHandler().setRelativeOffset(branch);
        }

        @Override
        public void impl_if_icmpgt(ExecutionContext context) {
            VMInt value2 = context.frame().popStack(VMInt.class);
            VMInt value1 = context.frame().popStack(VMInt.class);
            int branch = context.getInstr().getParam("branch", Integer.class);
            if (value1.getValue() > value2.getValue())
                context.getPCHandler().setRelativeOffset(branch);
        }

        @Override
        public void impl_if_icmple(ExecutionContext context) {
            VMInt value2 = context.frame().popStack(VMInt.class);
            VMInt value1 = context.frame().popStack(VMInt.class);
            int branch = context.getInstr().getParam("branch", Integer.class);
            if (value1.getValue() <= value2.getValue())
                context.getPCHandler().setRelativeOffset(branch);
        }

        @Override
        public void impl_if_acmpeq(ExecutionContext context) {
            VMReference value2 = context.frame().popStack(VMReference.class);
            VMReference value1 = context.frame().popStack(VMReference.class);
            int branch = context.getInstr().getParam("branch", Integer.class);
            if (VMReference.refEq(value1, value2))
                context.getPCHandler().setRelativeOffset(branch);
        }

        @Override
        public void impl_if_acmpne(ExecutionContext context) {
            VMReference value2 = context.frame().popStack(VMReference.class);
            VMReference value1 = context.frame().popStack(VMReference.class);
            int branch = context.getInstr().getParam("branch", Integer.class);
            if (!VMReference.refEq(value1, value2))
                context.getPCHandler().setRelativeOffset(branch);
        }

        @Override
        public void impl_goto(ExecutionContext context) {
            int branch = context.getInstr().getParam("branch", Integer.class);
            context.getPCHandler().setRelativeOffset(branch);
        }

        @Override
        public void impl_jsr(ExecutionContext context) {
            int address = context.frame().getPC() + 3;
            VMReturnAddress returnAddress = new VMReturnAddress(address);
            context.frame().pshStack(returnAddress);
            int branch = context.getInstr().getParam("branch", Integer.class);
            context.getPCHandler().setRelativeOffset(branch);
        }

        @Override
        public void impl_ret(ExecutionContext context) {
            int index = context.getInstr().getParam("index", Integer.class);
            VMReturnAddress address = context.frame().getLocal(index, VMReturnAddress.class);
            context.getPCHandler().setAbsoluteOffset(address.getAddress());
        }

        @Override
        public void impl_tableswitch(ExecutionContext context) {
            int defaultBranch = context.getInstr().getParam("default", Integer.class);
            int low = context.getInstr().getParam("low", Integer.class);
            int high = context.getInstr().getParam("high", Integer.class);
            List<Map<String, Integer>> offsets = context.getInstr().getParam("jump_offsets", List.class);
            VMInt value = context.frame().popStack(VMInt.class);
            int branch;
            if (low <= value.getValue() && value.getValue() <= high) {
                branch = offsets.get(value.getValue() - low).get("offset");
            } else {
                branch = defaultBranch;
            }
            context.getPCHandler().setRelativeOffset(branch);
        }

        @Override
        public void impl_lookupswitch(ExecutionContext context) {
            int defaultBranch = context.getInstr().getParam("default", Integer.class);
            List<Map<String, Integer>> matchOffsets = context.getInstr().getParam("match_offset_pairs", List.class);
            VMInt value = context.frame().popStack(VMInt.class);
            int branch = defaultBranch;
            for (Map<String, Integer> pair : matchOffsets) {
                if (pair.get("match") == value.getValue()) {
                    branch = pair.get("offset");
                    break;
                }
            }
            context.getPCHandler().setRelativeOffset(branch);
        }

        @Override
        public void impl_ireturn(ExecutionContext context) {
            VMType returnValue = context.frame().popStack();
            assert returnValue instanceof VMInt;
            context.frame().setReturnValue(returnValue);
        }

        @Override
        public void impl_lreturn(ExecutionContext context) {
            VMType returnValue = context.frame().popStack();
            assert returnValue instanceof VMLong;
            context.frame().setReturnValue(returnValue);
        }

        @Override
        public void impl_freturn(ExecutionContext context) {
            VMType returnValue = context.frame().popStack();
            assert returnValue instanceof VMFloat;
            context.frame().setReturnValue(returnValue);
        }

        @Override
        public void impl_dreturn(ExecutionContext context) {
            VMType returnValue = context.frame().popStack();
            assert returnValue instanceof VMDouble;
            context.frame().setReturnValue(returnValue);
        }

        @Override
        public void impl_areturn(ExecutionContext context) {
            VMType returnValue = context.frame().popStack();
            assert returnValue instanceof VMReference;
            context.frame().setReturnValue(returnValue);
        }

        @Override
        public void impl_return(ExecutionContext context) {
            context.frame().setReturnValue(null);
        }

        @Override
        public void impl_getstatic(ExecutionContext context) {

        }

        @Override
        public void impl_putstatic(ExecutionContext context) {

        }

        @Override
        public void impl_getfield(ExecutionContext context) {

        }

        @Override
        public void impl_putfield(ExecutionContext context) {

        }

        @Override
        public void impl_invokevirtual(ExecutionContext context) {

        }

        @Override
        public void impl_invokespecial(ExecutionContext context) {

        }

        @Override
        public void impl_invokestatic(ExecutionContext context) {
            int index = context.getInstr().getParam("index", Integer.class);
            ConstMethodRef methodRef = context.getConstPool().getConstant(index, ConstMethodRef.class);
            VMMethod method = context.getMethodPool().resolve(methodRef);
            context.frame().setInvokedMethod(method);
        }

        @Override
        public void impl_invokeinterface(ExecutionContext context) {

        }

        @Override
        public void impl_invokedynamic(ExecutionContext context) {

        }

        @Override
        public void impl_new(ExecutionContext context) {

        }

        @Override
        public void impl_newarray(ExecutionContext context) {

        }

        @Override
        public void impl_anewarray(ExecutionContext context) {

        }

        @Override
        public void impl_arraylength(ExecutionContext context) {

        }

        @Override
        public void impl_athrow(ExecutionContext context) {

        }

        @Override
        public void impl_checkcast(ExecutionContext context) {
            VMReference objectRef = context.frame().popStack(VMArrayReference.class);

        }

        @Override
        public void impl_instanceof(ExecutionContext context) {
            VMReference objectRef = context.frame().popStack(VMArrayReference.class);
            int index = context.getInstr().getParam("index", int.class);
            ConstClass objectType = context.getConstPool().getConstant(index, ConstClass.class);
            VMInt result = context.getVMMemory().isInstance(objectRef, objectType) ?
              new VMInt(1) : new VMInt(0);
            context.frame().pshStack(result);
        }

        @Override
        public void impl_monitorenter(ExecutionContext context) {

        }

        @Override
        public void impl_monitorexit(ExecutionContext context) {

        }

        @Override
        public void impl_wide(ExecutionContext context) {
            int opcode = context.getInstr().getParam("opcode", Integer.class);
            int index = context.getInstr().getParam("index", Integer.class);
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
                    VMReturnAddress address = context.frame().getLocal(index, VMReturnAddress.class);
                    context.getPCHandler().setAbsoluteOffset(address.getAddress());
                }
                case 0xc6 -> {  // iinc
                    int inc_val = context.getInstr().getParam("const", Integer.class);
                    VMInt value = context.frame().getLocal(index, VMInt.class);
                    value = new VMInt(value.getValue() + inc_val);
                    context.frame().putLocal(index, value);
                }
            }
        }

        @Override
        public void impl_multianewarray(ExecutionContext context) {

        }

        @Override
        public void impl_ifnull(ExecutionContext context) {
            int branch = context.getInstr().getParam("branch", Integer.class);
            VMReference value = context.frame().popStack(VMReference.class);
            if (value instanceof VMNullReference)
                context.getPCHandler().setRelativeOffset(branch);
        }

        @Override
        public void impl_ifnonnull(ExecutionContext context) {
            int branch = context.getInstr().getParam("branch", Integer.class);
            VMReference value = context.frame().popStack(VMReference.class);
            if (!(value instanceof VMNullReference))
                context.getPCHandler().setRelativeOffset(branch);
        }

        @Override
        public void impl_goto_w(ExecutionContext context) {
            int branch = context.getInstr().getParam("branch", Integer.class);
            context.getPCHandler().setRelativeOffset(branch);
        }

        @Override
        public void impl_jsr_w(ExecutionContext context) {
            int address = context.frame().getPC() + 3;
            VMReturnAddress returnAddress = new VMReturnAddress(address);
            context.frame().pshStack(returnAddress);
            int branch = context.getInstr().getParam("branch", Integer.class);
            context.getPCHandler().setRelativeOffset(branch);
        }
    }
}
