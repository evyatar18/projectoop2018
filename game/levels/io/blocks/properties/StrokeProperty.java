package project.game.levels.io.blocks.properties;

import java.awt.Color;

import project.game.levels.io.ColorsParser;
import project.game.objects.collidables.block.Block;

/**
 * {@link StrokeProperty} recognizes and applies stroke values.
 */
public class StrokeProperty implements BlockProperty {

    private ColorsParser parser = new ColorsParser();

    @Override
    public boolean is(String name) {
        return name.equals("stroke");
    }

    @Override
    public void apply(String name, String value, Block b) {
        Color c = this.parser.colorFromString(value);

        if (c != null) {
            b.setStroke(c);
        }
    }

}
