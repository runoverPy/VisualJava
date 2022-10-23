package com.visualjava.ui;

import com.visualjava.invoke.ExecutionContext;
import com.visualjava.vm.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;

import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class RuntimeController {
    @FXML
    public CheckBox doWrap;
    @FXML
    public TextArea terminal;
    private PrintStream out;
    private VMRuntime runtime;

    public void initialize() {
        doWrap.selectedProperty().addListener(((observe, oldValue, newValue) -> terminal.setWrapText(newValue)));
        out = makePrintStream(terminal);
        runtime = new VMRuntime("/home/eric/IdeaProjects/VisualJava/testfiles", RuntimeEventsListener.makeRuntimePrinter(out));
//        runtime = new VMRuntime("/home/eric/IdeaProjects/VisualJava/testfiles", new RuntimeEventsVisualizer());
        runtime.init("Fibonacci", 16);
    }

    public static PrintStream makePrintStream(TextArea terminal) {
        return new PrintStream(new OutputStream() {
                    private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

                    @Override
                    public void write(int b) {
                        buffer.write(b & 0xff);
                    }

                    @Override
                    public void flush() {
                        String frag = buffer.toString(StandardCharsets.UTF_8);
                        Platform.runLater(() -> terminal.appendText(frag));
                        buffer.reset();
                    }
                }, true);
    }

    public class RuntimeEventsVisualizer implements RuntimeEventsListener {

        @Override
        public ThreadEventsListener makeThreadListener(VMThread thread) {
            return new ThreadEventsVisualizer();
        }

        @Override
        public void onThreadSpawn(VMThread thread) {

        }

        @Override
        public void onThreadDeath(VMThread thread) {

        }

        @Override
        public void onRuntimeExit() {

        }

        public class ThreadEventsVisualizer implements ThreadEventsListener {

            @Override
            public void onFramePush(VMFrame frame) {

            }

            @Override
            public void onFramePop(VMFrame frame) {

            }

            @Override
            public void onInstrExec(ExecutionContext context) {

            }
        }
    }
}
