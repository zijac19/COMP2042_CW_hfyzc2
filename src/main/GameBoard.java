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
import java.awt.font.FontRenderContext;

import handlers.KeyHandler;
import handlers.MouseHandler;
import handlers.WindowHandler;
import models.Music;
import models.Wall;
import models.Player;
import models.Brick;
import models.Ball;
import handlers.ImageLoader;

import static main.Frame.frame;

public class GameBoard extends JPanel {
    private static boolean gaming = false;

    private static final String CONTINUE = "Continue";
    private static final String RESTART = "Restart";
    private static final String EXIT = "Exit";
    private static final String PAUSE = "Pause Menu";
    private static final int TEXT_SIZE = 30;
    private static final Color MENU_COLOR = new Color(0,255,0);


    private static final int DEF_WIDTH = 500;
    private static final int DEF_HEIGHT = 513;

    private final Image InGameBackground;

    private Timer gameTimer;

    private final Wall wall;

    private String message;
    private String status;

    public static boolean showPauseMenu;

    private final Font menuFont;

    private Rectangle continueButtonRect;
    private Rectangle exitButtonRect;
    private Rectangle restartButtonRect;
    private int strLen;

    private DebugConsole debugConsole;


    public GameBoard(){
        super();

        strLen = 0;
        showPauseMenu = false;

        menuFont = new Font("Monospaced",Font.PLAIN,TEXT_SIZE);

        InGameBackground = new ImageLoader(ImageLoader.ingamebackground).getImage();
        message = "";
        status = "";
        wall = new Wall(new Rectangle(0,0,DEF_WIDTH,DEF_HEIGHT),30,3,3,new Point(250,430));

        //debugConsole = new DebugConsole(owner,wall,this);

        //initialize the first level
        wall.nextLevel();
        gametime();
    }

    private void gametime() {
        gaming = true;
        gameTimer = new Timer(10,e ->{
            wall.move();
            wall.findImpacts();
            message = "";
            status = String.format("Bricks: %d Balls %d",wall.getBrickCount(),wall.getBallCount());
            if(wall.isBallLost()){
                if(wall.ballEnd()){
                    wall.wallReset();
                    message = "Game over";
                }
                wall.ballReset();
                gameTimer.stop();
            }
            else if(wall.isDone()){
                if(wall.hasLevel()){
                    message = "Go to Next Level";
                    gameTimer.stop();
                    wall.ballReset();
                    wall.wallReset();
                    wall.nextLevel();
                }
                else{
                    message = "ALL WALLS DESTROYED";
                    Music.GameEnd();
                    gameTimer.stop();
                }
            }

            repaint();
        });
    }

    public void paint(Graphics g){

        Graphics2D g2d = (Graphics2D) g;

        clear(g2d);

        g2d.setColor(Color.WHITE);
        g.setFont(Controller.smallFont);
        g2d.drawString(message,180,225);

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

    private void clear(Graphics g2d){
        g2d.drawImage(InGameBackground, 0, 0, DEF_WIDTH, DEF_HEIGHT, null);
    }

    private void drawBrick(Brick brick,Graphics2D g2d){
        Color tmp = g2d.getColor();

        g2d.setColor(brick.getInnerColor());
        g2d.fill(brick.getBrick());

        g2d.setColor(brick.getBorderColor());
        g2d.draw(brick.getBrick());


        g2d.setColor(tmp);
    }

    private void drawBall(Ball ball,Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = ball.getBallFace();

        g2d.setColor(ball.getInnerColor());
        g2d.fill(s);

        g2d.setColor(ball.getBorderColor());
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    private void drawPlayer(Player p,Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = p.getPlayerFace();
        g2d.setColor(Player.INNER_COLOR);
        g2d.fill(s);

        g2d.setColor(Player.BORDER_COLOR);
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    public void drawMenu(Graphics2D g2d){
        obscureGameBoard(g2d);
        drawPauseMenu(g2d);
        Toolkit.getDefaultToolkit().sync();
    }

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

        y *= 2;

        if(restartButtonRect == null){
            restartButtonRect = (Rectangle) continueButtonRect.clone();
            restartButtonRect.setLocation(x,y-restartButtonRect.height);
        }

        g2d.drawString(RESTART,x,y);

        y *= 3.0/2;

        if(exitButtonRect == null){
            exitButtonRect = (Rectangle) continueButtonRect.clone();
            exitButtonRect.setLocation(x,y-exitButtonRect.height);
        }

        g2d.drawString(EXIT,x,y);



        g2d.setFont(tmpFont);
        g2d.setColor(tmpColor);
    }

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
            gameTimer.stop();
            KeyHandler.ESCAPE = false;
        }
        if (KeyHandler.F1) {
            debugConsole.setVisible(true);
        }
    }

    public void mouseEvent() {
        if (showPauseMenu) {
            if(continueButtonRect.contains(Controller.mousePoint) && MouseHandler.MOUSECLICKED){
                showPauseMenu = false;
                MouseHandler.MOUSECLICKED = false;
                repaint();
            }
            else if(restartButtonRect.contains(Controller.mousePoint) && MouseHandler.MOUSECLICKED){
                message = "Game Restarted";
                wall.ballReset();
                wall.wallReset();
                showPauseMenu = false;
                MouseHandler.MOUSECLICKED = false;
                repaint();
            }
            else if(exitButtonRect.contains(Controller.mousePoint) && MouseHandler.MOUSECLICKED) {
                System.exit(0);
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

    public void onLostFocus(){
        if (WindowHandler.lostfocus && gaming)
            gameTimer.stop();
            message = "Focus Lost";
            repaint();
    }

}
