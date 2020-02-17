package dev.alexengrig.myte;

import javax.swing.*;
import java.awt.*;

public class TextEditor extends JFrame {
    public TextEditor() throws HeadlessException {
        setTitle("My Text Editor");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        LogListener listener = new LogListener();
        JTextArea textArea = new JTextArea();
        textArea.addKeyListener(listener);
        textArea.addAncestorListener(listener);
        textArea.addComponentListener(listener);
        textArea.addContainerListener(listener);
        textArea.addFocusListener(listener);
        textArea.addHierarchyListener(listener);
        textArea.addMouseListener(listener);
        textArea.addCaretListener(listener);
        textArea.addVetoableChangeListener(listener);
        textArea.addHierarchyBoundsListener(listener);
        textArea.addMouseMotionListener(listener);
        textArea.addMouseWheelListener(listener);
        textArea.addPropertyChangeListener(listener);
        textArea.addInputMethodListener(listener);
        add(new JScrollPane(textArea));
    }
}
