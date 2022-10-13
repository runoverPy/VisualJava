package com.visualjava.ui;

import com.visualjava.types.VMType;
import com.visualjava.vm.FrameEventsListener;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class FrameUIElement extends AnchorPane implements FrameEventsListener {
    public static FrameUIElement create() {
        return new FrameUIElement();
    }

    public FrameUIElement() {

    }

    private void pushStack() {}
    private void popStack() {}


    private void putLocal(int index, VMType value) {}
}
