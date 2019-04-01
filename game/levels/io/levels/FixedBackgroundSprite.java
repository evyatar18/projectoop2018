package project.game.levels.io.levels;

import biuoop.DrawSurface;
import project.game.objects.Sprite;
import project.game.objects.collidables.block.Background;
import project.geometry.Point;

/**
 * {@link FixedBackgroundSprite} is a background which is fixed to a DrawSurface.
 */
public class FixedBackgroundSprite implements Sprite {

    private Background background;

    /**
     * Construct a new {@link FixedBackgroundSprite}.
     * @param background : the background
     */
    public FixedBackgroundSprite(Background background) {
        this.background = background;
    }

    @Override
    public void drawOn(DrawSurface surface) {
        this.background.draw(new Point(0, 0), surface.getWidth(), surface.getHeight(), 0, surface);
    }

    @Override
    public void timePassed(double dt) {
        // TODO Auto-generated method stub

    }

}
