package project.game.levels.io.levels.properties;

import project.game.levels.io.BackgroundParser;
import project.game.levels.io.levels.FixedBackgroundSprite;
import project.game.levels.io.levels.ModifiableLevelInformation;

/**
 * {@link BackgroundProperty} is the background property of a given level.
 */
public class BackgroundProperty implements LevelProperty {

    private BackgroundParser parser = new BackgroundParser();

    @Override
    public boolean is(String name) {
        return name.equals("background");
    }

    @Override
    public void apply(String name, String value, ModifiableLevelInformation mli) {
        mli.setBackground(new FixedBackgroundSprite(this.parser.getBackground(value)));
    }

}
