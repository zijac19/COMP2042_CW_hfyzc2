package handlers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import main.Controller;

public class MouseHandler implements MouseListener, MouseMotionListener {

    public static boolean MOUSECLICKED = false;
    public static boolean hasPressed = false;
    public static boolean hasReleased = false;

    public MouseHandler() {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Controller.mousePoint = e.getPoint();
        MOUSECLICKED = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Controller.mousePoint = e.getPoint();
        hasPressed = true;
        hasReleased = false;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        hasReleased = true;
        hasPressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {Controller.mousePoint = e.getPoint();}
}
