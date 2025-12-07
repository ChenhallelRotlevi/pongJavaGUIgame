package game.listeners;

/**
 * HitNotifier interface for objects that can notify HitListeners about hit events.
 * This interface defines methods to add and remove HitListeners.
 */
public interface HitNotifier {
    /**
     * Adds a listener to the notifier.
     * @param hl the HitListener to be added
     */
    void addHitListener(HitListener hl);

    /**
     * Removes a listener from the notifier.
     * @param hl the HitListener to be removed
     */
    void removeHitListener(HitListener hl);
}
