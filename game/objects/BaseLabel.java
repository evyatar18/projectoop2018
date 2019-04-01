package project.game.objects;

import java.awt.Color;

import biuoop.DrawSurface;
import project.game.GameLevel;
import project.geometry.Point;
import project.misc.DrawUtils;

/**
 * {@link BaseLabel} is a class that takes care of creating labels.
 */
public abstract class BaseLabel extends BaseGameObject implements Sprite {

    private TopBar topBar;

    @Override
    public void addToGame(GameLevel game) {
        topBar = game.getTopBar();
        topBar.addLabel(this);
    }

    @Override
    public void removeFromGame(GameLevel g) {
        g.getTopBar().removeSprite(this);
    }

    /**
     * Get the position of this label.
     * @return the top left position
     */
    private Point getPosition() {
        return topBar.getPosition(this);
    }

    /**
     * Get the text presented on this label.
     * @return the text
     */
    protected abstract String getText();

    private Color color = Color.WHITE;

    private float hue;

    @Override
    public void drawOn(DrawSurface surface) {
        // init draw utils
        DrawUtils draw = new DrawUtils(surface);

        // get font size and text position
        int fontSize = 18;
        Point textPos = getPosition().asVector().add(0, fontSize).asPoint();

        // draw the text
        draw.drawText(textPos, getText(), fontSize, this.color);
    }

    @Override
    public void timePassed(double dt) {
        hue += 0.05;

        color = Color.getHSBColor(hue, 1, 1);
    }

}
