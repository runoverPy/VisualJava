package com.visualjava.ui;

import com.visualjava.types.VMType;
import com.visualjava.vm.VMFrame;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FrameUIElement extends AnchorPane {
    private final VMFrame frame;
    private final VBox stack;

    public static FrameUIElement create(VMFrame frame) {
        return new FrameUIElement(frame);
    }

    public FrameUIElement(VMFrame frame) {
        this.frame = frame;
        Label frameLabel = new Label(frame.getMethod().getMethodName());
        setLeftAnchor(frameLabel, 0d);
        setBottomAnchor(frameLabel, 0d);
        getChildren().add(frameLabel);
        Separator frameSeparator = new Separator();
        setLeftAnchor(frameSeparator, 0d);
        setRightAnchor(frameSeparator, 0d);
        setTopAnchor(frameSeparator, 0d);
        getChildren().add(frameSeparator);
        stack = new VBox();
        stack.setAlignment(Pos.BOTTOM_LEFT);
        stack.setPrefSize(100, getPrefHeight());
        setRightAnchor(stack, 0d);
        setBottomAnchor(stack, 0d);
        getChildren().add(stack);
    }

    public void update() {
        stack.getChildren().clear();
        List<Label> items = Arrays.stream(frame.getStackValues()).map(val -> new Label(val.toString())).collect(Collectors.toList());
        Collections.reverse(items);
        stack.getChildren().addAll(items);
    }

    private void pushStack() {}
    private void popStack() {}
    private void putLocal(int index, VMType value) {}
}
