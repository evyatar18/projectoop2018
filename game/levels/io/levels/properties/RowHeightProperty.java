package project.game.levels.io.levels.properties;

import project.game.levels.io.levels.ModifiableLevelInformation;
import project.misc.Utils;

/**
 * {@link RowHeightProperty} sets the row height for the blocks.
 */
public class RowHeightProperty implements LevelProperty {

    @Override
    public boolean is(String name) {
        return name.equals("row_height");
    }

    @Override
    public void apply(String name, String value, ModifiableLevelInformation mli) {
        Integer h = Utils.tryParseInt(value);

        if (h != null) {
            mli.getBlockGenerator().setRowHeight(h);
        }
    }

}
