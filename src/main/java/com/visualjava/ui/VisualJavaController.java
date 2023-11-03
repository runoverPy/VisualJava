package com.visualjava.ui;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.Path;
import java.util.*;

public class VisualJavaController {
    @FXML
    public BorderPane rootPane;
    @FXML
    public TabPane mainContainer;

    private final Map<RuntimeController, Tab> runtimeTabs = new HashMap<>();

    public void initialize() {
        System.err.println("initializing");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setControllerFactory(c -> {
                if (c != UnopenedController.class) throw new RuntimeException();
                return new UnopenedController(new Accessor());
            });
            mainContainer.getTabs().add(new Tab("Start Runtimes", loader.load(getClass().getResourceAsStream("/com/visualjava/fxml/Unopened.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public class Accessor {
        public void initRuntime(String mainClass, List<Path> classPath) {
            // something something start a new runtime and add it to a list
            // this method will not do complicated verification, and assume the mainClass is present on the classPath and
            // the class name is well-formed
            FXMLLoader loader = new FXMLLoader();
            loader.setControllerFactory(c -> {
                if (c != RuntimeController.class) throw new RuntimeException();
                return new RuntimeController(mainClass, classPath, new Accessor());
            });
            try {
                Node root = loader.load(getClass().getResourceAsStream("/com/visualjava/fxml/Runtime.fxml"));
                RuntimeController controller = loader.getController();
                Tab runtimeTab = new Tab("Runtime #" + runtimeTabs.size(), root);
                runtimeTabs.put(controller, runtimeTab);
                Platform.runLater(() -> {
                    mainContainer.getTabs().add(runtimeTab);
                    mainContainer.getSelectionModel().select(runtimeTab);
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void closeRuntime(RuntimeController runtime) {
            Tab runtimeTab = runtimeTabs.get(runtime);
            Platform.runLater(() -> mainContainer.getTabs().remove(runtimeTab));
        }
    }

    @FXML
    public void printStackTrace(Event event) {
//        StringBuffer buffer = new StringBuffer();
//
//        if (event.getTarget() instanceof Node)
//            printSceneGraph(((Node) event.getTarget()).getScene().getRoot(), buffer, "", "");
//        else buffer.append(event.getTarget());
//        System.out.println(buffer);
        new Exception().printStackTrace();
    }

    private void printSceneGraph(Node node, StringBuffer buffer, String prefix, String childPrefix) {
        buffer.append(prefix).append(node.getClass().getName()).append("\n");
        if (node instanceof Parent) {
            ObservableList<Node> children = ((Parent) node).getChildrenUnmodifiable();
            for (Iterator<Node> iter = children.iterator(); iter.hasNext();) {
                Node next = iter.next();
                if (iter.hasNext()) {
                    printSceneGraph(next, buffer, childPrefix + "├── ", childPrefix + "│   ");
                } else {
                    printSceneGraph(next, buffer, childPrefix + "└── ", childPrefix + "    ");
                }
            }

        }
    }
}
