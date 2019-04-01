package project.game.levels.io.levels.properties;

import java.util.regex.Pattern;

import project.game.levels.io.levels.ModifiableLevelInformation;
import project.misc.Utils;

/**
 * {@link BlocksStartProperty} is the property that sets the starting position for the blocks.
 */
public class BlocksStartProperty implements LevelProperty {

    private static final Pattern BLOCK_START_PATTERN = Pattern.compile("blocks_start_(x|y)");

    @Override
    public boolean is(String name) {
        return BLOCK_START_PATTERN.matcher(name).matches();
    }

    @Override
    public void apply(String name, String value, ModifiableLevelInformation mli) {
        Integer pos = Utils.tryParseInt(value);

        if (pos != null) {

            if (name.endsWith("x")) {
                // if it's x position
                mli.getBlockGenerator().setBlockStartX(pos);
            } else {
                // if it's y position
                mli.getBlockGenerator().setBlockStartY(pos);
            }
        }
    }

}
