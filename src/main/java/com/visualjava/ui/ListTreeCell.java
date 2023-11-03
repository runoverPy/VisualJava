package com.visualjava.ui;

import javafx.scene.control.*;

public class ListTreeCell<T> extends ListCell<TreeItem<T>> {
    public ListTreeCell(ListView<TreeItem<T>> view) {
        super();
        itemProperty().addListener((obs, stale, fresh) -> {
            TreeView<T> treeView = new TreeView<>(fresh);
            treeView.setStyle("-fx-border-opacity: 0");
            treeView.focusedProperty().addListener(((observe, oldValue, newValue) -> updateSelected(newValue)));
            treeView.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
            graphicProperty().set(treeView);
        });
        setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        updateListView(view);
        setSkin(new ListTreeCellSkin<>(this));
    }
}
