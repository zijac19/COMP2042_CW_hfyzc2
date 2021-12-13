package controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class listens the keyboard input from user
 *
 * Refactor by
 * @author Chang Zi Jac
 */
public class KeyHandler implements KeyListener{

	// initialize the variables
	public static boolean LEFT = false;
	public static boolean RIGHT = false;
	public static boolean SPACE = false;
	public static boolean ESCAPE = false;
	public static boolean F1 = false;

	public static boolean keyreleased = false;

	/**
	 * This is an empty method
	 */
	public KeyHandler() {
		
	}

	/**
	 * This method listens the key typed by user
	 *
	 * @param e
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	/**
	 * This method listens the key pressed by user
	 *
	 * @param e
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == 112) {
			F1 = true;
		}
		if(e.getKeyCode() == 27) {
			ESCAPE = true;
		}
		if(e.getKeyCode() == 32) {
			SPACE = true;
		}
		if(e.getKeyCode() == 65 || e.getKeyCode() == 37) {
			LEFT = true;
		}
		if(e.getKeyCode() == 68 || e.getKeyCode() == 39) {
			RIGHT = true;
		}
	}

	/**
	 * This method listens the key released by user
	 *
	 * @param e
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == 112) {
			F1 = false;
		}
		if(e.getKeyCode() == 65 || e.getKeyCode() == 37) {
			LEFT = false;
		}
		if(e.getKeyCode() == 68 || e.getKeyCode() == 39) {
			RIGHT = false;
		}
		keyreleased = true;
	}

}
