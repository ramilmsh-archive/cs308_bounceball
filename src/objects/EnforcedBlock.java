package objects;

import javafx.scene.paint.Paint;
import utils.Triplet;

import java.util.Iterator;

public class EnforcedBlock extends Block {
    // Strength
    private int s = 1;
    // Collision count
    private int c = 0;

    private static final String[] colors = new String[]{"white", "black", "blue", "red"};


    public EnforcedBlock(int x, int y, int s) {
        super(x, y);
        setFill(Paint.valueOf(colors[s - c]));
        this.s = s;
    }

    @Override
    public void hit(Triplet<Block, Paddle, Ball> t, Iterator<Block> iterator) {
        if (++c >= s)
            remove(iterator);
        setFill(Paint.valueOf(colors[s - c]));
    }
}
