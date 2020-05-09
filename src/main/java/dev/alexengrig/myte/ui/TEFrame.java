package dev.alexengrig.myte.ui;

import dev.alexengrig.myte.listener.MyDocumentListener;
import dev.alexengrig.myte.util.SwingExecutor;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.Utilities;
import java.awt.*;
import java.io.*;

public class TEFrame extends JFrame {
    private final SwingExecutor swingExecutor;

    private JTextArea textArea;
    private JPanel leftPanel;
    private JPanel bottomPanel;
    private JLabel caretLabel;

    public TEFrame() {
        setTitle("My Text Editor");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);
        initComponents();
        setVisible(true);
        swingExecutor = new SwingExecutor();
    }

    private void initComponents() {
        setJMenuBar(createMenuBar());
        setLayout(new BorderLayout());
        textArea = new JTextArea();
        textArea.getDocument().addDocumentListener(new MyDocumentListener() {
            private int count = textArea.getLineCount();

            @Override
            public void update(DocumentEvent e) {
                final int previousCount = count;
                swingExecutor.execute(() -> updateLineNumbers(previousCount));
                count = textArea.getLineCount();
            }
        });
        add(new JScrollPane(textArea));
        add(createLeftPanel(), BorderLayout.WEST);
        add(createBottomPanel(), BorderLayout.SOUTH);
    }

    private JMenuBar createMenuBar() {
        final JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        return menuBar;
    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");
        fileMenu.add(createNewFileMenuItem());
        fileMenu.add(createOpenFileMenuItem());
        fileMenu.add(createSaveFileMenuItem());
        return fileMenu;
    }

    private JMenuItem createNewFileMenuItem() {
        final JMenuItem newMenuItem = new JMenuItem("New");
        newMenuItem.addActionListener(e -> {
            System.out.println("New file");
        });
        return newMenuItem;
    }

    private JMenuItem createOpenFileMenuItem() {
        final JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.addActionListener(e -> {
            final JFileChooser fileChooser = new JFileChooser();
            if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(this)) {
                final File file = fileChooser.getSelectedFile();
                try (FileReader reader = new FileReader(file)) {
                    final StringBuilder builder = new StringBuilder();
                    final int length = 2048;
                    final char[] buffer = new char[length];
                    int count;
                    while ((count = reader.read(buffer, 0, length)) != -1) {
                        builder.append(buffer, 0, count);
                    }
                    swingExecutor.execute(() -> textArea.setText(builder.toString()));
                } catch (FileNotFoundException ex) {
                    System.out.println("File not found!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        return openMenuItem;
    }

    private JMenuItem createSaveFileMenuItem() {
        final JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.addActionListener(e -> {
            final JFileChooser fileChooser = new JFileChooser();
            if (JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(this)) {
                final File file = fileChooser.getSelectedFile();
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(textArea.getText());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        return saveMenuItem;
    }

    private JPanel createLeftPanel() {
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        updateLineNumbers(-1);
        return leftPanel;
    }

    private JPanel createBottomPanel() {
        bottomPanel = new JPanel();
        caretLabel = new JLabel(getCaretStatus());
        bottomPanel.add(caretLabel);
        textArea.addCaretListener(e -> {
            caretLabel.setText(getCaretStatus());
        });
        return bottomPanel;
    }

    private void updateLineNumbers(int previousCount) {
        if (previousCount != textArea.getLineCount()) {
            leftPanel.removeAll();
            for (int i = 0; i < textArea.getLineCount(); i++) {
                leftPanel.add(new JLabel(String.valueOf(i + 1)));
            }
            leftPanel.revalidate();
            leftPanel.repaint();
        }
    }

    private String getCaretStatus() {
        try {
            int caretPosition = textArea.getCaretPosition();
            int row = (caretPosition == 0) ? 1 : 0;
            for (int offset = caretPosition; offset > 0; ) {
                offset = Utilities.getRowStart(textArea, offset) - 1;
                row++;
            }
            int offset = Utilities.getRowStart(textArea, caretPosition);
            int col = caretPosition - offset + 1;
            return row + ":" + col;
        } catch (BadLocationException e) {
            e.printStackTrace();
            return "1:1";
        }
    }
}
