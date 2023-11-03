package com.visualjava.vm.events;

import com.visualjava.invoke.ExecutionContext;
import com.visualjava.types.VMType;

public interface FrameEventsListener {
    void onStackPush(VMType value);
    void onStackPop();
    void onLocalWrite(int index, VMType value);
    void onInstrExec(ExecutionContext context);
}
