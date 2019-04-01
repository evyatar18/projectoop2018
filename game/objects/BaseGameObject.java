package project.game.objects;

import project.game.GameLevel;
import project.game.objects.collidables.Collidable;

/**
 * {@link BaseGameObject} is a base class for all the game objects.
 */
public abstract class BaseGameObject {

    private GameLevel game;

    /**
     * Query whether this class is a {@link Collidable}.
     * @return true if it is. false otherwise.
     */
    private boolean isCollidable() {
        return Collidable.class.isInstance(this);
    }

    /**
     * Query whether this class is a {@link Sprite}.
     * @return true if it is. false otherwise.
     */
    private boolean isSprite() {
        return Sprite.class.isInstance(this);
    }

    /**
     * An optional method that can be used in order to do something when this object is added to a game.
     * @param gameLevel : the game this object was added to
     */
    protected void onAdd(GameLevel gameLevel) { }

    /**
     * Add this object to a game.
     * @param g : the game
     */
    public void addToGame(GameLevel g) {
        this.game = g;

        onAdd(this.game);

        if (isCollidable()) {
            this.game.addCollidable((Collidable) this);
        }

        if (isSprite()) {
            this.game.addSprite((Sprite) this);
        }
    }

    /**
     * Remove this object from a game.
     * @param g : the game
     */
    public void removeFromGame(GameLevel g) {
        if (isCollidable()) {
            g.removeCollidable((Collidable) this);
        }

        if (isSprite()) {
            g.removeSprite((Sprite) this);
        }
    }

    /**
     * Get the {@link GameLevel} for this object.
     * @return the game level
     */
    protected GameLevel getGame() {
        return this.game;
    }
}
