package game.listeners;

import game.objects.Ball;
import game.objects.Block;

/**
 * HitListener interface for objects that want to be notified when a hit occurs.
 * This interface defines the method that should be implemented to handle hit events.
 */
public interface HitListener {
    /**
     * This method is called whenever a hit occurs.
     *
     * @param beingHit the object that was hit
     * @param hitter   the object that hit it
     */
    void hitEvent(Block beingHit, Ball hitter);
}
