package project.game.levels.io.levels.properties;

import project.game.levels.io.levels.ModifiableLevelInformation;
import project.misc.Utils;

/**
 * {@link PaddleSpeedProperty} is the property that sets the paddle speed.
 */
public class PaddleSpeedProperty implements LevelProperty {

    @Override
    public boolean is(String name) {
        return name.equals("paddle_speed");
    }

    @Override
    public void apply(String name, String value, ModifiableLevelInformation mli) {
        Integer speed = Utils.tryParseInt(value);

        if (speed != null) {
            mli.setPaddleSpeed(speed);
        }
    }

}
