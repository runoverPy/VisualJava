package com.visualjava;

import java.util.function.Supplier;

public interface ExceptionSupplier<T> {
    T get() throws Exception;
}
