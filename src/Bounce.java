import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import objects.*;
import utils.Triplet;
import utils.Tuple;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Consumer;


/**
 * Main game class. Starts the game and manages resources and events
 */
public class Bounce extends Application {
    private static double FPS = 60.0;
    private static String TITLE = "Bounce";

    final int WIDTH = 300;
    final int HEIGHT = 300;
    private static int MAX_LIVES = 3;
    private Paint BACKGROUND = Color.WHITE;
    private int currentLevel = 0;

    private ArrayList<Tuple<Consumer<Triplet<Block, Paddle, Ball>>, Boolean>> paddleActionList;

    private Stage stage = null;
    private Group root = null;
    private ActionPaddle paddle = null;

    // MESSAGE BLOCK
    private static final String splashMessage = "Bounce Game:\n" +
            "Use mouse to control paddle\n" +
            "Task is to eliminate all blocks\n" +
            "If you miss all the balls, you lose\n" +
            "If you eliminate all blocks, you succeed\n" +
            "If you succeed in the last level, you win\n" +
            "Please, refer to README.md for more info\n\n" +
            "PRESS S to start, P - to pause, R - to reset,\n" +
            "L - to add lives, M - for madness.\n" +
            "Use numbers to select level";
    private Text splash = new Text(splashMessage);
    private Text status = new Text(0., HEIGHT - 50., "");
    private Text levelComplete = new Text(0., HEIGHT - 50., "Level complete");
    private Text gameOver = new Text(0., HEIGHT - 50., "Game Over");
    private Text success = new Text(0., HEIGHT - 50., "Victory!");
    private Text currentMessage = splash;
    private Rectangle background = new Rectangle(WIDTH, HEIGHT, Paint.valueOf("white"));
    // END MESSAGE BLOCK

    private ArrayList<Ball> balls = null;
    private ArrayList<Block> blocks = null;

    private Timeline animation;
    private Actions actions;
    private int lives = MAX_LIVES;
    private int score;

    public static void main(String[] args) {
        launch(args);
    }

    public void add(Ball ball) {
        ball.addTo(root, balls);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        actions = new Actions(this);
        definePaddleActions();
        stage = primaryStage;
        setAnimation();
        setLevel(0, 0);
    }

    private void definePaddleActions() {
        paddleActionList = new ArrayList<>(3);
        paddleActionList.add(new Tuple<>(actions::scalePaddle, false));
        paddleActionList.add(new Tuple<>(actions::reversePaddle, true));
        paddleActionList.add(new Tuple<>((t)->actions.scaleBallsSpeed(t, .5, 1), false));
    }

    private void setLevel(int level, int offsetTop) {
        animation.pause();
        Scene scene = initilizeScene(level, offsetTop);
        setTextVisibility(currentMessage, true);
        if (scene == null)
            return;
        // TODO use Events to handle actions
        paddle.setAction(paddleActionList.get(level));

        root.getChildren().add(status);
        root.getChildren().add(background);
        root.getChildren().add(success);
        root.getChildren().add(gameOver);
        root.getChildren().add(levelComplete);
        // May have been added earlier, but it does not matter
        try {
            root.getChildren().add(currentMessage);
        } catch (IllegalArgumentException ignored) {
        }
        splash.setFill(Paint.valueOf("red"));

        scene.setOnMouseMoved(this::handleMouseMoved);
        scene.setOnKeyPressed(this::handleKeyPressed);
    }

    private Scene initilizeScene(int level, int offsetTop) {
        setTextVisibility(success, false);
        setTextVisibility(gameOver, false);
        setTextVisibility(levelComplete, false);

        balls = new ArrayList<>();
        currentLevel = level;
        root = getLevelFromFile(level, offsetTop);
        actions.setElementLists(blocks, balls);
        if (root == null) {
            currentMessage = success;
            return null;
        }
        return setStage(level, root);
    }

    private Group getLevelFromFile(int level, int offsetTop) {
        Scanner io;
        try {
            File file = new File("data/" + level);
            io = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.print(e.toString());
            animation.pause();
            return null;
        }

        Group group = new Group();
        ArrayList<Block> list = new ArrayList<>();
        readBlocksFromFile(io, group, list, offsetTop);
        group.getChildren().add(new Text("bla"));

        blocks = list;
        balls = new ArrayList<>();
        return group;
    }

    private void readBlocksFromFile(Scanner io, Group group, ArrayList<Block> list, int offsetTop) {
        int i = 0, j = 0;
        String line;

        while (io.hasNext()) {
            line = io.nextLine();
            for (Character c : line.toCharArray())
                addBlock(c, i++ * Block.WIDTH, (j + offsetTop) * Block.HEIGHT, group, list);
            i = 0;
            j++;
        }
    }

    private void addBlock(char c, int x, int y, Group group, ArrayList<Block> list) {
        // Please refer to README.md for specifications
        switch (c) {
            case '1':
                list.add(new Block(x, y).addTo(group));
                break;
            case '2':
                list.add(new EnforcedBlock(x, y, 2).addTo(group));
                break;
            case '3':
                list.add(new EnforcedBlock(x, y, 3).addTo(group));
                break;
            case '4':
                list.add(new ActionBlock(x, y, actions::spawnBalls,
                        "green").addTo(group));
                break;
            case '5':
                list.add(new ActionBlock(x, y, actions::makeInvincible,
                        "yellow").addTo(group));
                break;
            case '6':
                list.add(new ActionBlock(x, y, actions::scaleBallsSpeed,
                        "purple").addTo(group));
                break;

        }
    }

    private Scene setStage(int level, Group root) {
        paddle = new ActionPaddle(0, .95 * HEIGHT, actions::defaultPaddleAction).addTo(root);
        add(new Ball(WIDTH, HEIGHT));
        Scene scene = new Scene(root, WIDTH, HEIGHT, BACKGROUND);
        stage.setScene(scene);
        stage.setTitle(TITLE + ": Level " + level);
        stage.show();
        return scene;
    }

    private void setAnimation() {
        KeyFrame frame = new KeyFrame(Duration.seconds(1 / FPS),
                e -> step(1 / FPS));
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
    }

    private void miss() {
        lives--;
        if (lives < 0) {
            currentMessage = gameOver;
            setLevel(0, 0);
            lives = MAX_LIVES;
        } else
            setLevel(currentLevel, MAX_LIVES - lives);
    }

    private void step(double dt) {
        Iterator<Ball> ballIterator = balls.iterator();
        if (!ballIterator.hasNext())
            miss();
        while (ballIterator.hasNext()) {
            Iterator<Block> blockIterator = blocks.iterator();
            if (!blockIterator.hasNext()) {
                currentMessage = levelComplete;
                setLevel(currentLevel + 1, 0);
            }
            // Some balls are added asynchronously, through callbacks
            // therefore, it is necessary to reset iterator
            Ball ball;
            try {
                ball = ballIterator.next();
            } catch (ConcurrentModificationException e) {
                return;
            }
            // Ball is off the screen
            if (ball.getCenterY() > HEIGHT) {
                ballIterator.remove();
                root.getChildren().remove(ball);
                continue;
            }
            calculateCollisions(blockIterator, ball, dt);
        }
        status.setText(getStatus());
    }

    private String getStatus() {
        return "Lives: " + lives + "\n" +
                "Score: " + score + "\n" +
                "Level: " + currentLevel;
    }

    private void calculateCollisions(Iterator<Block> blockIterator, Ball ball, double dt) {
        // with walls
        ball.collide(WIDTH);
        ball.collide(paddle);
        while (blockIterator.hasNext()) {
            Block block = blockIterator.next();
            if (ball.collide(block)) {
                block.hit(new Triplet<>(block, paddle, ball), blockIterator);
                score++;
            }
        }
        ball.move(dt);
    }

    private void setTextVisibility(Text text, boolean visible) {
        background.setVisible(visible);
        text.setVisible(visible);
    }

    private void handleMouseMoved(MouseEvent e) {
        double x = e.getX();
        x = x < 0 ? x : x + paddle.getWidth() > WIDTH ? WIDTH - paddle.getWidth() : x;
        paddle.setX(x);
    }

    private void handleKeyPressed(KeyEvent e) {
        KeyCode c = e.getCode();
        if (c.isDigitKey()) {
            setLevel(Integer.parseInt(c.getName()), 0);
            return;
        }
        switch (c.getName()) {
            case "R":
                setLevel(0, 0);
                break;
            case "M":
                actions.spawnBalls(null, 1000);
                break;
            case "L":
                lives++;
                break;
            case "S":
                currentMessage = splash;
                setTextVisibility(currentMessage, false);
                animation.play();
                break;
            case "P":
                setTextVisibility(currentMessage, true);
                animation.pause();
                break;
        }
    }
}