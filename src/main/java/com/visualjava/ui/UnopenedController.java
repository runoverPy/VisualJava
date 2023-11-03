package com.visualjava.ui;

import javafx.css.PseudoClass;
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
    public static final PseudoClass problem = PseudoClass.getPseudoClass("problem");

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
//    @FXML
//    public ListView<TreeItem<String>> classPathListTree;
//    @FXML
//    public ListTreeView<String> classPathListTree;
    @FXML
    public TreeView<String> classFileTree;
    @FXML
    public TextField mainClass;

    private final VisualJavaController.Accessor accessor;

    public UnopenedController(VisualJavaController.Accessor accessor) {
        this.accessor = accessor;
    }

    @FXML
    public void addClassPath(ActionEvent event) {
        File classFile = new DirectoryChooser().showDialog(null);
        if (classFile == null) return;
        Path classPath = classFile.toPath();
        classPathList.getItems().add(classPath.toString());
        TreeItem<String> classPathTreeItem = getClassTree(classPath, true);
//        classFileTreeRoot.getChildren().add(classPathTreeItem);
//        classPathListTree.getItems().add(classPathTreeItem);
    }

    @FXML
    public void delClassPath(ActionEvent event) {
        String rmPath = classPathList.getSelectionModel().getSelectedItem();
        if (rmPath == null) return;
        classPathList.getItems().remove(rmPath);
//        classFileTreeRoot
//                .getChildren()
//                .stream()
//                .filter(item -> item.getValue().equals(rmPath))
//                .findFirst()
//                .ifPresent(item -> classFileTreeRoot.getChildren().remove(item));
    }

    @FXML
    public void uMvClassPath(ActionEvent event) {
        int curIndex = classPathList.getSelectionModel().getSelectedIndex();
        String listItem = classPathList.getItems().remove(curIndex);
        classPathList.getItems().add(curIndex - 1, listItem);
//        TreeItem<String> treeItem = classFileTreeRoot.getChildren().remove(curIndex);
//        classFileTreeRoot.getChildren().add(curIndex - 1, treeItem);
        classPathList.getSelectionModel().select(curIndex - 1);
    }

    @FXML
    public void dMvClassPath(ActionEvent event) {
        int curIndex = classPathList.getSelectionModel().getSelectedIndex();
        String listItem = classPathList.getItems().remove(curIndex);
        classPathList.getItems().add(curIndex + 1, listItem);
//        TreeItem<String> treeItem = classFileTreeRoot.getChildren().remove(curIndex);
//        classFileTreeRoot.getChildren().add(curIndex + 1, treeItem);
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
        boolean error = false;
        // check if mainClass is not empty
        String mainClass = this.mainClass.getText();
        if (mainClass.equals("")) {
            System.err.println("Choose a `main`-class");
            error = true;
        }
        // check if classpath is not empty
        List<Path> classPath = classPathList.getItems().stream().map(Path::of).toList();
        if (classPath.isEmpty()) {
            System.err.println("Select files for the classpath");
            error = true;
        }
        // check if class exists in classpath
        Path mainClassPath = Path.of(mainClass.replace(".", "/") + ".class");
        if (classPath.stream().noneMatch(path -> Files.exists(path.resolve(mainClassPath)))) {
            System.err.println("main class not found on classpath");
            error = true;
        }
        if (error) {
            return;
        }
        System.out.println("Runtime initializing");
        accessor.initRuntime(mainClass, classPath);
    }

    public void initialize() {
//        classFileTreeRoot = new TreeItem<>();
//        classFileTree.setRoot(classFileTreeRoot);
        classPathList.getSelectionModel().selectedIndexProperty().addListener((observe, oldValue, newValue) -> {
            delClassPath.setDisable(newValue == null);
            uMvClassPath.setDisable(newValue == null || (int) newValue <= 0);
            dMvClassPath.setDisable(newValue == null || (int) newValue >= classPathList.getItems().size() - 1);
        });

        classPathList.getSelectionModel().selectedItemProperty().addListener((observe, oldValue, newValue) -> {
            classFileTree.setRoot(getClassTree(Path.of(newValue), true));
        });
//        classPathListTree.setCellFactory(ListTreeCell::new);
        ContextMenu mainClassValues = new ContextMenu();
        mainClassValues.getItems().add(new MenuItem("Fibonacci"));
        mainClass.setContextMenu(mainClassValues);
    }

    private TreeItem<String> getClassTree(Path path, boolean root) {
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*.class");
        TreeItem<String> treeNode = root ? new TreeItem<>(path.toAbsolutePath().toString()) : new TreeItem<>(path.getFileName().toString());
        if (Files.isDirectory(path)) {
            Map<String, String> nestedClasses = new HashMap<>(); // TODO: 26.10.22 make nested classes appear as children of their outer class
            try {
                List<TreeItem<String>> treeItems = Files
                        .list(path)
                        .peek(System.out::println)
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
                treeNode.setValue(treeNode.getValue().replace(".class", ""));
                return treeNode;
            }
            else return null;
//            return treeNode;
        }
    }
}
