package com.visualjava.vm;

import com.visualjava.types.VMType;

public interface FrameEventsListener {
    void onStackPush(VMType value);
    void onStackPop();
    void onLocalWrite(int index, VMType value);
}
