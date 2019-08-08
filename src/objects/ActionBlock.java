package objects;

import javafx.scene.paint.Paint;
import utils.Triplet;

import java.util.Iterator;
import java.util.function.Consumer;


/**
 * Class describes behavior of blocks that activate power-ups,
 * more generally, trigger instruction set, once hit
 */
public class ActionBlock extends Block {
    private Consumer<Triplet<Block, Paddle, Ball>> callback;

    public ActionBlock(int x, int y, Consumer<Triplet<Block, Paddle, Ball>> callback, String color) {
        super(x, y, WIDTH, HEIGHT);
        setAction(callback);
        this.setFill(Paint.valueOf(color));
    }

    @Override
    public void hit(Triplet<Block, Paddle, Ball> t, Iterator<Block> iterator) {
        remove(iterator);
        callback.accept(t);
    }

    /**
     * Defines action, when a ball hits it
     *
     * @param action: action
     */
    public void setAction(Consumer<Triplet<Block, Paddle, Ball>> action) {
        this.callback = action;
    }
}
