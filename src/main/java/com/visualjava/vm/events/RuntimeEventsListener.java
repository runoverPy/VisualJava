package com.visualjava.vm.events;

import com.visualjava.invoke.ExecutionContext;
import com.visualjava.types.VMType;
import com.visualjava.vm.VMFrame;
import com.visualjava.vm.VMThread;

import java.io.PrintStream;
import java.util.Date;

public interface RuntimeEventsListener {
    static RuntimeEventsListener makeRuntimePrinter() {
        return makeRuntimePrinter(System.out);
    }

    static RuntimeEventsListener makeRuntimePrinter(PrintStream out) {
        return new RuntimeEventsListener() {
            int frames = 0;
            @Override
            public ThreadEventsListener makeThreadListener(VMThread thread) {

                return new ThreadEventsListener() {

                    @Override
                    public FrameEventsListener makeFrameListener(VMFrame frame) {
                        return new FrameEventsListener() {
                            @Override
                            public void onStackPush(VMType value) {
                                out.printf("[%td-%<tm-%<tY %<tT %<tZ][THREAD %s | FRAME #%d] stack pushed value %s\n",
                                  new Date(),
                                  thread.getName(),
                                  frames,
                                  value.toString()
                                );
                            }

                            @Override
                            public void onStackPop() {
                                out.printf("[%td-%<tm-%<tY %<tT %<tZ][THREAD %s | FRAME #%d] stack popped value\n",
                                  new Date(),
                                  thread.getName(),
                                  frames
                                );
                            }

                            @Override
                            public void onLocalWrite(int index, VMType value) {
                                out.printf("[%td-%<tm-%<tY %<tT %<tZ][THREAD %s | FRAME #%d] wrote value %s to local #%d\n",
                                  new Date(),
                                  thread.getName(),
                                  frames,
                                  value.toString(),
                                  index
                                );
                            }

                            @Override
                            public void onInstrExec(ExecutionContext context) {
                                out.printf("[%td-%<tm-%<tY %<tT %<tZ][THREAD %s | FRAME #%d] executing `%s`",
                                        new Date(),
                                        thread.getName(),
                                        frames,
                                        context.getInstr().getMnemonic()
                                );
                            }
                        };
                    }

                    @Override
                    public void onFramePush(VMFrame frame) {
                        out.printf("[%td-%<tm-%<tY %<tT %<tZ][THREAD %s] pushed frame %s\n",
                                new Date(),
                                thread.getName(),
                                frame.getMethod().getMethodName()
                        );
                        frames++;
                    }

                    @Override
                    public void onFramePop(VMFrame frame) {
                        out.printf("[%td-%<tm-%<tY %<tT %<tZ][THREAD %s] popped frame %s\n",
                                new Date(),
                                thread.getName(),
                                frame.getMethod().getMethodName()
                        );
                        frames--;
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
                    public FrameEventsListener makeFrameListener(VMFrame frame) {
                        FrameEventsListener frameListener0 = threadListener0.makeFrameListener(frame);
                        FrameEventsListener frameListener1 = threadListener1.makeFrameListener(frame);

                        return new FrameEventsListener() {
                            @Override
                            public void onStackPush(VMType value) {
                                frameListener0.onStackPush(value);
                                frameListener1.onStackPush(value);
                            }

                            @Override
                            public void onStackPop() {
                                frameListener0.onStackPop();
                                frameListener1.onStackPop();
                            }

                            @Override
                            public void onLocalWrite(int index, VMType value) {
                                frameListener0.onLocalWrite(index, value);
                                frameListener1.onLocalWrite(index, value);
                            }

                            @Override
                            public void onInstrExec(ExecutionContext context) {
                                frameListener0.onInstrExec(context);
                                frameListener1.onInstrExec(context);
                            }
                        };
                    }

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
