package main;

import javax.swing.*;

import handlers.KeyHandler;
import handlers.MouseHandler;
import handlers.WindowHandler;

import java.awt.*;

public class Frame extends JFrame{

	protected static JFrame frame;
	
	public static int WIDTH = 500;
	public static int HEIGHT = 513;
	
	public static void main(String[] args) {
		frame = new JFrame("Brick Destroy");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new Controller());
		frame.pack();
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.addWindowListener(new WindowHandler());
		frame.addWindowFocusListener(new WindowHandler());
	}

	public static void windowsize() {
		frame.setTitle("Brick Destroy");
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		frame.setFocusable(true);
		frame.requestFocusInWindow();
		frame.addKeyListener(new KeyHandler());
		frame.addMouseListener(new MouseHandler());
		frame.addMouseMotionListener(new MouseHandler());
		defaultcursor();
	}

	public static void handcursor() {
		frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	public static void defaultcursor() {
		frame.setCursor(Cursor.getDefaultCursor());
	}
}
