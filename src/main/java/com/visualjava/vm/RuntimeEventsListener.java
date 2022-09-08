package com.visualjava.vm;

public interface RuntimeEventsListener {
    RuntimeEventsListener NULL = new RuntimeEventsListener() {
        @Override
        public void onThreadSpawn(VMThread thread) {}

        @Override
        public void onThreadDeath(VMThread thread) {}

        @Override
        public void onRuntimeExit() {}

        @Override
        public String toString() {
            return "RuntimeEventsListener NULL";
        }
    };

    void onThreadSpawn(VMThread thread);
    void onThreadDeath(VMThread thread);
    void onRuntimeExit();
}
