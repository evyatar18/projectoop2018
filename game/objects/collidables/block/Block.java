package project.game.objects.collidables.block;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;
import project.game.GameEnvironment;
import project.game.events.HitListener;
import project.game.events.HitNotifier;
import project.game.objects.BaseGameObject;
import project.game.objects.Sprite;
import project.game.objects.collidables.Ball;
import project.game.objects.collidables.Collidable;
import project.geometry.Line;
import project.geometry.Point;
import project.geometry.Rectangle;
import project.geometry.Vector2D;
import project.geometry.Velocity;
import project.misc.DrawUtils;

/**
 * A block in the game.
 */
public class Block extends BaseGameObject implements Collidable, Sprite, HitNotifier {

    public static final int BASIC_WIDTH = 60;
    public static final int BASIC_HEIGHT = 25;

    private Rectangle rect;
    private boolean countHits;

    // default hits
    private int hits;

    // the color of the borders
    private Color stroke;

    // the background of the block
    private Background background;


    /**
     * Construct a new block based only on the top left position.
     * @param topLeft : the top left position
     */
    public Block(Point topLeft) {
        this(topLeft, BASIC_WIDTH, BASIC_HEIGHT);
    }

    /**
     * Construct a new block.
     * @param topLeft : the top left corner of the block
     * @param width : the width of the block
     * @param height : the height of the block
     */
    public Block(Point topLeft, int width, int height) {
        this.rect = new Rectangle(topLeft, width, height);

        // let a block have 1 hit point
        this.hits = 1;

        this.countHits = true;
    }

    /**
     * Construct a new block.
     * @param rect : the rectangle this block is constructed from
     */
    public Block(Rectangle rect) {
        this(rect.getUpperLeft(), rect.getWidth(), rect.getHeight());
    }

    /**
     * Get the rectangle of the draw.
     * @return the rectangle
     */
    public Rectangle getDrawRectangle() {
        return this.rect;
    }


    /**
     * Get the top left position of this block.
     * @return the top left position
     */
    public Point topLeft() {
        return this.rect.getUpperLeft();
    }

    /**
     * Set the width of this rectangle.
     * @param w : the new width
     */
    public void setWidth(int w) {
        this.rect = new Rectangle(topLeft(), w, height());
    }

    /**
     * Get the width of this block.
     * @return the width
     */
    public int width() {
        return this.rect.getWidth();
    }

    /**
     * Set the height of this rectangle.
     * @param h : the new height
     */
    public void setHeight(int h) {
        this.rect = new Rectangle(topLeft(), width(), h);
    }

    /**
     * Get the height of this block.
     * @return the height
     */
    public int height() {
        return this.rect.getHeight();
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    @Override
    public Velocity hit(Ball ball, Point collisionPoint, Velocity currentVelocity, Line collisionLine) {
        countHit();

        Vector2D vec;

        // if hit a vertex, return in the same direction
        if (this.getCollisionRectangle().isVertex(collisionPoint)) {
            vec = currentVelocity.asVector().multiply(-1);
        } else {
            // get the velocity as vector so that we can perform operations
            vec = currentVelocity.asVector();

            // if the line is the x-axis line, minus velocity at y axis
            if (collisionLine.direction().onSameLine(Vector2D.X_UNIT)) {
                vec.multiplyY(-1);
            }

            // if the line is the y-axis line, minus velocity at x axis
            if (collisionLine.direction().onSameLine(Vector2D.Y_UNIT)) {
                vec.multiplyX(-1);
            }
        }

        this.notifyHit(ball);
        return new Velocity(vec);
    }

    @Override
    public Velocity hit(Ball ball, Point collisionPoint, Velocity currentVelocity) {
        throw new RuntimeException(ERR_NO_COLLISION_LINE);
    }

    /**
     * Count 1 hit for this block.
     */
    private void countHit() {
        if (this.countHits && this.hits > 0) {
            this.hits--;
        }
    }

    /**
     * Set the amount of hits this block can take.
     * @param hitPoints : the amount of hits.
     */
    public void setHits(int hitPoints) {
        this.hits = hitPoints;
    }

    /**
     * Get the amount of hits this block can take.
     * @return the amount of hits left
     */
    public int getHitPoints() {
        return this.hits;
    }

    @Override
    public void timePassed(double dt) { }

    @Override
    public void setGameEnvironment(GameEnvironment ge) { }

    /**
     * Set the background of this block.
     * @param bg : the background
     */
    public void setBackground(Background bg) {
        this.background = bg;
    }

    /**
     * Get the background of this block.
     * @return the background instance
     */
    public Background getBackground() {
        return this.background;
    }

    /**
     * Set the stroke color of this block.
     * @param s : the color
     */
    public void setStroke(Color s) {
        this.stroke = s;
    }

    @Override
    public void drawOn(DrawSurface surface) {
        DrawUtils utils = new DrawUtils(surface);

        // draw background
        this.background.draw(topLeft(), width(), height(), getHitPoints(), surface);

        // if there's stroke, draw it
        if (this.stroke != null) {
            utils.drawRectangle(this.rect, this.stroke);
        }
    }

    private List<HitListener> hitListeners = new ArrayList<>();

    /**
     * Notify that a hit has occurred.
     * @param hitter : the hitting ball
     */
    private void notifyHit(Ball hitter) {
        List<HitListener> l = new ArrayList<>(this.hitListeners);

        // notify all listeners
        for (HitListener listener : l) {
            listener.hitEvent(this, hitter);
        }
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    @Override
    public String toString() {
        final String format = "Block = {loc: %s, width: %s, height: %s}";
        return String.format(format, this.rect.getUpperLeft(), this.rect.getWidth(), this.rect.getHeight());
    }
}
