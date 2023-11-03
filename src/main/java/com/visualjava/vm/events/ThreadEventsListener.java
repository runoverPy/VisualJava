package com.visualjava.vm.events;

import com.visualjava.invoke.ExecutionContext;
import com.visualjava.vm.VMFrame;

public interface ThreadEventsListener {
    FrameEventsListener makeFrameListener(VMFrame frame);
    void onFramePush(VMFrame frame);
    void onFramePop(VMFrame frame);
    void onFreqChange(int newFreq);

    void onInstrExec(ExecutionContext context);
}
