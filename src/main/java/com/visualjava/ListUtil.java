package com.visualjava;

import java.util.List;
import java.util.function.Supplier;

public class ListUtil {
    public static <E> List<E> listComp(Supplier<List<E>> listSupplier, ExceptionSupplier<E> valueSupplier, int iters) throws Exception {
        List<E> list = listSupplier.get();
        for (int i = 0; i < iters; i++) list.add(valueSupplier.get());
        return list;
    }
}
