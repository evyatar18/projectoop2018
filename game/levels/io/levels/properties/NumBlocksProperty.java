package project.game.levels.io.levels.properties;

import project.game.levels.io.levels.ModifiableLevelInformation;
import project.misc.Utils;

/**
 * {@link NumBlocksProperty} sets the amount of blocks to be destroyed in order to complete a level.
 */
public class NumBlocksProperty implements LevelProperty {

    @Override
    public boolean is(String name) {
        return name.equals("num_blocks");
    }

    @Override
    public void apply(String name, String value, ModifiableLevelInformation mli) {
        Integer num = Utils.tryParseInt(value);

        if (num != null) {
            mli.setNumberOfBlocksToRemove(num);
        }
    }

}
