package project.game.objects;

import java.util.List;

/**
 * {@link ComplexSprite} is a sprite that is composed of other sprites.
 */
public interface ComplexSprite extends Sprite {

    /**
     * Get the sprites this sprite is made from.
     * @return the sub sprites
     */
    List<Sprite> getSubSprites();
}
