package project.game.levels.io.levels.properties;

import project.game.levels.io.levels.ModifiableLevelInformation;

/**
 * {@link LevelNameProperty} is the property that takes care of level names.
 */
public class LevelNameProperty implements LevelProperty {

    @Override
    public boolean is(String name) {
        return name.equals("level_name");
    }

    @Override
    public void apply(String name, String value, ModifiableLevelInformation mli) {
        mli.setLevelName(value);
    }

}
