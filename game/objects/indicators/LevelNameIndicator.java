package project.game.objects.indicators;

import project.game.objects.BaseLabel;

/**
 * {@link LevelNameIndicator} is the indicator that shows the level of the name.
 */
public class LevelNameIndicator extends BaseLabel {

    private final String levelName;

    /**
     * Construct a new {@link LevelNameIndicator}.
     * @param levelName : the level name
     */
    public LevelNameIndicator(String levelName) {
        this.levelName = levelName;
    }

    @Override
    protected String getText() {
        return "Level Name: " + this.levelName;
    }
}
