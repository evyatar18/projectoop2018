package project.game.objects;

import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;

/**
 * {@link BaseComplexSprite} is a base class for all the {@link ComplexSprite}s.
 */
public abstract class BaseComplexSprite extends BaseGameObject implements ComplexSprite {

    private List<Sprite> sprites;

    /**
     * Construct a new {@link BaseComplexSprite}.
     */
    public BaseComplexSprite() {
        sprites = new ArrayList<>();
    }

    /**
     * Draw only this sprite(not the sub-sprites).
     * @param surface : the surface to draw on
     */
    protected abstract void drawThis(DrawSurface surface);

    @Override
    public void drawOn(DrawSurface surface) {
        // first draw this sprite(most likely this sprite is a background)
        drawThis(surface);

        // then draw all the sub-sprites
        for (Sprite sprite : getSubSprites()) {
            sprite.drawOn(surface);
        }
    }

    /**
     * Time pass only for this sprite.
     */
    protected abstract void timePassThis();

    @Override
    public void timePassed(double dt) {
        /* made this way to keep consistency with 'drawOn' */
        timePassThis();

        for (Sprite sprite : getSubSprites()) {
            sprite.timePassed(dt);
        }
    }

    /**
     * Add a sub-sprite to this complex sprite.
     * @param sprite : the sprite to be added
     */
    protected void addSprite(Sprite sprite) {
        sprites.add(sprite);
    }

    /**
     * Remove a sub-sprite from this complex sprite.
     * @param sprite : the sprite to be removed
     */
    protected void removeSprite(Sprite sprite) {
        sprites.remove(sprite);
    }

    @Override
    public List<Sprite> getSubSprites() {
        return sprites;
    }

}
