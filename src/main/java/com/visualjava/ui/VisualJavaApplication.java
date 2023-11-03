package com.visualjava.ui;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventDispatchChain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.skin.TreeViewSkin;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
        scene.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, event -> {
            System.err.println(event.getTarget());
        });
//        stage.getIcons().add(0, new Image(VisualJavaApplication.class.getResourceAsStream("/com/visualjava/icon.png")));
        stage.show();
//        BorderPane pane = new BorderPane();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
