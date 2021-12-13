package models;

import java.awt.*;
import java.awt.Point;
import java.awt.geom.Point2D;


/**
 * This class contains the details of the cement brick
 *
 * Created by filippo on 04/09/16.
 * Refactor by
 * @author Chang Zi Jac
 */
public class ClayBrick extends Brick {

    // initialize the variables
    private static final String NAME = "Clay Brick";
    private static final Color DEF_INNER = new Color(178, 34, 34).darker();
    private static final Color DEF_BORDER = Color.GRAY;
    private static final int CLAY_STRENGTH = 1;


    /**
     * This method return the details of the clay brick
     * @param point
     * @param size
     */
    public ClayBrick(Point point, Dimension size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,CLAY_STRENGTH);
    }

    /**
     * This method make the brick shape
     * @param pos
     * @param size
     * @return Rectangle(pos,size)
     */
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    /**
     * This method get the brick shape
     * @return super.brickFace
     */
    @Override
    public Shape getBrick() {
        return super.brickFace;
    }


}
