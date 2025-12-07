package game.objects;

import biuoop.DrawSurface;
import Geomtry.Point;
import Geomtry.Rectangle;
import game.Game;
import game.collision.Collidable;
import game.collision.Sprite;
import game.listeners.HitListener;
import game.listeners.HitNotifier;

import java.util.List;
import java.util.ArrayList;
/**
 * Represents a block that can be collided with and drawn.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle rect;
    private java.awt.Color color;
    private Rectangle collisionRectangle;
    private List<HitListener> hitListeners;
    /**
     * Constructs a block with a given rectangle and color.
     * @param rect the rectangle shape of the block
     * @param color the color of the block
     */
    public Block(Rectangle rect, java.awt.Color color) {
        this.rect = rect;
        this.color = color;
        this.collisionRectangle = new Rectangle(rect.getUpperLeft().getX() - 5, rect.getUpperLeft().getY() - 5,
                rect.getWidth() + 10, rect.getHeight() + 10);
        this.hitListeners = new ArrayList<>();
    }

    /**
     * Gets the rectangle representing the block's shape.
     * @return the collision rectangle
     */
    public Rectangle getCollisionRectangle() {
        return collisionRectangle;
    }

    /**
     * Handles the collision with a ball and updates its velocity.
     * @param hitter the ball that hit the block
     * @param collisionPoint the point of collision
     * @param currentVelocity the current velocity before collision
     * @return the updated velocity after the collision
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {

        double blockTop = collisionRectangle.getUpperLeft().getY();
        double blockBottom = collisionRectangle.getUpperLeft().getY() + collisionRectangle.getHeight();
        double blockLeft = collisionRectangle.getUpperLeft().getX();
        double blockRight = collisionRectangle.getUpperLeft().getX() + collisionRectangle.getWidth();

        if (!ballColorMatch(hitter)) {
            this.notifyHit(hitter);
        }

        if (collisionPoint.getY() == blockTop) {
            return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        } else if (collisionPoint.getY() == blockBottom) {
            return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        } else if (collisionPoint.getX() == blockLeft) {
            return new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
        } else if (collisionPoint.getX() == blockRight) {
            return new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
        }

        return currentVelocity;
    }

    /**
     * Draws the block on the given DrawSurface.
     * @param d the DrawSurface to draw on
     */
    public void drawOn(DrawSurface d) {
        d.setColor(color);
        d.fillRectangle((int) rect.getUpperLeft().getX(),
                (int) rect.getUpperLeft().getY(),
                (int) rect.getWidth(),
                (int) rect.getHeight());
        d.setColor(java.awt.Color.BLACK);
        d.drawRectangle((int) rect.getUpperLeft().getX(),
                (int) rect.getUpperLeft().getY(),
                (int) rect.getWidth(),
                (int) rect.getHeight());
    }

    /**
     * Called every frame to indicate time has passed.
     */
    public void timePassed() {
        // No specific action needed for Block in timePassed
    }

    /**
     * Adds the block to the game.
     * @param g the game to add this block to
     */
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    /**
     * return true if the color of the ball match's the color of the block.
     * @param ball
     * @return true or false
     */
    public boolean ballColorMatch(Ball ball) {
        return this.color == ball.getColor();
    }

    /**
     * Removes the block from the game.
     * @param g the game to remove this block from
     */
    public void removeFromGame(Game g) {
        g.removeCollidable(this);
        g.removeSprite(this);
    }

    /**
     * Adds a hit listener to the block.
     * @param hl the HitListener to add
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * Removes a hit listener from the block.
     * @param hl the HitListener to remove
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * Notifies all hit listeners that a hit event has occurred.
     * @param hitter the ball that hit the block
     */
    private void notifyHit(Ball hitter) {
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * Gets the color of the block.
     * @return the color of the block
     */
    public java.awt.Color getColor() {
        return this.color;
    }

}