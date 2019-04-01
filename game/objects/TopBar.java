package project.game.objects;

import java.awt.Color;

import biuoop.DrawSurface;
import project.game.GameLevel;
import project.geometry.EqualSegmentDivision;
import project.geometry.Point;
import project.geometry.Segment;
import project.misc.DrawUtils;

/**
 * {@link TopBar} is a {@link Sprite} which represents the top bar of the game.
 */
public class TopBar extends BaseComplexSprite {

    private final Point position;
    private int width;
    private int height;

    private Color color;

    private final double spacing;

    private Segment barWidth;
    private Segment barContentSegment;

    /**
     * Construct a topbar with a given color.
     * @param color : the background color of the bar
     * @param height : the height of the bar
     */
    public TopBar(Color color, int height) {
        this.position = new Point(0, 0);
        this.spacing = 100;
        this.color = color;
        this.height = height;
    }

    /**
     * Get the width for this bar.
     * @return the width
     */
    public int width() {
        return this.width;
    }

    /**
     * Get the height for this bar.
     * @return the height
     */
    public int height() {
        return this.height;
    }

    /**
     * The top left point of this bar.
     * @return the point
     */
    public Point position() {
        return this.position;
    }

    @Override
    protected void onAdd(GameLevel game) {
        // init width according to the game
        this.width = game.width();

        // init the bar width segment
        this.barWidth = new Segment(0, game.width());
        this.barContentSegment = this.barWidth.subSegmentSpacedEquallyFromEdges(this.getSpacing());
    }

    /**
     * Add a label to this bar.
     * @param sprite : the label sprite
     */
    public void addLabel(Sprite sprite) {
        super.addSprite(sprite);
    }

    /**
     * Get the spacing from the edges.
     * @return spacing from the edges of the screen
     */
    private double getSpacing() {
        return this.spacing;
    }

    /**
     * Get the position for a label on this bar.
     * @param label : the label sprite
     * @return the position for this label
     */
    public Point getPosition(Sprite label) {
        // divide the top bar equally between sub-labels
        int topBarParts = super.getSubSprites().size();
        EqualSegmentDivision division = new EqualSegmentDivision(this.barContentSegment, topBarParts);

        // get the index of current label
        int index = super.getSubSprites().indexOf(label);

        // get the x and y positions for the label
        double x = division.leftmostPosition(index);
        double y = 2;

        // return the point
        return new Point(x, y);
    }

    @Override
    protected void drawThis(DrawSurface surface) {
        DrawUtils utils = new DrawUtils(surface);
        utils.fillRectangle(this.position, this.width, this.height, this.color);
    }

    @Override
    protected void timePassThis() {
    }

}
