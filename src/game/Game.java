package game;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import Geomtry.Point;
import Geomtry.Rectangle;
import game.collision.Collidable;
import game.collision.Sprite;
import game.listeners.BallRemover;
import game.listeners.BlockRemover;
import game.listeners.ScoreTrackingListener;
import game.objects.Ball;
import game.objects.Block;
import game.objects.Velocity;
import game.objects.ScoreIndecator;
import game.objects.Paddle;
import game.sprites.SpriteCollection;
import java.util.Collections;
/**
 * The Game class sets up and runs a simple block-breaking game.
 */
public class Game {
    private SpriteCollection sprites = new SpriteCollection();
    private GameEnvironment environment = new GameEnvironment();
    private Counter blocksCounter = new Counter();
    private Counter ballsCounter = new Counter();
    private Counter score = new Counter();
    private BlockRemover blockRemover = new BlockRemover(this, blocksCounter);
    private BallRemover ballRemover = new BallRemover(this, ballsCounter);
    private ScoreTrackingListener scoreTrackingListener = new ScoreTrackingListener(score, 5);
    private ScoreIndecator s = new ScoreIndecator(score);

    /**
     * Adds a collidable object to the game environment.
     * @param c the collidable to add
     */
    public void addCollidable(Collidable c) {
        environment.addCollidable(c);
    }

    /**
     * Adds a sprite to the sprite collection.
     * @param s the sprite to add
     */
    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }

    /**
     * Initializes the game elements.
     */
    public void initialize() {
        // Create the balls
        for (int i = 0; i < 2; i++) {
            Random rand = new Random();
            Ball ball = new Ball(new Point(390, 550), 5, java.awt.Color.RED);
            ball.setVelocity(Velocity.fromAngleAndSpeed(rand.nextFloat(-60, 60),  2));
            ball.setGameEnvironment(this.environment);
            ball.addToGame(this);
            ballsCounter.increase(1);
        }

        int blockWidth = 50;
        int blockHeight = 20;
        int gridStartX = 20;
        int gridStartY = 60;
        int gridWidth = 760;
        int gridHeight = 240;

        List<Point> gridPositions = new ArrayList<>();
        for (int x = gridStartX; x <= gridStartX + gridWidth - blockWidth; x += blockWidth) {
            for (int y = gridStartY; y <= gridStartY + gridHeight - blockHeight; y += blockHeight) {
                gridPositions.add(new Point(x, y));
            }
        }

// Shuffle positions randomly
        Collections.shuffle(gridPositions);

        java.awt.Color[] colors = {
                java.awt.Color.RED, java.awt.Color.BLUE, java.awt.Color.GREEN,
                java.awt.Color.YELLOW, java.awt.Color.PINK, java.awt.Color.CYAN
        };

        int numBlocks = Math.min(100, gridPositions.size()); // max number of blocks
        Random rand = new Random();

        for (int i = 0; i < numBlocks; i++) {
            Point pos = gridPositions.get(i);
            Rectangle rect = new Rectangle(pos, blockWidth, blockHeight);
            java.awt.Color color = colors[rand.nextInt(colors.length)];

            Block block = new Block(rect, color);
            block.addToGame(this);
            block.addHitListener(blockRemover);
            block.addHitListener(scoreTrackingListener);
            blocksCounter.increase(1);
        }





        //death region block
        Block deathRegion = new Block(new Rectangle(new Point(0, 600), 800, 20), java.awt.Color.BLACK);
        deathRegion.addToGame(this);
        deathRegion.addHitListener(ballRemover);

        // Add borders (screen size 800x600)
        Block topBorder = new Block(new Rectangle(new Point(0, 0), 800, 20), java.awt.Color.GRAY);
        Block leftBorder = new Block(new Rectangle(new Point(0, 0), 20, 600), java.awt.Color.GRAY);
        Block rightBorder = new Block(new Rectangle(new Point(780, 0), 20, 600), java.awt.Color.GRAY);


        topBorder.addToGame(this);
        leftBorder.addToGame(this);
        rightBorder.addToGame(this);

        s.addToGame(this);
    }
    /**
     * Runs the game loop.
     */
    public void run() {
        GUI gui = new GUI("Game", 800, 600);
        Sleeper sleeper = new Sleeper();
        int fps = 144;
        int msPerFrame = 1000 / fps;

        Paddle paddle = new Paddle(gui.getKeyboardSensor(),
                new Rectangle(new Point(350, 560), 200, 5));
        paddle.addToGame(this);

        while (true) {
            long startTime = System.currentTimeMillis();

            DrawSurface d = gui.getDrawSurface();
            this.sprites.drawAllOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed();

            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = msPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
            //check if player won
            if (blocksCounter.getValue() == 0) {
                score.increase(100);
                System.out.println("You Win!\nYour score is: " + score.getValue());
                DrawSurface gameOver = gui.getDrawSurface();
                gameOver.setColor(java.awt.Color.RED);
                gameOver.drawText(150, 300, "You Win!", 80);
                String scoreString = "Your final score is: " + score.getValue();
                gameOver.drawText(200, 400, scoreString, 40);
                gui.show(gameOver);
                sleeper.sleepFor(10000);
                gui.close();
                return;
            }
            //check if player lost
            if (ballsCounter.getValue() == 0) {
                System.out.println("Game Over.\nYour score is: " + score.getValue());
                DrawSurface gameOver = gui.getDrawSurface();
                gameOver.setColor(java.awt.Color.RED);
                gameOver.drawText(150, 300, "Game Over", 80);
                String scoreString = "Your score is: " + score.getValue();
                gameOver.drawText(200, 450, scoreString, 40);
                gui.show(gameOver);
                sleeper.sleepFor(10000);
                gui.close();
                return;
            }
        }
    }

    /**
     * Gets the game environment.
     * @return the game environment
     */
    public GameEnvironment getEnvironment() {
        return environment;
    }

    /**
     * Removes a collidable from the game environment.
     * @param c the collidable to remove
     */
    public void removeCollidable(Collidable c) {
        environment.removeCollidable(c);
    }

    /**
     * Removes a sprite from the game.
     * @param s the sprite to remove
     */
    public void removeSprite(Sprite s) {
        sprites.removeSprite(s);
    }
}
