package com.visualjava.ui;

import com.visualjava.vm.VMThread;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

class FXMLThreadBundle {
    public final AnchorPane root;
    public final ThreadController controller;

    public FXMLThreadBundle(VMThread thread) {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(c -> {
            if (c != ThreadController.class) throw new RuntimeException();
            return new ThreadController(thread);
        });
        try {
            root = loader.load(ThreadController.class.getResourceAsStream("/com/visualjava/fxml/Thread.fxml"));
            controller = loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
