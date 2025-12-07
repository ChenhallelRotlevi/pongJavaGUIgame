package game.objects;

import game.collision.Collidable;
import game.collision.Sprite;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;

import Geomtry.Point;
import Geomtry.Rectangle;
import game.GameEnvironment;
import game.Game;

/**
 * Paddle represents the player-controlled paddle in the game.
 */
public class Paddle implements Sprite, Collidable {
    private KeyboardSensor keyboard;
    private Rectangle rect;
    private GameEnvironment ge;
    private Rectangle collisionRectangle;

    /**
     * Constructs a new Paddle.
     * @param k the keyboard sensor
     * @param rect the paddle's initial rectangle
     */
    public Paddle(KeyboardSensor k, Rectangle rect) {
        this.rect = new Rectangle(rect.getUpperLeft(), rect.getWidth(), rect.getHeight());
        keyboard = k;
        this.collisionRectangle = new Rectangle(rect.getUpperLeft().getX() - 5,
                rect.getUpperLeft().getY() - 5, rect.getWidth() + 10, rect.getHeight() + 10);
    }

    /**
     * Updates paddle position based on key input.
     */
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    /**
     * Moves the paddle left.
     */
    public void moveLeft() {
        if (this.rect.getUpperLeft().getX() < -rect.getWidth() + 20) {
            this.rect = new Rectangle(
                    new Point(800, rect.getUpperLeft().getY()),
                    this.rect.getWidth(), this.rect.getHeight());
            this.collisionRectangle = new Rectangle(rect.getUpperLeft().getX() - 5,
                    rect.getUpperLeft().getY() - 5, rect.getWidth() + 10, rect.getHeight() + 10);
        }
        this.rect = new Rectangle(
                new Point(this.rect.getUpperLeft().getX() - 3, this.rect.getUpperLeft().getY()),
                this.rect.getWidth(), this.rect.getHeight());
        this.collisionRectangle = new Rectangle(rect.getUpperLeft().getX() - 5,
                rect.getUpperLeft().getY() - 5, rect.getWidth() + 10, rect.getHeight() + 10);
    }

    /**
     * Moves the paddle right.
     */
    public void moveRight() {
        if (this.rect.getUpperLeft().getX() > 780) {
            this.rect = new Rectangle(
                    new Point(-rect.getWidth() + 20, rect.getUpperLeft().getY()),
                    this.rect.getWidth(), this.rect.getHeight());
            this.collisionRectangle = new Rectangle(rect.getUpperLeft().getX() - 5,
                    rect.getUpperLeft().getY() - 5, rect.getWidth() + 10, rect.getHeight() + 10);
        }
        this.rect = new Rectangle(
                new Point(this.rect.getUpperLeft().getX() + 3, this.rect.getUpperLeft().getY()),
                this.rect.getWidth(), this.rect.getHeight());
        this.collisionRectangle = new Rectangle(rect.getUpperLeft().getX() - 5,
                rect.getUpperLeft().getY() - 5, rect.getWidth() + 10, rect.getHeight() + 10);
    }

    /**
     * Draws the paddle on the given surface.
     * @param d the DrawSurface to draw on
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.CYAN);
        d.fillRectangle((int) this.rect.getUpperLeft().getX(),
                (int) this.rect.getUpperLeft().getY(),
                (int) this.rect.getWidth(),
                (int) this.rect.getHeight());
        d.setColor(Color.BLACK);
        d.drawRectangle((int) this.rect.getUpperLeft().getX(),
                (int) this.rect.getUpperLeft().getY(),
                (int) this.rect.getWidth(),
                (int) this.rect.getHeight());
    }

    /**
     * Handles ball collision with the paddle.
     * @param collisionPoint the point of collision
     * @param currentVelocity the current velocity
     * @return updated velocity after the hit
     */

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        int region = getRegion(collisionPoint.getX());
        double speed = currentVelocity.getSpeed();

        switch (region) {
            case 1: return Velocity.fromAngleAndSpeed(300, speed); // sharp left
            case 2: return Velocity.fromAngleAndSpeed(330, speed); // soft left
            case 3: return new Velocity(currentVelocity.getDx(), -Math.abs(currentVelocity.getDy())); // straight up
            case 4: return Velocity.fromAngleAndSpeed(30, speed);  // soft right
            case 5: return Velocity.fromAngleAndSpeed(60, speed);  // sharp right
            default: return new Velocity(currentVelocity.getDx(), -Math.abs(currentVelocity.getDy())); // fallback
        }
    }
    /**
     * Gets the region of the paddle based on the x-coordinate.
     * @param x the x-coordinate of the collision point
     * @return the region number (1-5)
     */
    private int getRegion(double x) {
        Rectangle rect = getCollisionRectangle();
        double startX = rect.getUpperLeft().getX();
        double width = rect.getWidth();
        double regionWidth = width / 5.0;

        int region = (int) ((x - startX) / regionWidth) + 1;

        // Clamp between 1 and 5
        if (region < 1) {
            return 1;
        }
        if (region > 5) {
            return 5;
        }
        return region;
    }
    /**
     * Gets the collision rectangle.
     * @return the paddle's collision rectangle
     */
    public Rectangle getCollisionRectangle() {
        return collisionRectangle;
    }

    /**
     * Adds the paddle to the game.
     * @param g the game to add to
     */
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
        this.ge = g.getEnvironment();
    }
    /**
     * Checks if two doubles are almost equal.
     * @param a first double
     * @param b second double
     * @return true if they are almost equal, false otherwise
     */
    private boolean almostEqual(double a, double b) {
        return Math.abs(a - b) < 1e-6;
    }

}
