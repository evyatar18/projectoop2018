package project.game.levels;

import java.util.List;

import project.game.objects.collidables.block.Block;

/**
 * {@link BlocksGenerator} is a class that takes care of generating a list of blocks.
 */
public interface BlocksGenerator {

    /**
     * Generate blocks.
     * @return a list of blocks
     */
    List<Block> generate();

    /**
     * The amount of blocks which will be generated.
     * @return the amount of blocks
     */
    int amount();
}
