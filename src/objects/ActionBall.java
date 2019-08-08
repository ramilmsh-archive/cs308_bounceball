package objects;

public class ActionBall extends Ball {
    public ActionBall(int x, int y) {
        this(x, y, RADIUS);
    }
    public ActionBall(int x, int y, double r) {
        super(x, y, r);
    }
}
