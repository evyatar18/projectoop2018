package project.game.animation;

import java.awt.Color;

import biuoop.DrawSurface;
import project.game.SpriteCollection;
import project.geometry.EqualSegmentDivision;
import project.geometry.Point;
import project.geometry.Segment;
import project.misc.DrawUtils;
import project.misc.Utils;

/**
 * {@link CountdownAnimation} is a countdown animation.
 */
public class CountdownAnimation implements Animation {

    private final Segment timeSegment;
    private final EqualSegmentDivision countdownFinder;

    private final SpriteCollection gameScreen;

    private final int countdownStart;

    /**
     * Construct a new {@link CountdownAnimation}.
     * @param numOfSeconds : the amount of seconds to display the count
     * @param countFrom : the number to start counting down from
     * @param gameScreen : all the sprites in this game
     */
    public CountdownAnimation(double numOfSeconds,
            int countFrom,
            SpriteCollection gameScreen) {
        // create the time segment for this animation
        this.timeSegment = Segment.fromStartAndWidth(System.currentTimeMillis(), numOfSeconds * 1000);

        // create an equal time segment division which will decide what countdown to display
        this.countdownFinder = new EqualSegmentDivision(this.timeSegment, countFrom);

        this.countdownStart = countFrom;
        this.gameScreen = gameScreen;
    }

    /**
     * Get the current countdown number.
     * @return the number
     */
    private int currentCountdown() {
        int index = this.countdownFinder.getIndexForPosition(System.currentTimeMillis());

        // we subtract because we start from the bigger numbers
        int countdown = this.countdownStart - index;

        return countdown;
    }

    @Override
    public void doOneFrame(DrawSurface surface, double dt) {
        String count = String.valueOf(currentCountdown());

        // draw the game sprites
        this.gameScreen.drawAllOn(surface);

        int fontSize = 30;
        Point screenMid = new Point(surface.getWidth() / 2, surface.getHeight() / 2 + fontSize);

        DrawUtils draw = new DrawUtils(surface);

        draw.drawTextSafe(screenMid, count, fontSize, Color.DARK_GRAY, Utils.oppositeColor(Color.DARK_GRAY));
    }

    @Override
    public boolean shouldStop() {
        // if the current time is out of the time segment, we are done
        return !this.timeSegment.contains(System.currentTimeMillis());
    }

}
