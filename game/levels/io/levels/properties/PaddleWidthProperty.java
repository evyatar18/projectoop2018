package project.game.levels.io.levels.properties;

import project.game.levels.io.levels.ModifiableLevelInformation;
import project.misc.Utils;

/**
 * {@link PaddleWidthProperty} is the property that sets the paddle width.
 */
public class PaddleWidthProperty implements LevelProperty {

    @Override
    public boolean is(String name) {
        return name.equals("paddle_width");
    }

    @Override
    public void apply(String name, String value, ModifiableLevelInformation mli) {
        Integer width = Utils.tryParseInt(value);

        if (width != null) {
            mli.setPaddleWidth(width);
        }
    }

}
