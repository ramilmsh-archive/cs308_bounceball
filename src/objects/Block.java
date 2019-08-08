package objects;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import math.num.Vector;
import utils.Triplet;

import java.util.Iterator;


/**
 * Basic block, destroyed be one hit
 */
public class Block extends Rectangle {
    public static int WIDTH = 20;
    public static int HEIGHT = 20;

    private Group parent = null;
    Vector center;
    double radius;


    public Block(int x, int y) {
        this(x, y, Block.WIDTH, Block.HEIGHT);
    }

    Block(int x, int y, int w, int h) {
        super(x, y, w, h);
        this.center = new Vector(x + w / 2, y + h / 2);
        this.radius = Math.sqrt(Math.pow(w / 2, 2) + Math.pow(h / 2, 2));
    }

    public Block addTo(Group parent) {
        this.parent = parent;
        this.parent.getChildren().add(this);
        return this;
    }

    /**
     * Triggers actions, when a block is hit
     */
    public void hit(Triplet<Block, Paddle, Ball> t, Iterator<Block> iterator) {
        this.remove(iterator);
    }

    void remove(Iterator<Block> iterator) {
        iterator.remove();
        parent.getChildren().remove(this);
    }
}
