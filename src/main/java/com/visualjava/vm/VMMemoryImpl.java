package com.visualjava.vm;

import com.visualjava.data.constants.ConstClass;
import com.visualjava.types.*;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Field;
import java.util.*;

public class VMMemoryImpl implements VMMemory {
//    private static final Unsafe unsafe;
//
//    static {
//        try {
//            Field theUnsafe = Unsafe.class.getField("theUnsafe");
//            theUnsafe.setAccessible(true);
//            unsafe = (Unsafe) theUnsafe.get(null);
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
//    }

    private final ReferenceQueue<VMReference> refQueue;
    private final Map<PhantomReference<VMReference>, Integer> activeReferences;

    public VMMemoryImpl() {
        refQueue = new ReferenceQueue<>();
        activeReferences = new HashMap<>();
    }

    @Override
    public void collect() {
        Reference<?> ref;
        while ((ref = refQueue.poll()) != null) {
            ref.clear();
        }

        List<PhantomReference<VMReference>> dealloc = new LinkedList<>();
        for (Map.Entry<PhantomReference<VMReference>, Integer> entry : activeReferences.entrySet()) {
            PhantomReference<VMReference> reference = entry.getKey();
            if (reference.refersTo(null)) dealloc.add(reference);
        }
        for (PhantomReference<VMReference> phantomReference : dealloc)
            activeReferences.remove(phantomReference);
    }

    @Override
    public VMType load(Object object) throws IllegalAccessException {
        Class<?> objectClass = object.getClass();

        if (objectClass == Integer.class) {
            return new VMInt((Integer) object);
        } else if (objectClass == Float.class) {
            return new VMFloat((Float) object);
        } else if (objectClass == Long.class) {
            return new VMLong((Long) object);
        } else if (objectClass == Double.class) {
            return new VMDouble((Double) object);
        } else if (objectClass == Boolean.class) {
            return new VMBool((Boolean) object);
        } else if (objectClass == Byte.class) {
            return new VMByte((Byte) object);
        } else if (objectClass == Short.class) {
            return new VMShort((Short) object);
        } else if (objectClass == Character.class) {
            return new VMChar((Character) object);
        } else {
            VMReference objectRef = newObject(null);

            Class<?> curClass = objectClass;
            List<Field> fields = new ArrayList<>();
            while (curClass.getSuperclass() != null) {
                for (Field f : curClass.getDeclaredFields()) fields.add(f);
            }
            Field[] objectFields = fields.toArray(Field[]::new);

            for (Field field : objectFields) {
                VMType fieldValue = load(field.get(object));
                putField(objectRef, fieldValue);

            }
            return objectRef;
        }
    }

    private int reserveToken() {
        int token;
        do {
            token = new Random().nextInt();
        } while (isTokenReserved(token));
        return token;
    }

    private boolean isTokenReserved(int token) {
        return false;
    }

    private void releaseToken(int token) {

    }

    @Override
    public VMObjectReference newObject(ConstClass constClass) {
        VMObjectReference objectReference = new VMObjectReference();
        PhantomReference<VMReference> phantomReference = new PhantomReference<>(objectReference, refQueue);
        activeReferences.put(phantomReference, reserveToken());
        return objectReference;
    }

    @Override
    public VMArrayReference newArray(ConstClass constClass) {
        VMArrayReference arrayReference = new VMArrayReference();
        return null;
    }

    @Override
    public void putField(VMReference reference, VMType value) {}

    @Override
    public VMType getField(VMReference objectRef) {
        return null;
    }

    @Override
    public boolean isInstance(VMReference objectRef, ConstClass type) {
        return false;
    }

    @Override
    public VMType getArrayField(VMArrayReference arrayRef, int index) {
        return null;
    }

    @Override
    public <T extends VMType> T getArrayField(VMArrayReference arrayRef, int index, Class<T> _class) {
        return (T) getArrayField(arrayRef, index);
    }

    @Override
    public void putArrayField(VMArrayReference arrayRef, int index, VMType value) {

    }

    private class MEMType {}

    private class MEMObject extends MEMType {
        private final String type;
        private final Map<String, VMType> fields;

        public MEMObject(String type) {
            this.type = type;
            this.fields = new HashMap<>();
        }

        public VMType getField(String field) {
            return fields.get(field);
        }

        public void putField(String field, VMType value) {
            fields.put(field, value);
        }
    }

    private class MEMArray extends MEMType {
        private final String elementType;
        private final VMType[] slots;

        public MEMArray(String elementType, int length) {
            this.elementType = elementType;
            this.slots = new VMType[length];
        }

        public VMType getField(int index) {
            return slots[index];
        }

        public void putField(int index, VMType value) {
            slots[index] = value;
        }
    }
}
