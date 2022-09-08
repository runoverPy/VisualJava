package com.visualjava.ui;

import com.visualjava.vm.RuntimeEventsListener;
import com.visualjava.vm.VMRuntime;
import com.visualjava.vm.VMThread;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class VisualJavaController implements RuntimeEventsListener {
    @FXML
    public TabPane threadContainer;
    @FXML
    public SwingNode terminalCase;

    private static VisualJavaController instance;

    public static VisualJavaController getInstance() {
        return instance;
    }

    public VisualJavaController() throws InstantiationException {
        if (instance == null) instance = this;
        else throw new InstantiationException("Singleton Instance already created");
        VMRuntime.getInstance().setRuntimeListener(this);
    }

    public void initialize() {
        JTextPane terminalPane = new JTextPane();
        terminalPane.setEditable(false);
        terminalPane.setBackground(Color.BLACK);
        terminalPane.setForeground(Color.GREEN);
        terminalPane.setFont(new Font("Monospaced", Font.PLAIN, 14));
        terminalPane.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        JScrollPane textScrollPane = new JScrollPane(terminalPane);
        textScrollPane.setBounds(10, 10, 450, 500);
        textScrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        terminalCase.setContent(textScrollPane);

        PrintStream out, err;
        out = createOutStream(terminalPane);
        err = createErrStream(terminalPane);

        System.setOut(out);
        System.setErr(err);
    }

    private static PrintStream createOutStream(JTextPane textPane) {
        StyledDocument document = textPane.getStyledDocument();
        OutputStream backOut = new OutputStream() {
            private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            @Override
            public void write(int data) {
                buffer.write(data & 0xff);
            }

            @Override
            public void flush() {
                try {
                    document.insertString(document.getLength(), buffer.toString(StandardCharsets.UTF_8), null);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
                buffer.reset();
            }
        };
        return new PrintStream(backOut, true);
    }

    private static PrintStream createErrStream(JTextPane textPane) {
        SimpleAttributeSet errAttributes = new SimpleAttributeSet();
        StyleConstants.setForeground(errAttributes, Color.RED);

        StyledDocument document = textPane.getStyledDocument();

        OutputStream backErr = new OutputStream() {
            private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            @Override
            public void write(int data) {
                buffer.write(data & 0xff);
            }

            @Override
            public void flush() {
                try {
                    document.insertString(
                      document.getLength(),
                      buffer.toString(StandardCharsets.UTF_8),
                      errAttributes
                    );
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
                buffer.reset();
            }
        };
        return new PrintStream(backErr, true);
    }

    public void addThread(VMThread thread) throws IOException {
        AnchorPane threadPane = ThreadController.newThread(thread);
        Tab threadTab = new Tab();
        threadTab.setText(thread.getName());
        threadTab.setClosable(false);
        threadTab.setContent(threadPane);
        threadContainer.getTabs().add(threadTab);
    }

    public void delThread(VMThread thread) {
        threadContainer
          .getTabs()
          .stream()
          .filter(tab -> tab.getText().equals(thread.getName()))
          .findFirst()
          .ifPresentOrElse(
            threadTab -> threadContainer.getTabs().remove(threadTab),
            () -> System.out.println("Cannot find thread \"" + thread.getName() + "\"")
          );
    }

    @Override
    public void onThreadSpawn(VMThread thread) {
        Platform.runLater(() -> {
            try {
                addThread(thread);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void onThreadDeath(VMThread thread) {
        Platform.runLater(() -> delThread(thread));
    }

    @Override
    public void onRuntimeExit() {

    }
}
