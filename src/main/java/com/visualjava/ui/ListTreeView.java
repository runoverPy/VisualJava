package com.visualjava.ui;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ListTreeView<T> extends VBox {
    private final ObservableList<TreeItem<T>> treeItems = FXCollections.observableList(new LinkedList<>());
    private SelectionModel<T> selectionModel = new SingleSelectionModel<>() {
        @Override
        protected T getModelItem(int index) {
            return storedModelItems.get(index).getValue();
        }

        @Override
        protected int getItemCount() {
            return getChildren().size();
        }
    };
//
//    @Override
//    protected Skin<?> createDefaultSkin() {
//        return new ListTreeViewSkin<>(this);
//    }



    private final Map<TreeItem<T>, TreeView<T>> currentTrees = new HashMap<>();
    private final List<TreeItem<T>> storedModelItems = new LinkedList<>();

    public ObservableList<TreeItem<T>> getItems() {
        return treeItems;
    }

    public ListTreeView() {
        setFillWidth(true);
        treeItems.addListener((ListChangeListener<? super TreeItem<T>>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    List<? extends TreeItem<T>> additions = change.getAddedSubList();
                    for (TreeItem<T> addition : additions) {
                        TreeView<T> treeView = new TreeView<>(addition);
//                        VBox.setVgrow(treeView, Priority.NEVER);
                        treeView.setMinHeight(0);
//                        treeView.setPrefHeight(0);
                        treeView.autosize();
                        System.out.println("min height: " + treeView.getMinHeight() + " pref height: " + treeView.getPrefHeight() + " height: " + treeView.getStyle());
                        System.out.println("is resizable: " + treeView.isResizable());
//                        treeView.selectionModelProperty().addListener((obs, old, neu) -> selectionModel.select(neu));
                        getChildren().add(treeView);
                    }
                    storedModelItems.addAll(change.getAddedSubList());
                }

                if (change.wasRemoved()) {
                    change.getRemoved().forEach(item -> {
                        getChildren().remove(currentTrees.get(item));
                        currentTrees.remove(item);
                    });
                }
            }
        });
    }
}
