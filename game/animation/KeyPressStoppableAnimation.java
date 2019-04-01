package project.game.animation;

import java.awt.Color;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import project.geometry.Point;
import project.misc.DrawUtils;
import project.misc.Utils;

/**
 * {@link KeyPressStoppableAnimation} is an animation that halts when a key is pressed.
 */
public class KeyPressStoppableAnimation implements Animation {

    private Animation animation;
    private String exitKey;
    private KeyboardSensor keyboard;

    private boolean isAlreadyPressed = true;

    /**
     * Construct a new {@link KeyPressStoppableAnimation}.
     * @param keyboard : a keyboard attached to this animation
     * @param exitKey : the exit key to exit this animation
     * @param animation : the animation to run
     */
    public KeyPressStoppableAnimation(KeyboardSensor keyboard, String exitKey, Animation animation) {
        this.keyboard = keyboard;
        this.exitKey = exitKey;
        this.animation = animation;
    }

    @Override
    public void doOneFrame(DrawSurface surface, double dt) {
        DrawUtils draw = new DrawUtils(surface);
        draw.fillBackground(Color.WHITE);

        this.animation.doOneFrame(surface, dt);

        String txt = String.format("Press %s to continue", this.exitKey);
        draw.drawTextSafe(new Point(surface.getWidth() / 8, surface.getHeight() * 7 / 8), txt, 30,
                Color.GREEN, Utils.oppositeColor(Color.GREEN));

        // change is already pressed value
        if (!this.keyboard.isPressed(this.exitKey)) {
            this.isAlreadyPressed = false;
        }
    }

    @Override
    public boolean shouldStop() {
        return !this.isAlreadyPressed && this.keyboard.isPressed(this.exitKey);
    }

}
