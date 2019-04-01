package project.game.levels.io.levels.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.game.levels.io.levels.ModifiableLevelInformation;
import project.geometry.Velocity;

/**
 * {@link BallVelocitiesProperty} is a property that takes care of ball velocities.
 */
public class BallVelocitiesProperty implements LevelProperty {

    private static final Pattern VELOCITY_PATTERN = Pattern.compile("(-?\\d+),(\\d+)");

    @Override
    public boolean is(String name) {
        return name.equals("ball_velocities");
    }

    @Override
    public void apply(String name, String value, ModifiableLevelInformation mli) {
        mli.setInitialBallVelocities(parseVelocities(value));
    }

    /**
     * Parses velocities from a given string.
     * @param str : the string
     * @return a list of velocities
     */
    private List<Velocity> parseVelocities(String str) {
        List<Velocity> velocities = new ArrayList<>();

        Matcher m = VELOCITY_PATTERN.matcher(str);

        while (m.find()) {
            int angle = Integer.parseInt(m.group(1));
            int speed = Integer.parseInt(m.group(2));

            velocities.add(Velocity.fromAngleAndSpeed(angle, speed));
        }

        return velocities;
    }
}
