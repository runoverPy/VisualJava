package com.visualjava.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class UnopenedController {
    @FXML
    public Button addClassPath;
    @FXML
    public Button delClassPath;
    @FXML
    public Button uMvClassPath;
    @FXML
    public Button dMvClassPath;
    @FXML
    public Button cpyClassPath;
    @FXML
    public ListView<String> classPathList;
    @FXML
    public TreeView<String> classFileTree;
    private TreeItem<String> classFileTreeRoot;
    @FXML
    public TextField mainClass;

    @FXML
    public void addClassPath(ActionEvent event) {
        File classFile = new DirectoryChooser().showDialog(null);
        if (classFile == null) return;
        Path classPath = classFile.toPath();
        classPathList.getItems().add(classPath.toString());
        classFileTreeRoot.getChildren().add(getClassTree(classPath, true));
    }

    @FXML
    public void delClassPath(ActionEvent event) {
        String rmPath = classPathList.getSelectionModel().getSelectedItem();
        if (rmPath == null) return;
        classPathList.getItems().remove(rmPath);
        classFileTreeRoot
                .getChildren()
                .stream()
                .filter(item -> item.getValue().equals(rmPath))
                .findFirst()
                .ifPresent(item -> classFileTreeRoot.getChildren().remove(item));
    }

    @FXML
    public void uMvClassPath(ActionEvent event) {
        int curIndex = classPathList.getSelectionModel().getSelectedIndex();
        String listItem = classPathList.getItems().remove(curIndex);
        classPathList.getItems().add(curIndex - 1, listItem);
        TreeItem<String> treeItem = classFileTreeRoot.getChildren().remove(curIndex);
        classFileTreeRoot.getChildren().add(curIndex - 1, treeItem);
        classPathList.getSelectionModel().select(curIndex - 1);
    }

    @FXML
    public void dMvClassPath(ActionEvent event) {
        int curIndex = classPathList.getSelectionModel().getSelectedIndex();
        String listItem = classPathList.getItems().remove(curIndex);
        classPathList.getItems().add(curIndex + 1, listItem);
        TreeItem<String> treeItem = classFileTreeRoot.getChildren().remove(curIndex);
        classFileTreeRoot.getChildren().add(curIndex + 1, treeItem);
        classPathList.getSelectionModel().select(curIndex + 1); // /home/eric/IdeaProjects/VisualJava/testfiles:/home/eric/IdeaProjects/VisualJava/target/classes
    }

    @FXML
    public void cpyClassPath(ActionEvent event) {
        String classPathRepr = String.join(":", classPathList.getItems());
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(classPathRepr);
        content.putHtml(classPathRepr);
        clipboard.setContent(content);
    }

    @FXML
    public void startRuntime() {

    }

    @FXML
    public void echo(ListView.EditEvent<String> event) {
        System.out.println(event);
    }

    public void initialize() {
        classFileTreeRoot = new TreeItem<>();
        classFileTree.setRoot(classFileTreeRoot);
        classPathList.getSelectionModel().selectedIndexProperty().addListener((observe, oldValue, newValue) -> {
            delClassPath.setDisable(newValue == null);
            uMvClassPath.setDisable(newValue == null || (int) newValue <= 0);
            dMvClassPath.setDisable(newValue == null || (int) newValue >= classPathList.getItems().size() - 1);
        });
    }

    private TreeItem<String> getClassTree(Path path, boolean root) {
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*.class");
        TreeItem<String> treeNode = root ? new TreeItem<>(path.toAbsolutePath().toString()) : new TreeItem<>(path.getFileName().toString());
        if (Files.isDirectory(path)) {
            Map<String, String> nestedClasses = new HashMap<>();
            try {
                List<TreeItem<String>> treeItems = Files
                        .list(path)
                        .map(p -> getClassTree(p, false))
                        .filter(Objects::nonNull)
                        .toList();
                if (treeItems.isEmpty()) return null;
                treeItems.stream().filter(item -> item.getChildren().size() == 1).forEach(item -> {
                    TreeItem<String> onlyChild = item.getChildren().get(0);
                    String onlyChildName = onlyChild.getValue();
                    item.setValue(item.getValue() + "." + onlyChildName);
                    item.getChildren().setAll(onlyChild.getChildren());
                });
                treeNode.getChildren().addAll(treeItems);
                return treeNode;
            } catch (IOException ioe) {
                return new TreeItem<>("# ERROR #");
            }
        } else {
            if (matcher.matches(path.getFileName())) {
                return treeNode;
            }
            else return null;
//            return treeNode;
        }
    }
}
