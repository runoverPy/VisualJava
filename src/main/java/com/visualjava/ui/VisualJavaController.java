package com.visualjava.ui;

import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
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

public class VisualJavaController {
    @FXML
    public TabPane threadContainer;
    @FXML
    public TextField threadName;
    @FXML
    public SwingNode terminalCase;
    private JTextPane terminalPane;

    private static VisualJavaController instance;

    public static VisualJavaController getInstance() {
        return instance;
    }

    public VisualJavaController() throws InstantiationException {
        if (instance == null) instance = this;
        else throw new InstantiationException("Singleton Instance already created");
    }

    public void initialize() {
        terminalPane = new JTextPane();
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

    public void killThread(String threadName) {
        Tab mortalThreadTab = threadContainer
          .getTabs()
          .stream()
          .filter(tab -> tab.getText().equals(threadName))
          .findFirst()
          .get();
        Tab selectedTab = threadContainer.getSelectionModel().getSelectedItem();
    }
    public void addThread(String threadName) throws IOException {
        AnchorPane threadPane = ThreadController.newThread(threadName);
        Tab threadTab = new Tab();
        threadTab.setText(threadName);
        threadTab.setClosable(false);
        threadTab.setContent(threadPane);
        threadContainer.getTabs().add(threadTab);
    }

    public void addThread(ActionEvent action) throws IOException {
        String threadName = this.threadName.getText();
        if (threadName.equals("")) System.out.println("No name is not a valid name");
        else addThread(threadName);
        this.threadName.clear();
    }

    @FXML
    public void killThread() {
        System.out.println("killing thread");
    }

    @FXML
    public void acceptEvent(Event e) {
        System.out.println(e);
    }
}
