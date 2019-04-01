package project.game.animation;

import java.awt.Color;

import biuoop.DrawSurface;
import project.game.scores.HighScoresTable;
import project.game.scores.ScoreInfo;
import project.geometry.Point;
import project.misc.DrawUtils;

/**
 * {@link HighScoresAnimation} is the animation that shows high scores.
 */
public class HighScoresAnimation implements Animation {

    private final HighScoresTable scores;

    /**
     * Construct a new {@link HighScoresAnimation}.
     * @param scores : a high scores table
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.scores = scores;
    }


    private static final int HEIGHT_TAB = 30;
    private static final int WIDTH_TAB = 300;
    private static final int FONT_SIZE = 20;

    /**
     * Draw the text inside the table (one row).
     * @param p : the top left point of the row
     * @param text : the text to draw
     * @param draw : a {@link DrawUtils} instance
     */
    private void drawTableRow(Point p, String[] text, DrawUtils draw) {

        for (int i = 0; i < text.length; i++) {
            // draw the wrapping rectangle
            draw.drawRectangle(p, WIDTH_TAB, HEIGHT_TAB, Color.black);

            // draw the text
            draw.drawText(p.asVector().add(0, FONT_SIZE + 2).asPoint(),
                    text[i], FONT_SIZE, Color.BLACK);

            // move the point a bit
            int dx = WIDTH_TAB;
            p = p.asVector().add(dx, 0).asPoint();
        }

    }

    @Override
    public void doOneFrame(DrawSurface surface, double dt) {
        DrawUtils draw = new DrawUtils(surface);

        // paint it white
        draw.fillBackground(Color.WHITE);

        // decide on x and y positions
        Point p = new Point(100, 100);

        // draw the table
        drawTableRow(p, new String[] {"Player Name", "Player Score"}, draw);

        for (ScoreInfo scoreInfo : this.scores.getHighScores()) {
            p = p.asVector().add(0, HEIGHT_TAB).asPoint();

            drawTableRow(p, new String[] {scoreInfo.getName(), String.valueOf(scoreInfo.getScore())}, draw);
        }
    }

    @Override
    public boolean shouldStop() {
        return false;
    }
}
