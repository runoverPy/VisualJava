package com.visualjava.ui;

import com.visualjava.vm.RuntimeEventsListener;
import com.visualjava.vm.ThreadEventsListener;
import com.visualjava.vm.VMRuntime;
import com.visualjava.vm.VMThread;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.*;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class VisualJavaController implements RuntimeEventsListener {
    @FXML
    public BorderPane rootPane;
    @FXML
    public TabPane threadContainer;
    private final FileChooser fileChooser = new FileChooser();
    private final List<Path> classPath = new ArrayList<>();
    private VMRuntime runtime;

//    private final Terminal terminal;

    public VisualJavaController() {
//        this.terminal = new Terminal();
    }

    public void initialize() {
//        PrintStream
//          out = terminal.createOutStream(),
//          err = terminal.createErrStream();
//        err.println("This feature is useless, there is currenty no plan to implement objects so anything using Strings and PrintStreams are kinda useless");
//        out.println("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");
//        List<File> chosenFile = fileChooser.showOpenMultipleDialog(null);
//        classPath.add(new DirectoryChooser().showDialog(null).toPath());
//        runtime = new VMRuntime(classPath);
//        runtime.init("fibonacci");
//        runtime.setRuntimeListener(this);

        try {
            FXMLLoader loader = new FXMLLoader();
            rootPane.centerProperty().set(loader.load(getClass().getResourceAsStream("/com/visualjava/fxml/Runtime.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addThread(VMThread thread) throws IOException {
        AnchorPane threadPane = ThreadController.newThread(thread);
        Tab threadTab = new Tab();
        threadTab.setText(thread.getName());
        threadTab.setClosable(false);
        threadTab.setContent(threadPane);
        threadContainer.getTabs().add(threadTab);
    }

    public void delThread(VMThread thread) {
        threadContainer
          .getTabs()
          .stream()
          .filter(tab -> tab.getText().equals(thread.getName()))
          .findFirst()
          .ifPresentOrElse(
            threadTab -> threadContainer.getTabs().remove(threadTab),
            () -> System.out.println("Cannot find thread \"" + thread.getName() + "\"")
          );
    }

    @Override
    public ThreadEventsListener makeThreadListener(VMThread thread) {
        return null;
    }

    @Override
    public void onThreadSpawn(VMThread thread) {
        Platform.runLater(() -> {
            try {
                addThread(thread);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void onThreadDeath(VMThread thread) {
        Platform.runLater(() -> delThread(thread));
    }

    @Override
    public void onRuntimeExit() {

    }

    private final static class ClassPathPopup {
    }

    private void onRuntimeStart() {
        FXMLLoader loader = new FXMLLoader();
        try {
            Node runtimeUI = loader.load(getClass().getResourceAsStream(""));
            Object runtimeController = loader.getController();
        } catch (IOException e) {
            throw new RuntimeException("failed to load critical file", e);
        }
    }

    private void onRuntimeDeath() {

    }
}
