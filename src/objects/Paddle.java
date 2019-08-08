package objects;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;


/**
 * Base Paddle class
 */
public class Paddle extends Rectangle {
    static final double HEIGHT = 5;
    static final double WIDTH = 40;

    public Paddle(double x, double y) {
        this(x, y, WIDTH);
    }

    public Paddle(double x, double y, double w) {
        super(x, y, w, HEIGHT);
    }

    /**
     * Attach paddle to a Group
     *
     * @param root: group
     * @return this
     */
    public Paddle addTo(Group root) {
        root.getChildren().add(this);
        return this;
    }

    /**
     * Calculates new Ball velocity
     *
     * @param ball: ball
     */
    public void hit(Ball ball) {

    }

    public void move(double x) {
        setX(x - getWidth() / 2);
    }
}
