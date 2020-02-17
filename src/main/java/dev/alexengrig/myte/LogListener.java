package dev.alexengrig.myte;

import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.PrintStream;
import java.util.EventObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogListener implements KeyListener, AncestorListener, ComponentListener, ContainerListener, FocusListener, HierarchyListener, MouseListener, CaretListener, VetoableChangeListener, HierarchyBoundsListener, MouseMotionListener, MouseWheelListener, PropertyChangeListener, InputMethodListener {
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final PrintStream printStream = System.out;

    private void print(AWTEvent event) {
        executorService.execute(() -> printStream.println(event));
    }

    private void print(EventObject event) {
        executorService.execute(() -> printStream.println(event));
    }

    private void print(String message) {
        executorService.execute(() -> printStream.println(message));
    }

    @Override
    public void keyTyped(KeyEvent e) {
        print(String.format("KeyListener: Typed key '%c' (%d).", e.getKeyChar(), e.getKeyCode()));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        print(String.format("KeyListener: Pressed key '%c' (%d).", e.getKeyChar(), e.getKeyCode()));
    }

    @Override
    public void keyReleased(KeyEvent e) {
        print(String.format("KeyListener: Released key '%c' (%d).", e.getKeyChar(), e.getKeyCode()));
    }

    @Override
    public void ancestorAdded(AncestorEvent event) {
        print(event);
    }

    @Override
    public void ancestorRemoved(AncestorEvent event) {
        print(event);
    }

    @Override
    public void ancestorMoved(AncestorEvent event) {
        print(event);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        print(e);
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        print(e);
    }

    @Override
    public void componentShown(ComponentEvent e) {
        print(e);
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        print(e);
    }

    @Override
    public void componentAdded(ContainerEvent e) {
        print(e);
    }

    @Override
    public void componentRemoved(ContainerEvent e) {
        print(e);
    }

    @Override
    public void focusGained(FocusEvent e) {
        print(e);
    }

    @Override
    public void focusLost(FocusEvent e) {
        print(e);
    }

    @Override
    public void hierarchyChanged(HierarchyEvent e) {
        print(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        print(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        print(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        print(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        print(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        print(e);
    }

    @Override
    public void caretUpdate(CaretEvent e) {
        print(e);
    }

    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        print(evt);
    }

    @Override
    public void ancestorMoved(HierarchyEvent e) {
        print(e);
    }

    @Override
    public void ancestorResized(HierarchyEvent e) {
        print(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        print(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        print(e);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        print(e);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        print(evt);
    }

    @Override
    public void inputMethodTextChanged(InputMethodEvent event) {
        print(event);
    }

    @Override
    public void caretPositionChanged(InputMethodEvent event) {
        print(event);
    }
}
