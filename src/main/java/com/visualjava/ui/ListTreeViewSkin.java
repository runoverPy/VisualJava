package com.visualjava.ui;

import javafx.scene.control.ListView;
import javafx.scene.control.SkinBase;
import javafx.scene.control.skin.VirtualContainerBase;
import javafx.scene.control.skin.VirtualFlow;

public class ListTreeViewSkin<T> extends VirtualContainerBase<ListView<T>, ListTreeCell<T>> {
    private final VirtualFlow<ListTreeCell<T>> flow;

    /**
     * Constructor for all SkinBase instances.
     *
     * @param control The control for which this Skin should attach to.
     */
    protected ListTreeViewSkin(ListView<T> control) {
        super(control);

        flow = getVirtualFlow();
        flow.setFixedCellSize(0);
    }

    @Override
    protected int getItemCount() {
        return 0;
    }

    @Override
    protected void updateItemCount() {

    }
}
