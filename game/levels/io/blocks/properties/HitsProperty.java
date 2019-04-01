package project.game.levels.io.blocks.properties;

import project.game.objects.collidables.block.Block;
import project.misc.Utils;

/**
 * {@link HitsProperty} recognizes and applies hit points properties.
 */
public class HitsProperty implements BlockProperty {

    @Override
    public boolean is(String name) {
        return name.equals("hit_points");
    }

    @Override
    public void apply(String name, String value, Block b) {
        Integer i = Utils.tryParseInt(value);

        if (i != null) {
            b.setHits(i);
        }
    }


}
