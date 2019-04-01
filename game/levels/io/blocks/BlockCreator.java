package project.game.levels.io.blocks;

import project.game.objects.collidables.block.Block;

/**
 * {@link BlockCreator} is a factory that generates blocks.
 */
public interface BlockCreator {

    /**
     * Generate a block at a given location.
     * @param xpos : the x position
     * @param ypos : the y position
     * @return a generated block
     */
    Block create(int xpos, int ypos);
}
