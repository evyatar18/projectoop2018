package project.game.levels.io.blocks.properties;

import project.game.objects.collidables.block.Block;
import project.misc.Utils;


/**
 * {@link WidthProperty} recognizes and applies width values.
 */
public class WidthProperty implements BlockProperty {

    @Override
    public boolean is(String name) {
        return name.equals("width");
    }

    @Override
    public void apply(String name, String value, Block b) {
        Integer i = Utils.tryParseInt(value);

        if (i != null) {
            b.setWidth(i);
        }
    }

}
