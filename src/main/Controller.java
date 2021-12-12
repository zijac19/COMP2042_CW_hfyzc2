package main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import handlers.KeyHandler;
import handlers.MouseHandler;
import models.Music;


public class Controller extends JPanel implements Runnable {

	public enum STATE{
		MENU,
		PICKLEVEL,
		GAME,
		GAMEOVER,
		WINSCREEN,
		PICKUSERLEVEL,
		CREATELEVEL,
	}

	private Thread thread;
	private Graphics2D g2d;
	private BufferedImage image;
	
	private static STATE state = STATE.MENU;
	private static GameBoard gameBoard;
	//private static GameOver gameOver;
	//private static WinScreen winScreen;
	private static HomeMenu homeMenu;
	//private static CreateLevel createLevel;
	//private PickLevel pickLevel;
	//private PickUserLevel pickUserLevel;
	
	public static Point mousePoint = new Point(0, 0);
	public static Font bigFont = new Font("TimesRoman", Font.PLAIN, 25);
	public static Font smallFont = new Font("TimesRoman", Font.PLAIN, 18);
	
	private boolean running = true;
	private static final long serialVersionUID = 1L;
	private long lastTime;
	public double fps;
	public static int score = 0;
	public static int level = 0;
	
	public Controller() {
		super();
		setPreferredSize(new Dimension(Frame.WIDTH, Frame.HEIGHT));
		setFocusable(true);
		requestFocus(true);
	}
	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}
	private void init() {
		image = new BufferedImage(Frame.WIDTH, Frame.HEIGHT, BufferedImage.TYPE_INT_RGB);
		g2d = (Graphics2D) image.getGraphics();
		this.addKeyListener(new KeyHandler());
		this.addMouseListener(new MouseHandler());
		this.addMouseMotionListener(new MouseHandler());
		Music.init();
		homeMenu = new HomeMenu();
		//Files.init();
		//pickLevel = new PickLevel();
		//pickUserLevel = new PickUserLevel();
	}

	public void run() {
		init();
		while (running) {
			lastTime = System.nanoTime();
			display();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {

			}
			fps = 1000000000.0 / (System.nanoTime() - lastTime);
			lastTime = System.nanoTime();
		}
	}

	private void display() {
		switch(state) {
		case MENU:
			homeMenu.paint(g2d);
			homeMenu.mouseEvent();
			break;
		/*case PICKLEVEL:
			pickLevel.tick();
			pickLevel.render(g);
			break;*/
		case GAME:
			if (!GameBoard.showPauseMenu)
				gameBoard.paint(g2d);
				gameBoard.keyEvent();
			if (GameBoard.showPauseMenu)
				gameBoard.drawMenu(g2d);
				gameBoard.mouseEvent();
			break;
		/*case GAMEOVER:
			gameOver.tick();
			gameOver.render(g);
			break;
		case WINSCREEN:
			winScreen.tick();
			winScreen.render(g);
			break;
		case PICKUSERLEVEL:
			pickUserLevel.tick();
			pickUserLevel.render(g);
			break;
		case CREATELEVEL:
			createLevel.tick();
			createLevel.render(g);
			break;*/
		default:
			break;
	}
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}
	
	public static void switchStates(STATE state) {
		Controller.state = state;
		if(state == STATE.MENU) {
			homeMenu = new HomeMenu();
		}
		if(state == STATE.GAME) {
			Music.GameStart();
			gameBoard = new GameBoard();
			Frame.windowsize();
		}
		if(state == STATE.WINSCREEN) {
			//winScreen = new WinScreen();
		}
	}
	public static void switchStates(STATE state, int level) {
		if(state == STATE.GAME) {
			score = 0;
			//gameBoard = new Game(level);
		}
		if(state == STATE.CREATELEVEL) {
			//createLevel = new CreateLevel(level);
		}
		Controller.level = level;
		Controller.state = state;
	}
}
