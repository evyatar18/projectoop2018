package project.game;
import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;
import project.game.objects.Sprite;

/**
 * A collection of sprites.
 */
public class SpriteCollection {

    private List<Sprite> sprites;

    /**
     * Construct a new collection of sprites.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<>();
    }

    /**
     * Add a sprite to the collection.
     * @param sprite : the sprite to add
     */
    public void addSprite(Sprite sprite) {
        this.sprites.add(sprite);
    }

    private double timeDiff;

    /**
     * Set the passed time.
     * @param dt : the time passed since last call
     */
    public void setTimePassed(double dt) {
        this.timeDiff = dt;
    }

    /**
     * Notify all the sprites that time has passed.
     */
    public void notifyAllTimePassed() {
        List<Sprite> spritesClone = new ArrayList<>(this.sprites);

        for (Sprite sprite : spritesClone) {
            sprite.timePassed(this.timeDiff);
        }
    }

    /**
     * Draw all the sprites on a given {@link DrawSurface}.
     * @param surface : the surface
     */
    public void drawAllOn(DrawSurface surface) {
        for (Sprite sprite : this.sprites) {
            sprite.drawOn(surface);
        }
    }

    /**
     * Remove a sprite from this collection.
     * @param sprite : the sprite to be removed
     */
    public void removeSprite(Sprite sprite) {
        this.sprites.remove(sprite);
    }
}
