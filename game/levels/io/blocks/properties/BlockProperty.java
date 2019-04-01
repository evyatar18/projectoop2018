package project.game.levels.io.blocks.properties;

import project.game.objects.collidables.block.Block;

/**
 * {@link BlockProperty} is a property that can be applied to blocks.
 */
public interface BlockProperty {

    /**
     * Query whether a given name is the name matching this property.
     * @param name : the name of the property
     * @return true if it is. false otherwise.
     */
    boolean is(String name);

    /**
     * Apply this property to a given block.
     * @param name : the name of the property
     * @param value : the value of the property
     * @param b : the block
     */
    void apply(String name, String value, Block b);
}
