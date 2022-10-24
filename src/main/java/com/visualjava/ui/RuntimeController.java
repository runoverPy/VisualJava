package com.visualjava.ui;

import com.visualjava.vm.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RuntimeController {
    @FXML
    public CheckBox doWrap;
    @FXML
    public TextArea terminal;
    @FXML
    public TabPane threadContainer;
    private PrintStream out;
    private VMRuntime runtime;

    public void initialize() {
        doWrap.selectedProperty().addListener(((observe, oldValue, newValue) -> terminal.setWrapText(newValue)));
        out = makePrintStream(terminal);
        runtime = new VMRuntime("/home/eric/IdeaProjects/VisualJava/testfiles", RuntimeEventsListener.forkRuntimeListener(RuntimeEventsListener.makeRuntimePrinter(out), new RuntimeEventsVisualizer()));
//        runtime = new VMRuntime("/home/eric/IdeaProjects/VisualJava/testfiles", new RuntimeEventsVisualizer());
        try {
            runtime.start("Fibonacci", 16);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e); // go back to unopened screen, inform user of problem
        }
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
        private final Map<VMThread, FXMLThreadBundle> threadBundles = new HashMap<>();
        private final Map<VMThread, Tab> activeThreadTabs = new HashMap<>();

        @Override
        public ThreadEventsListener makeThreadListener(VMThread thread) {
            FXMLThreadBundle bundle = new FXMLThreadBundle(thread);
            threadBundles.put(thread, bundle);
            return bundle.controller.new ThreadEventsVisualizer();
        }

        @Override
        public void onThreadStart(VMThread thread) {
            FXMLThreadBundle bundle = threadBundles.get(thread);
            Tab threadTab = new Tab(thread.getName(), bundle.root);
            threadTab.setClosable(false);
            activeThreadTabs.put(thread, threadTab);
            Platform.runLater(() -> threadContainer.getTabs().add(threadTab));
        }

        @Override
        public void onThreadDeath(VMThread thread) {
            Tab threadTab = activeThreadTabs.get(thread);
            Platform.runLater(() -> threadContainer.getTabs().remove(threadTab));
            activeThreadTabs.remove(thread);
            threadBundles.remove(thread);
        }

        @Override
        public void onRuntimeExit() {

        }
    }
}
