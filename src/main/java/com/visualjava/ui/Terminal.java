package com.visualjava.ui;

import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Terminal extends SwingNode {
    private final JTextPane terminalPane;

    public Terminal() {
        SwingNode terminalCase = new SwingNode();
        AnchorPane.setTopAnchor(terminalCase, 12d);
        AnchorPane.setBottomAnchor(terminalCase, 12d);
        AnchorPane.setLeftAnchor(terminalCase, 12d);
        AnchorPane.setRightAnchor(terminalCase, 12d);
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
        Scene scene = new Scene(new AnchorPane(terminalCase), 600, 960);
        Stage terminalWindow = new Stage();
        terminalWindow.setTitle("Visual Java Console");
        terminalWindow.setScene(scene);
        terminalWindow.show();
    }

    public PrintStream createOutStream() {
        StyledDocument document = terminalPane.getStyledDocument();
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

    public PrintStream createErrStream() {
        SimpleAttributeSet errAttributes = new SimpleAttributeSet();
        StyleConstants.setForeground(errAttributes, Color.RED);

        StyledDocument document = terminalPane.getStyledDocument();

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
}
