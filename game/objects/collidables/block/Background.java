package project.game.objects.collidables.block;

import biuoop.DrawSurface;
import project.geometry.Point;

/**
 * {@link Background} is a background for a block.
 */
public interface Background {


    /**
     * Get a {@link Background} for a given amount of hits.
     * @param hits : the amount of hits
     * @return a {@link Background} instance
     */
    Background getBackground(int hits);

    /**
     * Draw the background of the block.
     * @param p : the top left position of the block
     * @param width : the width of the block
     * @param height : the height of the block
     * @param hitPoints : the current amount of hit points the block has
     * @param surface : a surface to draw the background on
     */
    void draw(Point p, int width, int height, int hitPoints, DrawSurface surface);
}
