package dev.alexengrig.myte.ui;

import dev.alexengrig.myte.listener.MyDocumentListener;
import dev.alexengrig.myte.util.SwingExecutor;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TEFrame extends JFrame {
    private final SwingExecutor swingExecutor;

    private JTextArea textArea;
    private JPanel leftPanel;

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
        textArea = new JTextArea("Input your text here");
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

    private JPanel createLeftPanel() {
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        updateLineNumbers(-1);
        return leftPanel;
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

}
