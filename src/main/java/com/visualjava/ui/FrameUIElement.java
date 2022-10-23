package com.visualjava.ui;

import com.visualjava.types.VMType;
import javafx.scene.layout.AnchorPane;

public class FrameUIElement extends AnchorPane {
    public static FrameUIElement create() {
        return new FrameUIElement();
    }

    public FrameUIElement() {

    }

    private void pushStack() {}
    private void popStack() {}


    private void putLocal(int index, VMType value) {}
}
