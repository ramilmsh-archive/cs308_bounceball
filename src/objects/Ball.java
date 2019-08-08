package objects;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import math.num.Vector;

import java.util.ArrayList;
import java.util.Random;


/**
 * Basic ball class, implements collisions with objects
 */
public class Ball extends Circle {
    private Vector vel;
    private Vector pos;
    public double magnitude;

    static final double RADIUS = 5;
    private static final double MAX_SPEED_MULTIPLIER = 1;
    private Random random = new Random();

    private boolean invincible = false;

    public Ball(int w, int h) {
        this(w, h, RADIUS);
    }

    /**
     * Creates add Ball within x by y block with radius r
     *
     * @param w: width
     * @param h: height
     * @param r: radius
     */
    public Ball(int w, int h, double r) {
        super(r);
        double MAX_SPEED = new Vector(w, h).norm() * MAX_SPEED_MULTIPLIER;
        // Restrict spawn to lower 20% of the window width
        this.setCenter(new Vector(random.nextDouble() * w, (.8 + .2 * random.nextDouble()) * h));
        // velocity: {[-1, 1], [-1, -.1]} of [.3, 1] of max velocity
        magnitude = (random.nextDouble() * .7 + .3) * MAX_SPEED;
        vel = new Vector((2 * random.nextDouble() - 1), -.9 * random.nextDouble() - .1)
                .normalize().mult(magnitude);
    }

    public Ball addTo(Group root, ArrayList<Ball> list) {
        list.add(this);
        root.getChildren().add(this);
        return this;
    }

    private void setCenter(Vector pos) {
        this.pos = pos;
        setCenterX(pos.x);
        setCenterY(pos.y);
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }

    public void scaleSpeed(double factor, long delay) {
        magnitude *= factor;
        new Thread(() -> {
            try {
                Thread.sleep(delay * 1000);
            } catch (InterruptedException ignored) {
            }
            magnitude /= factor;
        });
    }

    public void move(double dt) {
        setCenter(pos.add(vel.mult(dt)));
    }

    /**
     * Processes collisions with walls
     *
     * @param width: screen width
     */
    public void collide(int width) {
        // Math.abs, prevents the balls from getting stuck twitching near the walls
        if (pos.x - getRadius() <= 0)
            vel = new Vector(Math.abs(vel.x), vel.y);
        else if (pos.x + getRadius() >= width)
            vel = new Vector(-Math.abs(vel.x), vel.y);
        else if (pos.y - getRadius() <= 0)
            vel = new Vector(vel.x, Math.abs(vel.y));
    }

    public void collide(ActionPaddle paddle) {
        if (getCenterY() + getRadius() >= paddle.getY() && (getCenterX() >= paddle.getX()
                && getCenterX() <= paddle.getX() + paddle.getWidth()))
            // angle depends on which portion the ball hit
            // angle [.1 * PI, .9 * PI]
            paddle.hit(this);
    }

    // TODO solve multiple collision problem
    public boolean collide(Block block) {
        // To optimize performance for blocks that are far away
        if (block.center.sub(pos).norm() > block.radius + getRadius())
            return false;

        // Displacement from current velocity due to collision with current block
        Vector _vel = this.reflect(block);
        if (_vel == null)
            return false;
        setVel(invincible ? vel : _vel);
        return true;
    }

    public void setVel(Vector vel) {
        this.vel = vel;
    }

    /**
     * Checks if collision has happened and reflects the ball
     * 1|2|3
     * -----
     * 8| |4
     * -----
     * 7|6|5
     *
     * @param block: Bounds of the block
     * @return new velocity vector
     */
    private Vector reflect(Block block) {
        Bounds blockBounds = block.getBoundsInLocal();
        // Within 2 or 6
        if (getCenterX() < blockBounds.getMaxX() && blockBounds.getMinX() < getCenterX())
            // Is it really touching?
            if (Math.abs(getCenterY() - block.center.y) > block.getHeight() / 2 + getRadius())
                return null;
            else
                return new Vector(vel.x, -vel.y);

        // Within 4 or 8
        if (getCenterY() < blockBounds.getMaxY() && blockBounds.getMinY() < getCenterY())
            // Is it really touching?
            if (Math.abs(getCenterX() - block.center.x) > block.getWidth() / 2 + getRadius())
                return null;
            else
                return new Vector(-vel.x, vel.y);
        // Within 1, 3, 5 or 7 and touching
        return reflectFromCorner(block, blockBounds);
    }

    /**
     * Approximates add corner to add ball with 0 radius and reflects add ball from it
     * Deflection formula by meriton at
     * https://gamedev.stackexchange.com/questions/10911/a-ball-hits-the-corner-where-will-it-deflect
     *
     * @param block:       block
     * @param blockBounds: bounds
     * @return new direction vector
     */
    private Vector reflectFromCorner(Block block, Bounds blockBounds) {
        // Choosing corner
        Vector dir = pos.sub(block.center);
        Vector corner;
        if (dir.x > 0) {
            if (dir.y > 0)
                corner = new Vector(blockBounds.getMaxX(), blockBounds.getMinY());
            else
                corner = new Vector(blockBounds.getMaxX(), blockBounds.getMaxY());
        } else {
            if (dir.y > 0)
                corner = new Vector(blockBounds.getMinX(), blockBounds.getMinY());
            else
                corner = new Vector(blockBounds.getMinX(), blockBounds.getMaxY());
        }

        Vector normal = pos.sub(corner);
        double c = -2 * vel.dot(normal) / normal.norm();
        return vel.add(normal.mult(c)).normalize().mult(magnitude);
    }
}