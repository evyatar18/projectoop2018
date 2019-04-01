package project.game.animation;

import biuoop.DrawSurface;

/**
 * {@link Animation} is an animation.
 */
public interface Animation {

    /**
     * Animate one frame.
     * @param surface : the surface to draw on
     * @param dt : the amount of time passed since the last call in seconds
     */
    void doOneFrame(DrawSurface surface, double dt);

    /**
     * Query whether this animation is finished.
     * @return true if it should. false otherwise.
     */
    boolean shouldStop();
}
