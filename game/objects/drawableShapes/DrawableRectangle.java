package project.game.objects.drawableShapes;

import java.awt.Color;

import biuoop.DrawSurface;
import project.game.objects.Sprite;
import project.geometry.Rectangle;
import project.misc.DrawUtils;

/**
 * {@link DrawableRectangle} is a rectangle that can be drawn.
 */
public class DrawableRectangle implements Sprite {

    private Rectangle rect;
    private Color color;
    private boolean fill;

    /**
     * Construct a new {@link DrawableRectangle}.
     * @param rect : the rectangle to draw
     * @param color : the color of the rectangle
     * @param fill : whether to fill this rectangle or not
     */
    public DrawableRectangle(Rectangle rect, Color color, boolean fill) {
        this.rect = rect;
        this.color = color;
        this.fill = fill;
    }

    @Override
    public void drawOn(DrawSurface surface) {
        DrawUtils draw = new DrawUtils(surface);

        // decide whether to fill this rectangle
        if (fill) {
            draw.fillRectangle(rect, color);
        } else {
            draw.drawRectangle(rect, color);
        }
    }

    @Override
    public void timePassed(double dt) {

    }

}
