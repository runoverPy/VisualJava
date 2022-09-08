package com.visualjava.ui;

import com.visualjava.vm.RuntimeEventsListener;
import com.visualjava.vm.VMRuntime;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VisualJavaApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        VBox root = fxmlLoader.load(HelloApplication.class.getResourceAsStream("/com/visualjava/VisualJava.fxml"));
        Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
        stage.setTitle("Visual Java Interpreter Inspection");
        stage.setScene(scene);
        stage.show();
        VMRuntime.launch(getParameters().getRaw().toArray(new String[0]));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
