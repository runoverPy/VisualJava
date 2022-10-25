package com.visualjava.ui;

import com.visualjava.invoke.ExecutionContext;
import com.visualjava.types.VMType;
import com.visualjava.vm.FrameEventsListener;
import com.visualjava.vm.ThreadEventsListener;
import com.visualjava.vm.VMFrame;
import com.visualjava.vm.VMThread;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.*;

public class ThreadController {
    private final RuntimeController.ThreadAccessor runtimeAccessor;

    private final VMThread thread;

    public ThreadController(VMThread thread, RuntimeController.ThreadAccessor runtimeAccessor) {
        this.thread = thread;
        this.runtimeAccessor = runtimeAccessor;
    }

    @FXML
    private Label threadNameLabel;
    @FXML
    private Label isDaemonLabel;
    @FXML
    private TextField cycleFreqField;
    @FXML
    private VBox frames;

    public void initialize() {
        threadNameLabel.setText(String.format("Thread \"%s\"", thread.getName()));
        isDaemonLabel.setText(String.format("is daemon: %b", thread.isDaemon()));
        cycleFreqField.setText(Integer.toString(thread.getCycleFrequency()));
    }

    @FXML
    public void killThread() {
        thread.killThread();
    }

    @FXML
    public void pauseThread() {
        thread.togglePause();
    }

    @FXML
    public void acceptNewFrequencyValue() {
        String newCycleFreq = cycleFreqField.getText();
        List<String> styleClass = cycleFreqField.getStyleClass();

        try {
            int cycleFreq = Integer.parseInt(newCycleFreq);
            thread.setCycleFrequency(cycleFreq);
            styleClass.removeAll(Collections.singleton("error"));
        } catch (NumberFormatException nfe) {
            System.err.println("\"" + newCycleFreq + "\" does not parse to an int");
            if (!styleClass.contains("error")) styleClass.add("error");
            System.out.println(styleClass);
        }
    }

    public class ThreadEventsVisualizer implements ThreadEventsListener {
        private final Map<VMFrame, FrameUIElement> frameElements = new HashMap<>();
        private final Stack<FrameUIElement> activeFrames = new Stack<>();

        @Override
        public FrameEventsListener makeFrameListener() {
            return new FrameEventsListener() {
                @Override
                public void onStackPush(VMType value) {

                }

                @Override
                public void onStackPop() {

                }

                @Override
                public void onLocalWrite(int index, VMType value) {

                }
            };
        }

        @Override
        public void onFramePush(VMFrame frame) {
            FrameUIElement frameElement = FrameUIElement.create(frame);
            frameElements.put(frame, frameElement);
            Platform.runLater(() -> frames.getChildren().add(0, frameElement));
            activeFrames.push(frameElement);
        }

        @Override
        public void onFramePop(VMFrame frame) {
            FrameUIElement frameElement = frameElements.get(frame);
            Platform.runLater(() -> frames.getChildren().remove(frameElement));
            activeFrames.pop();
            if (!activeFrames.empty()) Platform.runLater(activeFrames.peek()::update);
        }

        @Override
        public void onFreqChange(int newFreq) {
            cycleFreqField.setText(Integer.toString(newFreq));
        }

        @Override
        public void onInstrExec(ExecutionContext context) {
            Platform.runLater(activeFrames.peek()::update);
        }
    }
}
