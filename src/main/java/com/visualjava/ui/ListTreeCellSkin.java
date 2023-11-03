package com.visualjava.ui;

import javafx.scene.control.ListCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.skin.ListCellSkin;

public class ListTreeCellSkin<T> extends ListCellSkin<TreeItem<T>> {
    public ListTreeCellSkin(ListCell<TreeItem<T>> control) {
        super(control);
    }
}
