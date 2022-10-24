package com.visualjava.ui;

import com.visualjava.invoke.ExecutionContext;
import com.visualjava.vm.ThreadEventsListener;
import com.visualjava.vm.VMFrame;
import com.visualjava.vm.VMThread;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.*;

public class ThreadController implements ThreadEventsListener {
    public static AnchorPane newThread(VMThread thread) {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(c -> {
            if (c != ThreadController.class) throw new RuntimeException();
            return new ThreadController(thread);
        });
        try {
            return loader.load(ThreadController.class.getResourceAsStream("/com/visualjava/fxml/Thread.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final VMThread thread;

    public ThreadController(VMThread thread) {
        this.thread = thread;
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

    @Override
    public void onFramePush(VMFrame frame) {
        frames.getChildren().add(FrameUIElement.create());
    }

    @Override
    public void onFramePop(VMFrame frame) {
        frames.getChildren().remove(0);
    }

    @Override
    public void onFreqChange(int newFreq) {
        cycleFreqField.setText(Integer.toString(thread.getCycleFrequency()));
    }

    @Override
    public void onInstrExec(ExecutionContext context) {

    }

    public class ThreadEventsVisualizer implements ThreadEventsListener {
        @Override
        public void onFramePush(VMFrame frame) {

        }

        @Override
        public void onFramePop(VMFrame frame) {

        }

        @Override
        public void onFreqChange(int newFreq) {
            cycleFreqField.setText(Integer.toString(newFreq));
        }

        @Override
        public void onInstrExec(ExecutionContext context) {

        }
    }
}
