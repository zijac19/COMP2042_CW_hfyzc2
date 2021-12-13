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
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import models.Music;
import controllers.MouseHandler;
import main.Controller;
import main.Frame;

/**
 *This class is the home menu scene
 *
 * Refactor by
 * @author Chang Zi Jac
 */
public class HomeMenu extends JComponent{

    // initialize the variables
    private static final Dimension area = new Dimension(483, 473);
    private static final String GREETINGS = "Welcome to:";
    private static final String GAME_TITLE = "Brick Destroy";
    private static final String CREDITS = "Version 2.0";
    private static final String START_TEXT = "Start";
    private static final String SUPPORT_TEXT = "Support";
    private static final String EXIT_TEXT = "Exit";

    private static final Color BG_COLOR = Color.GREEN.darker();
    private static final Color BORDER_COLOR = new Color(200,8,21); //Venetian Red
    private static final Color DASH_BORDER_COLOR = new  Color(255, 216, 0);//school bus yellow
    private static final Color TEXT_COLOR = new Color(16, 52, 166);//egyptian blue
    private static final Color CLICKED_BUTTON_COLOR = BG_COLOR.brighter();
    private static final Color CLICKED_TEXT = Color.WHITE;
    private static final int BORDER_SIZE = 5;
    private static final float[] DASHES = {12,6};

    private final Rectangle menuFace;
    private final Rectangle startButton;
    private final Rectangle supportButton;
    private final Rectangle exitButton;


    private final BasicStroke borderStoke;
    private final BasicStroke borderStoke_noDashes;

    private final Font greetingsFont;
    private final Font gameTitleFont;
    private final Font creditsFont;
    private final Font buttonFont;

    private boolean startClicked;
    private boolean supportClicked;
    private boolean exitClicked;

    /**
     * This method initialize the home menu
     */
    public HomeMenu(){
        menuFace = new Rectangle(new Point(0,0),area);

        Dimension btnDim = new Dimension(area.width / 3, area.height / 12);
        startButton = new Rectangle(btnDim);
        supportButton = new Rectangle(btnDim);
        exitButton = new Rectangle(btnDim);

        borderStoke = new BasicStroke(BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,0,DASHES,0);
        borderStoke_noDashes = new BasicStroke(BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);

        greetingsFont = new Font("Noto Mono",Font.PLAIN,25);
        gameTitleFont = new Font("Noto Mono",Font.BOLD,40);
        creditsFont = new Font("Monospaced",Font.PLAIN,15);
        buttonFont = new Font("Monospaced",Font.PLAIN,startButton.height-2);

        Music.HomeStart();
    }

    /**
     * This method paint the display
     * @param g
     */
    public void paint(Graphics g){
        drawMenu((Graphics2D)g);
    }

    /**
     * This method display the home menu background
     * @param g2d
     */
    public void drawMenu(Graphics2D g2d){

        drawContainer(g2d);

        /*
        all the following method calls need a relative
        painting directly into the HomeMenu rectangle,
        so the translation is made here so the other methods do not do that.
         */
        Color prevColor = g2d.getColor();
        Font prevFont = g2d.getFont();

        double x = menuFace.getX();
        double y = menuFace.getY();

        g2d.translate(x,y);

        //methods calls
        drawText(g2d);
        drawButton(g2d);
        //end of methods calls

        g2d.translate(-x,-y);
        g2d.setFont(prevFont);
        g2d.setColor(prevColor);
    }

    /**
     * This method display the border of the home menu
     * @param g2d
     */
    private void drawContainer(Graphics2D g2d){
        Color prev = g2d.getColor();

        g2d.setColor(BG_COLOR);
        g2d.fill(menuFace);

        Stroke tmp = g2d.getStroke();

        g2d.setStroke(borderStoke_noDashes);
        g2d.setColor(DASH_BORDER_COLOR);
        g2d.draw(menuFace);

        g2d.setStroke(borderStoke);
        g2d.setColor(BORDER_COLOR);
        g2d.draw(menuFace);

        g2d.setStroke(tmp);

        g2d.setColor(prev);
    }

    /**
     * This method display the menu text
     * @param g2d
     */
    private void drawText(Graphics2D g2d){

        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D greetingsRect = greetingsFont.getStringBounds(GREETINGS,frc);
        Rectangle2D gameTitleRect = gameTitleFont.getStringBounds(GAME_TITLE,frc);
        Rectangle2D creditsRect = creditsFont.getStringBounds(CREDITS,frc);

        int sX,sY;

        sX = (int)(menuFace.getWidth() - greetingsRect.getWidth()) / 2;
        sY = (int)(menuFace.getHeight() / 4);

        g2d.setFont(greetingsFont);
        g2d.drawString(GREETINGS,sX,sY);

        sX = (int)(menuFace.getWidth() - gameTitleRect.getWidth()) / 2;
        sY += (int) gameTitleRect.getHeight() * 1.1;//add 10% of String height between the two strings

        g2d.setFont(gameTitleFont);
        g2d.drawString(GAME_TITLE,sX,sY);

        sX = (int)(menuFace.getWidth() - creditsRect.getWidth()) / 2;
        sY += (int) creditsRect.getHeight() * 1.1;

        g2d.setFont(creditsFont);
        g2d.drawString(CREDITS,sX,sY);


    }

    /**
     * This method display the button
     * @param g2d
     */
    private void drawButton(Graphics2D g2d){

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D txtRect = buttonFont.getStringBounds(START_TEXT,frc);
        Rectangle2D stxtRect = buttonFont.getStringBounds(SUPPORT_TEXT,frc);
        Rectangle2D eTxtRect = buttonFont.getStringBounds(EXIT_TEXT,frc);

        g2d.setFont(buttonFont);

        int x = (menuFace.width - startButton.width) / 2;
        int y =(int) ((menuFace.height - startButton.height) * 0.64);

        startButton.setLocation(x,y);

        x = (int)(startButton.getWidth() - txtRect.getWidth()) / 2;
        y = (int)(startButton.getHeight() - txtRect.getHeight()) / 2;

        x += startButton.x;
        y += startButton.y + (startButton.height * 0.9);



        if(startClicked){
            Color tmp = g2d.getColor();
            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(startButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(START_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(startButton);
            g2d.drawString(START_TEXT,x,y);
        }

        x = startButton.x;
        y = startButton.y;

        y *= 1.25;

        supportButton.setLocation(x,y);




        x = (int)(supportButton.getWidth() - stxtRect.getWidth()) / 2;
        y = (int)(supportButton.getHeight() - stxtRect.getHeight()) / 2;

        x += supportButton.x;
        y += supportButton.y + (supportButton.height * 0.9);

        if(supportClicked){
            Color tmp = g2d.getColor();

            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(supportButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(SUPPORT_TEXT,x,y);
            g2d.setColor(tmp);

        }
        else{
            g2d.draw(supportButton);
            g2d.drawString(SUPPORT_TEXT,x,y);
        }

        x = supportButton.x;
        y = supportButton.y;

        y *= 1.2;

        exitButton.setLocation(x,y);




        x = (int)(exitButton.getWidth() - eTxtRect.getWidth()) / 2;
        y = (int)(exitButton.getHeight() - eTxtRect.getHeight()) / 2;

        x += exitButton.x;
        y += exitButton.y + (startButton.height * 0.9);

        if(exitClicked){
            Color tmp = g2d.getColor();

            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(exitButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(EXIT_TEXT,x,y);
            g2d.setColor(tmp);

        }
        else{
            g2d.draw(exitButton);
            g2d.drawString(EXIT_TEXT,x,y);
        }

    }

    /**
     * This method control the mouse event
     */
    public void mouseEvent() {
            if(startButton.contains(Controller.mousePoint) && MouseHandler.MOUSECLICKED){
                MouseHandler.MOUSECLICKED = false;
                Music.HomeEnd();
                Controller.switchStates(Controller.STATE.GAME);
            }
            else if(supportButton.contains(Controller.mousePoint) && MouseHandler.MOUSECLICKED){
                MouseHandler.MOUSECLICKED = false;
                try {
                    Desktop desktop = java.awt.Desktop.getDesktop();
                    URI oURL = new URI("https://github.com/zijac19/COMP2042_CW_hfyzc2");
                    desktop.browse(oURL);
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
            else if(exitButton.contains(Controller.mousePoint) && MouseHandler.MOUSECLICKED){
                MouseHandler.MOUSECLICKED = false;
                System.out.println("Goodbye " + System.getProperty("user.name"));
                System.exit(0);
            }
            else if(startButton.contains(Controller.mousePoint) && MouseHandler.hasPressed) {
                startClicked = true;
                repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);
            }
            else if(supportButton.contains(Controller.mousePoint) && MouseHandler.hasPressed) {
                supportClicked = true;
                repaint(supportButton.x,supportButton.y,supportButton.width+1,supportButton.height+1);
            }
            else if(exitButton.contains(Controller.mousePoint) && MouseHandler.hasPressed){
                exitClicked = true;
                repaint(exitButton.x,exitButton.y,exitButton.width+1,exitButton.height+1);
            }
            else if(startClicked && MouseHandler.hasReleased) {
                startClicked = false;
                repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);
            }
            else if(supportClicked && MouseHandler.hasReleased) {
                supportClicked = false;
                repaint(supportButton.x,supportButton.y,supportButton.width+1,supportButton.height+1);
            }
            else if(exitClicked && MouseHandler.hasReleased){
                exitClicked = false;
                repaint(exitButton.x,exitButton.y,exitButton.width+1,exitButton.height+1);
            }
            if(startButton.contains(Controller.mousePoint) || supportButton.contains(Controller.mousePoint) || exitButton.contains(Controller.mousePoint)) {
                Frame.handcursor();
            }
            else {
                Frame.defaultcursor();
            }
    }

}
