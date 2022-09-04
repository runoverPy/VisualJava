package com.visualjava.vm;

import com.visualjava.data.constants.ConstClass;
import com.visualjava.types.VMArrayReference;
import com.visualjava.types.VMObjectReference;
import com.visualjava.types.VMReference;
import com.visualjava.types.VMType;

public interface VMMemory {
    void collect();

    VMType load(Object object) throws IllegalAccessException;

    VMObjectReference newObject(ConstClass constClass);

    VMArrayReference newArray(ConstClass constClass);

    void putField(VMReference reference, VMType value);

    VMType getField(VMReference objectRef);

    boolean isInstance(VMReference objectRef, ConstClass type);

    VMType getArrayField(VMArrayReference arrayRef, int index);

    <T extends VMType> T getArrayField(VMArrayReference arrayRef, int index, Class<T> _class);

    void putArrayField(VMArrayReference arrayRef, int index, VMType value);
}
