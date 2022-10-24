package com.visualjava.ui;

import com.visualjava.types.VMType;
import com.visualjava.vm.VMFrame;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class FrameUIElement extends AnchorPane {
    private final VMFrame frame;

    public static FrameUIElement create(VMFrame frame) {
        return new FrameUIElement(frame);
    }

    public FrameUIElement(VMFrame frame) {
        this.frame = frame;
        super.setPrefHeight(frame.getMethod().getStackSize() * 20);
        setMinHeight(USE_PREF_SIZE);
//        setBackground(new Background(new BackgroundFill(Color.color(0.7, 0.3, 0.3), new CornerRadii(0), new Insets(0))));
        getChildren().add(new Label(frame.getMethod().getMethodName()));
        Separator frameSeparator = new Separator();
        setLeftAnchor(frameSeparator, 0d);
        setRightAnchor(frameSeparator, 0d);
        setTopAnchor(frameSeparator, 0d);
        getChildren().add(frameSeparator);
    }

    private void pushStack() {}
    private void popStack() {}


    private void putLocal(int index, VMType value) {}
}
