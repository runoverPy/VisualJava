package com.visualjava.ui;

import com.visualjava.invoke.ExecutionContext;
import com.visualjava.types.VMType;
import com.visualjava.vm.events.FrameEventsListener;
import com.visualjava.vm.VMFrame;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;

public class FrameUIElement extends HBox {
    private final VMFrame frame;
    private final Label instr;
    private final VBox stack;

    public static FrameUIElement create(VMFrame frame) {
        return new FrameUIElement(frame);
    }

    public FrameUIElement(VMFrame frame) {
        this.frame = frame;
        Label frameLabel = new Label(frame.getMethod().getMethodName());
        getChildren().add(frameLabel);
        Separator frameSeparator1 = new Separator();
        getChildren().add(frameSeparator1);
        this.instr = new Label(frame.getCurrentInstruction().getMnemonic());
        getChildren().add(instr);
        stack = new VBox();
        stack.setAlignment(Pos.BOTTOM_LEFT);
        stack.setPrefSize(100, getPrefHeight());
        getChildren().add(stack);
    }

    public class FrameEventsVisualizer implements FrameEventsListener {
        @Override
        public void onStackPush(VMType value) {
            Platform.runLater(() -> stack.getChildren().add(0, new Label(value.toString())));
        }

        @Override
        public void onStackPop() {
            Platform.runLater(() -> {
                int index = stack.getChildren().size() - 1;
                stack.getChildren().remove(index);
            });
        }

        @Override
        public void onLocalWrite(int index, VMType value) {

        }

        @Override
        public void onInstrExec(ExecutionContext context) {
            Platform.runLater(() -> {
                    instr.setText(context.getInstr().getMnemonic());
            });
        }
    }
}
