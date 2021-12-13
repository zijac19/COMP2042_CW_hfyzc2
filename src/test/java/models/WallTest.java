package models;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class WallTest {

    Wall wall = new Wall(new Rectangle(0,0,600,450), 30, 3, 6/2, new Point(300,430));

    @Test
    void isDone() {
        assertEquals(true, wall.isDone());
    }

    @Test
    void BallEnd() {
        assertEquals(false, wall.ballEnd());
    }

    @Test
    void isBallLost() {
        assertEquals(false, wall.isBallLost());
    }
}