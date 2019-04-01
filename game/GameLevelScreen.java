package project.game;

import project.geometry.Point;
import project.geometry.Segment;

/**
 * {@link GameLevelScreen} is the {@link DrawScreen} that defines the bounds for each {@link GameLevel}.
 */
public class GameLevelScreen implements DrawScreen {

    private final Point topLeft;

    /**
     * Construct a new {@link GameLevelScreen}.
     */
    public GameLevelScreen() {
        this.topLeft = new Point(GameLevel.BOUNDING_BLOCK_WIDTH,
                GameLevel.TOPBAR_HEIGHT + GameLevel.BOUNDING_BLOCK_WIDTH);
    }

    @Override
    public int height() {
        return GameLevel.HEIGHT - (GameLevel.TOPBAR_HEIGHT + GameLevel.BOUNDING_BLOCK_WIDTH);
    }

    @Override
    public int width() {
        return 800 - (2 * GameLevel.BOUNDING_BLOCK_WIDTH);
    }

    @Override
    public Point transform(Point p) {
        return new Point(proportionalX(p.getX()), proportionalY(p.getY()));
    }

    @Override
    public double proportionalX(double fraction) {
        return this.topLeft.getX() + (fraction * width());
    }

    @Override
    public double proportionalY(double fraction) {
        return this.topLeft.getY() + (fraction * height());
    }

    @Override
    public double leftmostX() {
        return proportionalX(0);
    }

    @Override
    public double rightmostX() {
        return proportionalX(1);
    }

    @Override
    public double topY() {
        return proportionalY(0);
    }

    @Override
    public double bottomY() {
        return proportionalY(1);
    }

    @Override
    public Segment widthSegment() {
        return new Segment(leftmostX(), rightmostX());
    }

    @Override
    public Segment heightSegment() {
        return new Segment(topY(), bottomY());
    }

    @Override
    public boolean inside(Point point) {
        // check if the point is inside both axises
        boolean insideX = widthSegment().contains(point.getX());
        boolean insideY = heightSegment().contains(point.getY());

        // return whether it is
        return insideX && insideY;
    }

}
