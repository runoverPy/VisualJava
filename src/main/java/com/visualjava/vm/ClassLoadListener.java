package com.visualjava.vm;

import com.visualjava.data.ClassData;

public interface ClassLoadListener {
    void onClassLoad(ClassData classData);
}
