package project.game.levels;

import project.game.DrawScreen;

/**
 * {@link BlocksGeneratorFactory} is a factory that initializes {@link BlocksGenerator} objects.
 */
public interface BlocksGeneratorFactory {

    /**
     * Initialize a {@link BlocksGenerator} object.
     * @param drawScreen : the {@link DrawScreen} the blocks will be positioned on
     * @return a {@link BlocksGenerator} object
     */
    BlocksGenerator make(DrawScreen drawScreen);
}
