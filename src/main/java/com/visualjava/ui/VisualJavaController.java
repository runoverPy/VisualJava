package com.visualjava.ui;

import com.visualjava.vm.RuntimeEventsListener;
import com.visualjava.vm.VMRuntime;
import com.visualjava.vm.VMThread;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.*;

import javafx.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class VisualJavaController implements RuntimeEventsListener {
    @FXML
    public SplitPane contentRoot;
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

    @FXML
    public void showClassPathDialog(ActionEvent event) {
        Stage popup = new Stage();
        popup.setWidth(400);
        popup.setHeight(300);
        Scene dialogScene = new Scene(new AnchorPane());
        popup.setScene(dialogScene);
        popup.initOwner(contentRoot.getScene().getWindow());
        popup.initModality(Modality.WINDOW_MODAL);
        popup.showAndWait();
    }

    private final static class ClassPathPopup {
    }
}
