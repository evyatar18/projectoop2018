package project.game.objects.collidables.block;

import java.awt.Color;
import java.awt.Image;

import biuoop.DrawSurface;
import project.geometry.Point;
import project.misc.DrawUtils;

/**
 * {@link OneBackground} is a block background.
 */
public class OneBackground implements Background {

    private Color color;
    private Image img;

    /**
     * Construct a background from a given color.
     * @param color : the color
     */
    public OneBackground(Color color) {
        this.color = color;
    }

    /**
     * Construct a background from a given image.
     * @param img : the image
     */
    public OneBackground(Image img) {
        this.img = img;
    }

    @Override
    public void draw(Point p, int width, int height, int hitPoints, DrawSurface surface) {
        DrawUtils draw = new DrawUtils(surface);

        if (this.color != null) {
            draw.fillRectangle(p, width, height, this.color);
        }

        if (this.img != null) {
            draw.drawImage(p, this.img);
        }
    }

    @Override
    public Background getBackground(int hits) {
        return this;
    }

}
