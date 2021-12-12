package handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

	public static boolean LEFT = false;
	public static boolean RIGHT = false;
	public static boolean SPACE = false;
	public static boolean ESCAPE = false;
	public static boolean F1 = false;

	public static boolean keyreleased = false;

	public KeyHandler() {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

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
