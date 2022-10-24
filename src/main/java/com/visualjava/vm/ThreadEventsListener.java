package com.visualjava.vm;

import com.visualjava.invoke.ExecutionContext;

public interface ThreadEventsListener {
    void onFramePush(VMFrame frame);
    void onFramePop(VMFrame frame);
    void onFreqChange(int newFreq);

    void onInstrExec(ExecutionContext context);
}
