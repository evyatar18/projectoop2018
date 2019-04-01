package project.game.animation;

import java.awt.Color;

import biuoop.DrawSurface;
import project.geometry.Point;
import project.misc.DrawUtils;
import project.misc.Utils;

/**
 * {@link BaseTextScreen} is a base class for all the classes that display text on the screen.
 */
public abstract class BaseTextScreen implements Animation {

    /**
     * Get the text that should be displayed in this screen.
     * @return the text
     */
    protected abstract String getText();

    private static final Color BACKGROUND_COLOR = Color.BLACK;
    private static final Color TEXT_COLOR = Color.WHITE;

    @Override
    public void doOneFrame(DrawSurface surface, double dt) {
        DrawUtils draw = new DrawUtils(surface);

        // draw background
        draw.fillBackground(BACKGROUND_COLOR);

        /* text parameters */
        int fontSize = 32;
        int x = 100;
        int y = surface.getHeight() / 2;

        // draw the text on the screen
        draw.drawTextSafe(new Point(x, y), getText(), fontSize, TEXT_COLOR, Utils.oppositeColor(TEXT_COLOR));
    }

    @Override
    public boolean shouldStop() {
        // if they display text on the screen, they should appear for some time
        return false;
    }
}
