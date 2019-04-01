package project.game.events;

/**
 * {@link HitNotifier} notifies {@link HitListener}s that the event has occurred.
 */
public interface HitNotifier {

    /**
     * Add the given listener as a hit listener for this object.
     * @param hl : the listener
     */
    void addHitListener(HitListener hl);

    /**
     * Remove the given listener from listening to hit events for this object.
     * @param hl : the listener
     */
    void removeHitListener(HitListener hl);
}
