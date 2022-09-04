package com.visualjava.ui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ThreadController {
    private static final Map<String, ThreadController> threads = new HashMap<>();

    public static AnchorPane newThread(String threadName) throws IOException {
        FXMLLoader loader = new FXMLLoader(ThreadController.class.getResource("/com/visualjava/Thread.fxml"));
        return loader.load();
    }

    public ThreadController() {
        System.out.println("created new thread!");
    }

    @FXML
    private Label threadName;

    @FXML
    private TextField cycleFreqField;

    @FXML
    public void killThread() {
        System.out.println(threadName.getText());
    }

    @FXML
    public void acceptNewFrequencyValue(Event e) {
        String newCycleFreq = cycleFreqField.getText();
        try {
            int cycleFreq = Integer.parseInt(newCycleFreq);
            System.out.println(cycleFreq);
        } catch (NumberFormatException nfe) {
            System.err.println("\"" + newCycleFreq + "\" does not parse to an int");
        }
    }
}
