package game.listeners;

import game.objects.Ball;
import game.objects.Block;
/**
 * PrintingHitListener is a HitListener that prints a message
 * whenever a block is hit by a ball.
 * This is primarily for debugging or logging purposes.
 */
public class PrintingHitListener implements HitListener {
    /**
     * This method is called whenever a block is hit.
     * It prints a message indicating that a block was hit.
     *
     * @param beingHit the block that was hit
     * @param hitter   the ball that hit it
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        System.out.println("A Block was hit.");
    }
}