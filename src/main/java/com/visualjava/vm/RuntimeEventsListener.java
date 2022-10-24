package com.visualjava.vm;

import com.visualjava.invoke.ExecutionContext;

import java.io.PrintStream;
import java.util.Date;

public interface RuntimeEventsListener {
    static RuntimeEventsListener makeRuntimePrinter() {
        return makeRuntimePrinter(System.out);
    }

    static RuntimeEventsListener makeRuntimePrinter(PrintStream out) {
        return new RuntimeEventsListener() {
            @Override
            public ThreadEventsListener makeThreadListener(VMThread thread) {
                return new ThreadEventsListener() {
                    @Override
                    public void onFramePush(VMFrame frame) {
                        out.printf("[%td-%<tm-%<tY %<tT %<tZ][THREAD %s] pushed frame %s\n",
                                new Date(),
                                thread.getName(),
                                frame.getMethod().getMethodName()
                        );
                    }

                    @Override
                    public void onFramePop(VMFrame frame) {
                        out.printf("[%td-%<tm-%<tY %<tT %<tZ][THREAD %s] popped frame %s\n",
                                new Date(),
                                thread.getName(),
                                frame.getMethod().getMethodName()
                        );
                    }

                    @Override
                    public void onFreqChange(int newFreq) {
                        out.printf("[%td-%<tm-%<tY %<tT %<tZ][THREAD %s] frequency set to %d\n",
                                new Date(),
                                thread.getName(),
                                newFreq
                        );
                    }

                    @Override
                    public void onInstrExec(ExecutionContext context) {
                        out.printf("[%td-%<tm-%<tY %<tT %<tZ][THREAD %s] executing %-20s %s\n",
                                new Date(),
                                thread.getName(),
                                String.format("`%s`", context.getInstr().getMnemonic()),
                                context.frame().frameState()
                        );
                    }
                };
            }

            @Override
            public void onThreadStart(VMThread thread) {
                out.printf("[%td-%<tm-%<tY %<tT %<tZ][RUNTIME] starting thread %s\n", new Date(), thread.getName());
            }

            @Override
            public void onThreadDeath(VMThread thread) {
                out.printf("[%td-%<tm-%<tY %<tT %<tZ][RUNTIME] killing thread %s\n", new Date(), thread.getName());
            }

            @Override
            public void onRuntimeExit() {
                out.printf("[%td-%<tm-%<tY %<tT %<tZ][RUNTIME] runtime exiting\n", new Date());
            }
        };
    }

    static RuntimeEventsListener forkRuntimeListener(RuntimeEventsListener runtimeListener0, RuntimeEventsListener runtimeListener1) {
        return new RuntimeEventsListener() {
            @Override
            public ThreadEventsListener makeThreadListener(VMThread thread) {
                ThreadEventsListener threadListener0 = runtimeListener0.makeThreadListener(thread);
                ThreadEventsListener threadListener1 = runtimeListener1.makeThreadListener(thread);

                return new ThreadEventsListener() {
                    @Override
                    public void onFramePush(VMFrame frame) {
                        threadListener0.onFramePush(frame);
                        threadListener1.onFramePush(frame);
                    }

                    @Override
                    public void onFramePop(VMFrame frame) {
                        threadListener0.onFramePop(frame);
                        threadListener1.onFramePop(frame);
                    }

                    @Override
                    public void onFreqChange(int newFreq) {
                        threadListener0.onFreqChange(newFreq);
                        threadListener1.onFreqChange(newFreq);
                    }

                    @Override
                    public void onInstrExec(ExecutionContext context) {
                        threadListener0.onInstrExec(context);
                        threadListener1.onInstrExec(context);
                    }
                };
            }

            @Override
            public void onThreadStart(VMThread thread) {
                runtimeListener0.onThreadStart(thread);
                runtimeListener1.onThreadStart(thread);
            }

            @Override
            public void onThreadDeath(VMThread thread) {
                runtimeListener0.onThreadDeath(thread);
                runtimeListener1.onThreadDeath(thread);
            }

            @Override
            public void onRuntimeExit() {
                runtimeListener0.onRuntimeExit();
                runtimeListener1.onRuntimeExit();
            }
        };
    }


    ThreadEventsListener makeThreadListener(VMThread thread);

    void onThreadStart(VMThread thread);
    void onThreadDeath(VMThread thread);
    void onRuntimeExit();
}
