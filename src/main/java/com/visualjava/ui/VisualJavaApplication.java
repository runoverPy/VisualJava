package com.visualjava.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VisualJavaApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        BorderPane root = fxmlLoader.load(VisualJavaApplication.class.getResourceAsStream("/com/visualjava/fxml/VisualJava.fxml"));
        Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
        stage.setTitle("Visual Java Interpreter Inspection");
        stage.setScene(scene);
//        stage.getIcons().add(0, new Image(VisualJavaApplication.class.getResourceAsStream("/com/visualjava/icon.png")));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
