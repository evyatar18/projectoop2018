package project.game.levels.io.blocks.properties;

import project.game.objects.collidables.block.Block;
import project.misc.Utils;


/**
 * {@link HeightProperty} recognizes and applies height properties.
 */
public class HeightProperty implements BlockProperty {

    @Override
    public boolean is(String name) {
        return name.equals("height");
    }

    @Override
    public void apply(String name, String value, Block b) {
        Integer i = Utils.tryParseInt(value);

        if (i != null) {
            b.setHeight(i);
        }
    }

}
