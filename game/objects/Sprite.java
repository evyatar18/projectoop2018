package project.game.objects;
import biuoop.DrawSurface;

/**
 * A sprite in the scene.
 */
public interface Sprite {

    /**
     * Draw this sprite on a given surface.
     * @param surface : the given surface
     */
    void drawOn(DrawSurface surface);

    /**
     * Notify this sprite that a unit of time has passed.
     * @param dt : the amount of time passed
     */
    void timePassed(double dt);
}
