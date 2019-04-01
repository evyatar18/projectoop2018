package project.game.levels;

import project.game.DrawScreen;
import project.game.objects.Sprite;

/**
 * {@link LevelBackgroundFactory} is a factory that creates {@link Sprite}s which are serving
 *  as level background.
 */
public interface LevelBackgroundFactory {

    /**
     * Generate a background for this level.
     * @param drawScreen : the draw screen the background will be attached to
     * @return a background sprite
     */
    Sprite make(DrawScreen drawScreen);
}
