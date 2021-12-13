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
package view;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;

import controllers.KeyHandler;
import controllers.MouseHandler;
import models.Music;
import models.Wall;
import models.Player;
import models.Brick;
import models.Ball;
import controllers.ImageLoader;
import main.Controller;
import main.Frame;

import static main.Frame.frame;

/**
 * This class is the in-game scene
 *
 * Refactor by
 * @author Chang Zi Jac
 */
public class GameBoard extends JPanel {
    //initialize variables
    private static boolean gaming = false;

    private static final String CONTINUE = "Continue";
    private static final String RESTART = "Restart";
    private static final String EXIT = "Exit";
    private static final String PAUSE = "Pause Menu";
    private static final int TEXT_SIZE = 30;
    private static final Color MENU_COLOR = new Color(0,255,0);
    private static final Color CLICKED_TEXT = Color.WHITE;

    private static final int DEF_WIDTH = 500;
    private static final int DEF_HEIGHT = 513;

    private final Image InGameBackground;

    private Timer gameTimer;

    private final Wall wall;

    private String message;
    private String message2;
    private String status;

    public static boolean showPauseMenu;

    private final Font menuFont;

    private Rectangle continueButtonRect;
    private Rectangle exitButtonRect;
    private Rectangle restartButtonRect;
    private int strLen;

    private boolean continueClicked;
    private boolean restartClicked;
    private boolean exitClicked;

    /**
     * This method initialize the scene status
     */
    public GameBoard(){
        super();

        strLen = 0;
        showPauseMenu = false;

        menuFont = new Font("Monospaced",Font.PLAIN,TEXT_SIZE);

        InGameBackground = new ImageLoader(ImageLoader.ingamebackground).getImage();
        message = "";
        message2 = "";
        status = "";
        wall = new Wall(new Rectangle(0,0,DEF_WIDTH,DEF_HEIGHT),30,3,3,new Point(250,430));

        //initialize the first level
        wall.nextLevel();
        gametime();
    }

    /**
     * This method set the game timer
     */
    private void gametime() {
        gaming = true;
        gameTimer = new Timer(10,e ->{
            wall.move();
            wall.findImpacts();
            message = "";
            message2 = "";
            status = String.format("Score: %d Bricks: %d Balls %d",wall.getScore(),wall.getBrickCount(),wall.getBallCount());
            if(wall.isBallLost()){
                if(wall.ballEnd()){
                    message = "     Game Over";
                    message2 = String.format("  Highest Score: %d",wall.getScore());
                    wall.wallReset();
                }
                wall.ballReset();
                gameTimer.stop();
            }
            else if(wall.isDone()){
                if(wall.hasLevel()){
                    message = "Go to Next Level";
                    message2 = String.format("  Highest Score: %d",wall.getScore());
                    gameTimer.stop();
                    wall.ballReset();
                    wall.wallReset();
                    wall.nextLevel();
                }
                else{
                    message = "ALL WALLS DESTROYED";
                    message2 = String.format("  Highest Score: %d",wall.getScore());
                    Music.GameEnd();
                    gameTimer.stop();
                }
            }

            repaint();
        });
    }

    /**
     * This method paint the display
     *
     * @param g
     */
    public void paint(Graphics g){

        Graphics2D g2d = (Graphics2D) g;

        clear(g2d);

        g2d.setColor(Color.WHITE);
        g.setFont(Controller.smallFont);
        g2d.drawString(message,180,225);

        g2d.setColor(Color.WHITE);
        g.setFont(Controller.smallFont);
        g2d.drawString(message2,180,265);

        g2d.setColor(Color.WHITE);
        g.setFont(Controller.smallFont);
        g2d.drawString(status,20,493);

        drawBall(wall.ball,g2d);

        for(Brick b : wall.bricks)
            if(!b.isBroken())
                drawBrick(b,g2d);

        drawPlayer(wall.player,g2d);

        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * This method paint the background
     * @param g2d
     */
    private void clear(Graphics g2d){
        g2d.drawImage(InGameBackground, 0, 0, DEF_WIDTH, DEF_HEIGHT, null);
    }

    /**
     * This method display the brick
     * @param brick
     * @param g2d
     */
    private void drawBrick(Brick brick,Graphics2D g2d){
        Color tmp = g2d.getColor();

        g2d.setColor(brick.getInnerColor());
        g2d.fill(brick.getBrick());

        g2d.setColor(brick.getBorderColor());
        g2d.draw(brick.getBrick());


        g2d.setColor(tmp);
    }

    /**
     * This method display the ball
     * @param ball
     * @param g2d
     */
    private void drawBall(Ball ball,Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = ball.getBallFace();

        g2d.setColor(ball.getInnerColor());
        g2d.fill(s);

        g2d.setColor(ball.getBorderColor());
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    /**
     * This method display the player
     * @param p
     * @param g2d
     */
    private void drawPlayer(Player p,Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = p.getPlayerFace();
        g2d.setColor(Player.INNER_COLOR);
        g2d.fill(s);

        g2d.setColor(Player.BORDER_COLOR);
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    /**
     * This method dispay the pause menu
     * @param g2d
     */
    public void drawMenu(Graphics2D g2d){
        obscureGameBoard(g2d);
        drawPauseMenu(g2d);
        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * This method display the pause menu background
     * @param g2d
     */
    private void obscureGameBoard(Graphics2D g2d){

        Composite tmp = g2d.getComposite();
        Color tmpColor = g2d.getColor();

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.55f);
        g2d.setComposite(ac);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,DEF_WIDTH,DEF_HEIGHT);

        g2d.setComposite(tmp);
        g2d.setColor(tmpColor);
    }

    /**
     * This method display the pause menu text
     * @param g2d
     */
    private void drawPauseMenu(Graphics2D g2d){
        Font tmpFont = g2d.getFont();
        Color tmpColor = g2d.getColor();


        g2d.setFont(menuFont);
        g2d.setColor(MENU_COLOR);

        if(strLen == 0){
            FontRenderContext frc = g2d.getFontRenderContext();
            strLen = menuFont.getStringBounds(PAUSE,frc).getBounds().width;
        }

        int x = (frame.getWidth() - strLen) / 2;
        int y = frame.getHeight() / 10;

        g2d.drawString(PAUSE,x,y);

        x = frame.getWidth() / 8;
        y = frame.getHeight() / 4;


        if(continueButtonRect == null){
            FontRenderContext frc = g2d.getFontRenderContext();
            continueButtonRect = menuFont.getStringBounds(CONTINUE,frc).getBounds();
            continueButtonRect.setLocation(x,y-continueButtonRect.height);
        }

        g2d.drawString(CONTINUE,x,y);

        if(continueClicked) {
            Color tmp = g2d.getColor();
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(CONTINUE,x,y);
            g2d.setColor(tmp);
        }
        else {
            g2d.drawString(CONTINUE,x,y);
        }

        y *= 2;

        if(restartButtonRect == null){
            restartButtonRect = (Rectangle) continueButtonRect.clone();
            restartButtonRect.setLocation(x,y-restartButtonRect.height);
        }

        g2d.drawString(RESTART,x,y);

        if(restartClicked) {
            Color tmp = g2d.getColor();
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(RESTART,x,y);
            g2d.setColor(tmp);
        }
        else {
            g2d.drawString(RESTART,x,y);
        }

        y *= 3.0/2;

        if(exitButtonRect == null){
            exitButtonRect = (Rectangle) continueButtonRect.clone();
            exitButtonRect.setLocation(x,y-exitButtonRect.height);
        }

        g2d.drawString(EXIT,x,y);

        if(exitClicked) {
            Color tmp = g2d.getColor();
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(EXIT,x,y);
            g2d.setColor(tmp);
        }
        else {
            g2d.drawString(EXIT,x,y);
        }

        g2d.setFont(tmpFont);
        g2d.setColor(tmpColor);
    }

    /**
     * This method control the key event
     */
    public void keyEvent() {
        if (KeyHandler.keyreleased) {
            wall.player.stop();
        }
        if (KeyHandler.LEFT){
            wall.player.moveLeft();
        }
        if (KeyHandler.RIGHT) {
            wall.player.movRight();
        }
        if (KeyHandler.SPACE) {
            if(!showPauseMenu) {
                if (gameTimer.isRunning()) {
                    message = "        Paused";
                    gameTimer.stop();
                }
                else {
                    gameTimer.start();
                }
                KeyHandler.SPACE = false;
            }
        }
        if (KeyHandler.ESCAPE) {
            showPauseMenu = !showPauseMenu;
            repaint();
            if (showPauseMenu){Music.PauseMenu();}
            else {Music.PauseEnd();}
            message = "        Paused";
            gameTimer.stop();
            KeyHandler.ESCAPE = false;
        }
    }

    /**
     * This method control the mouse event
     */
    public void mouseEvent() {
        if(showPauseMenu) {
            if(continueButtonRect.contains(Controller.mousePoint) && MouseHandler.MOUSECLICKED){
                showPauseMenu = false;
                continueClicked = false;
                MouseHandler.MOUSECLICKED = false;
                Music.PauseEnd();
                repaint();
            }
            else if(restartButtonRect.contains(Controller.mousePoint) && MouseHandler.MOUSECLICKED){
                message = "Game Restarted";
                wall.ballReset();
                wall.wallReset();
                showPauseMenu = false;
                restartClicked = false;
                MouseHandler.MOUSECLICKED = false;
                Music.PauseEnd();
                repaint();
            }
            else if(exitButtonRect.contains(Controller.mousePoint) && MouseHandler.MOUSECLICKED) {
                System.exit(0);
            }
            else if(continueButtonRect.contains(Controller.mousePoint) && MouseHandler.hasPressed){
                continueClicked = true;
                repaint();
            }
            else if(continueButtonRect.contains(Controller.mousePoint) && MouseHandler.hasReleased){
                continueClicked = false;
                repaint();
            }
            else if(restartButtonRect.contains(Controller.mousePoint) && MouseHandler.hasPressed){
                restartClicked = true;
                repaint();
            }
            else if(restartButtonRect.contains(Controller.mousePoint) && MouseHandler.hasReleased){
                restartClicked = false;
                repaint();
            }
            else if(exitButtonRect.contains(Controller.mousePoint) && MouseHandler.hasPressed){
                exitClicked = true;
                repaint();
            }
            else if(exitButtonRect.contains(Controller.mousePoint) && MouseHandler.hasReleased){
                exitClicked = false;
                repaint();
            }
        }

        if(exitButtonRect != null && showPauseMenu) {
            if (exitButtonRect.contains(Controller.mousePoint) || continueButtonRect.contains(Controller.mousePoint) || restartButtonRect.contains(Controller.mousePoint))
                Frame.handcursor();
            else
                Frame.defaultcursor();
        }
        else {
            Frame.defaultcursor();
        }
    }
}
