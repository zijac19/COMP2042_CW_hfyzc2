package handlers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener, MouseMotionListener {

    public static boolean MOUSECLICKED = false;
    public static boolean MOUSEDOWN = false;
    public static boolean hasPressed = false;
    public static boolean hasReleased = false;
    public static Point mousePoint;

    public MouseHandler() {

    }

    public void mouseClicked(MouseEvent e) {
//		System.out.println("X: " + e.getX() + "\nY: " + e.getY() + "\n");
        mousePoint = e.getPoint();
        MOUSECLICKED = true;
    }
    public void mousePressed(MouseEvent e) {
        mousePoint = e.getPoint();
        hasPressed = true;
        hasReleased = false;
    }
    public void mouseReleased(MouseEvent e) {
        hasReleased = true;
        hasPressed = false;
    }
    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
    public void mouseDragged(MouseEvent e) {

    }
    public void mouseMoved(MouseEvent e) {
        mousePoint = e.getPoint();
    }
}
