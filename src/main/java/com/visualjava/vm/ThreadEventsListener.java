package com.visualjava.vm;

public interface ThreadEventsListener {
    void onFramePush(VMFrame frame);
    void onFramePop(VMFrame frame);
}
