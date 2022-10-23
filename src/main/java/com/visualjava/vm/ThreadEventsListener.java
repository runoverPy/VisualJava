package com.visualjava.vm;

import com.visualjava.invoke.ExecutionContext;

public interface ThreadEventsListener {
    void onFramePush(VMFrame frame);
    void onFramePop(VMFrame frame);

    void onInstrExec(ExecutionContext context);
}
