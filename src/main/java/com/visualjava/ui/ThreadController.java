package com.visualjava.ui;

import com.visualjava.vm.ThreadEventsListener;
import com.visualjava.vm.VMRuntime;
import com.visualjava.vm.VMThread;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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
            return loader.load(ThreadController.class.getResourceAsStream("/com/visualjava/Thread.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private VMThread thread = null;
    private final String threadName;

    public ThreadController(VMThread thread) {
        this(thread.getName());
        this.thread = thread;
    }

    public ThreadController(String threadName) {
        this.threadName = threadName;
    }

    @FXML
    private Label threadNameLabel;
    @FXML
    private Label isDaemonLabel;
    @FXML
    private TextField cycleFreqField;

    public void initialize() {
        threadNameLabel.setText(String.format("Thread \"%s\"", threadName));
        isDaemonLabel.setText(String.format("is daemon: %b", thread.isVMThreadDaemon()));
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
}
