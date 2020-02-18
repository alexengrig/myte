package dev.alexengrig.myte;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class TextEditor extends JFrame {
    private final Executor backgroundExecutor;
    private JButton stopButton;

    public TextEditor() throws HeadlessException {
        this.backgroundExecutor = Executors.newCachedThreadPool();
        setTitle("My Text Editor");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setLayout(new FlowLayout());

        stopButton = new JButton("Stop");
        add(stopButton);

        JButton goButton = new JButton("Go");
        goButton.addActionListener(getGoButtonListener());
        add(goButton);

    }

    private ActionListener getGoButtonListener() {
        return event -> {
            final FutureTask<Object> task = new FutureTask<>(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("Completed!");
                } catch (InterruptedException e) {
                    System.out.println("Interrupted");
                }
            }, null);
            stopButton.addActionListener(e -> task.cancel(true));
            backgroundExecutor.execute(task);
        };
    }
}
