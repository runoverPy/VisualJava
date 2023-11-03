package com.visualjava.ui;

import com.visualjava.vm.*;
import com.visualjava.vm.events.RuntimeEventsListener;
import com.visualjava.vm.events.ThreadEventsListener;
import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RuntimeController {
    public static final PseudoClass safe = PseudoClass.getPseudoClass("safe");

    @FXML
    public CheckBox doWrap;
    @FXML
    public TextArea terminal;
    @FXML
    public TabPane threadContainer;
    @FXML
    public CheckBox threadAutoClose;
    @FXML
    public Button runtimeClose;

    public void initialize() {
        doWrap.selectedProperty().addListener(((observe, oldValue, newValue) -> terminal.setWrapText(newValue)));
        out = makePrintStream(terminal);
        try {
            runtime.start(mainClass, 4);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e); // go back to unopened screen, inform user of problem
        }
    }

    private PrintStream out;
    private final VMRuntime runtime;
    private final String mainClass;

    public RuntimeController(String className, List<Path> classPath, VisualJavaController.Accessor accessor) {
        this.runtime = new VMRuntime(classPath, new RuntimeEventsVisualizer());
        mainClass = className;
    }

    public RuntimeController() {
        this.runtime = new VMRuntime("/home/eric/IdeaProjects/VisualJava/testfiles", new RuntimeEventsVisualizer());
        mainClass = "Fibonacci";
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
            FXMLThreadBundle bundle = new FXMLThreadBundle(thread, new ThreadAccessor());
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
            if (threadAutoClose.selectedProperty().get()) {
                Platform.runLater(() -> threadContainer.getTabs().remove(threadTab));
            } else {
                Platform.runLater(() -> {
                    threadTab.getContent().setDisable(true);
                    threadTab.setClosable(true);
                });
            }
            activeThreadTabs.remove(thread);
            threadBundles.remove(thread);
        }

        @Override
        public void onRuntimeExit() {

        }
    }

    public class ThreadAccessor {}
}
