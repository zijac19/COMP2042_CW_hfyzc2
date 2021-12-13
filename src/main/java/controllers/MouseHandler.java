package controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import main.Controller;

/**
 * This class listens the mouse input from user
 *
 * Refactor by
 * @author Chang Zi Jac
 */
public class MouseHandler implements MouseListener, MouseMotionListener {
    // initialize the variables
    public static boolean MOUSECLICKED = false;
    public static boolean hasPressed = false;
    public static boolean hasReleased = false;

    /**
     * This is an empty method
     */
    public MouseHandler() {
    }

    /**
     * This method listens the mouse clicked by user
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        Controller.mousePoint = e.getPoint();
        MOUSECLICKED = true;
    }

    /**
     * This method listens the mouse pressed by user
     *
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        Controller.mousePoint = e.getPoint();
        hasPressed = true;
        hasReleased = false;
    }

    /**
     * This method listens the mouse released by user
     *
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        hasReleased = true;
        hasPressed = false;
    }

    /**
     * This method listens the mouse entered by user
     *
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * This method listens the mouse exited by user
     *
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }

    /**
     * This method listens the mouse dragged by user
     *
     * @param e
     */
    @Override
    public void mouseDragged(MouseEvent e) {
    }

    /**
     * This method listens the mouse moved by user
     *
     * @param e
     */
    @Override
    public void mouseMoved(MouseEvent e) {Controller.mousePoint = e.getPoint();}
}
