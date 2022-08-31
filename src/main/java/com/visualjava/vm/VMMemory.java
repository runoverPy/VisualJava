package com.visualjava.vm;

import com.visualjava.data.constants.ConstClass;
import com.visualjava.types.VMReference;
import com.visualjava.types.VMType;

import java.lang.ref.PhantomReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.*;

public class VMMemory {
    private final Map<PhantomReference<VMReference>, Integer> activeReferences;

    public VMMemory(long size) {
        activeReferences = new HashMap<>();
    }

    public void collect() {
        List<PhantomReference<VMReference>> dealloc = new LinkedList<>();
        for (Map.Entry<PhantomReference<VMReference>, Integer> entry : activeReferences.entrySet()) {
            PhantomReference<VMReference> reference = entry.getKey();
            if (reference.refersTo(null)) dealloc.add(reference);
        }
        for (PhantomReference<VMReference> phantomReference : dealloc)
            activeReferences.remove(phantomReference);
    }

    public VMType load(Object object) {
        Class<?> objectClass = object.getClass();
        if (objectClass.isPrimitive()) {

        } else {
            VMReference objectRef = newObject(null);
            Field[] objectFields = objectClass.getFields();
            for (Field field : objectFields) {

            }
        }
    }

    public VMReference newObject(ConstClass constClass) {

    }

    public void putField(VMReference reference, VMType value) {}

    private class MemObject {
        private final String type;
        private final Map<String, VMType> fields;

        public MemObject(String type) {
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
}
