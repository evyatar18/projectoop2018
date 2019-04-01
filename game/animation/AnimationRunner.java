package project.game.animation;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * The {@link AnimationRunner} runs one animation.
 */
public class AnimationRunner {

    private final GUI gui;
    private final int fps;
    private final Sleeper sleeper;

    /**
     * Construct a new animation runner.
     * @param gui : the gui to draw on
     * @param fps : the amount of frames per second
     */
    public AnimationRunner(GUI gui, int fps) {
        this.gui = gui;
        this.fps = fps;
        this.sleeper = new Sleeper();
    }

    /**
     * Construct a new animation runner.
     * @param gui : the gui to draw on
     */
    public AnimationRunner(GUI gui) {
        this(gui, 60);
    }

    /**
     * Run an animation.
     * @param animation : the animation to run
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = 1000 / this.fps;

        // while the animation is yet to be done
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing

            // draw frame
            DrawSurface surface = this.gui.getDrawSurface();
            animation.doOneFrame(surface, 1D / this.fps);
            this.gui.show(surface);

            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long millisecondsLeftToSleep = millisecondsPerFrame - usedTime;

            // if still needs sleep
            if (millisecondsLeftToSleep > 0) {
                this.sleeper.sleepFor(millisecondsLeftToSleep);
            }
        }
    }
}
