/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package main;

import javax.swing.*;
import java.awt.*;

import handlers.KeyHandler;
import handlers.MouseHandler;

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
