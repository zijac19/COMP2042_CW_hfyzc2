package models;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;

/**
 * This class defines the ball's details
 *
 * Created by filippo on 04/09/16.
 *
 *Refactor by
 * @author Chang Zi Jac
 */
abstract public class Ball {

    //initialize the variables
    private Shape ballFace;

    private Point2D center;

    Point2D up;
    Point2D down;
    Point2D left;
    Point2D right;

    private Color border;
    private Color inner;

    private int speedX;
    private int speedY;

    /**
     * This method initialize the ball's status
     *
     * @param center
     * @param radiusA
     * @param radiusB
     * @param inner
     * @param border
     */
    public Ball(Point2D center,int radiusA,int radiusB,Color inner,Color border){
        this.center = center;

        up = new Point2D.Double();
        down = new Point2D.Double();
        left = new Point2D.Double();
        right = new Point2D.Double();

        up.setLocation(center.getX(),center.getY()-(radiusB / 2));
        down.setLocation(center.getX(),center.getY()+(radiusB / 2));

        left.setLocation(center.getX()-(radiusA /2),center.getY());
        right.setLocation(center.getX()+(radiusA /2),center.getY());


        ballFace = makeBall(center,radiusA,radiusB);
        this.border = border;
        this.inner  = inner;
        speedX = 0;
        speedY = 0;
    }

    /**
     *This defines the shape of the ball
     *
     */
    protected abstract Shape makeBall(Point2D center,int radiusA,int radiusB);

    /**
     * This method defines the ball's position when the ball moved
     */
    public void move(){
        RectangularShape tmp = (RectangularShape) ballFace;
        center.setLocation((center.getX() + speedX),(center.getY() + speedY));
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h);
        setPoints(w,h);


        ballFace = tmp;
    }

    /**
     * This method sets the ball speed
     *
     * @param x
     * @param y
     */
    public void setSpeed(int x,int y){
        speedX = x;
        speedY = y;
    }

    /**
     * This method sets the ball's x speed
     *
     * @param s
     */
    public void setXSpeed(int s){
        speedX = s;
    }

    /**
     * This method sets the ball's y speed
     *
     * @param s
     */
    public void setYSpeed(int s){
        speedY = s;
    }

    /**
     * This method sets the ball's reverse x speed
     *
     */
    public void reverseX(){
        speedX *= -1;
    }

    /**
     * This method sets the ball's reverse y speed
     *
     */
    public void reverseY(){
        speedY *= -1;
    }

    /**
     * This method gets the ball's border colour
     *
     * @return border
     */
    public Color getBorderColor(){
        return border;
    }


    /**
     * This method gets the ball's inner colour
     *
     * @return inner
     */
    public Color getInnerColor(){
        return inner;
    }


    /**
     * This method gets the ball's position
     *
     * @return center
     */
    public Point2D getPosition(){
        return center;
    }


    /**
     * This method gets the ball's face
     *
     * @return ballFace
     */
    public Shape getBallFace(){
        return ballFace;
    }

    /**
     * This method calculates the ball's next position
     *
     * @param p
     */
    public void moveTo(Point p){
        center.setLocation(p);

        RectangularShape tmp = (RectangularShape) ballFace;
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h);
        ballFace = tmp;
    }

    /**
     * This method set the ball's location
     *
     * @param width
     * @param height
     */
    private void setPoints(double width,double height){
        up.setLocation(center.getX(),center.getY()-(height / 2));
        down.setLocation(center.getX(),center.getY()+(height / 2));

        left.setLocation(center.getX()-(width / 2),center.getY());
        right.setLocation(center.getX()+(width / 2),center.getY());
    }

    /**
     * This method gets the ball's x speed
     *
     * @return speedX
     */
    public int getSpeedX(){
        return speedX;
    }

    /**
     * This method gets the ball's y speed
     *
     * @return speedY
     */
    public int getSpeedY(){
        return speedY;
    }


}
