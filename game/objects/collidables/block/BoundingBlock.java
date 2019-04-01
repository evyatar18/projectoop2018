package project.game.objects.collidables.block;

import java.awt.Color;

import biuoop.DrawSurface;
import project.game.GameEnvironment;
import project.game.events.HitListener;
import project.game.objects.BaseGameObject;
import project.game.objects.Sprite;
import project.game.objects.collidables.Ball;
import project.game.objects.collidables.Collidable;
import project.geometry.Line;
import project.geometry.Point;
import project.geometry.Rectangle;
import project.geometry.Velocity;
import project.misc.DrawUtils;

/**
 * {@link BoundingBlock} is a block that is a boundary of the screen.
 */
public class BoundingBlock extends BaseGameObject implements Collidable, Sprite {

    private Block block;
    private Color color;

    /**
     * Construct a new {@link BoundingBlock}.
     * @param topLeft : the top left point
     * @param width : the width
     * @param height : the height
     * @param color : the color
     */
    public BoundingBlock(Point topLeft, int width, int height, Color color) {
        this.block = new Block(topLeft, width, height);
        this.color = color;
    }

    /**
     * Add a hit listener to this block.
     * @param listener : the listener to add
     */
    public void addHitListener(HitListener listener) {
        this.block.addHitListener(listener);
    }

    /**
     * Remove a hit listener from this block.
     * @param listener : the listener to remove
     */
    public void removeHitListener(HitListener listener) {
        this.block.removeHitListener(listener);
    }

    @Override
    public void drawOn(DrawSurface surface) {
        DrawUtils utils = new DrawUtils(surface);
        utils.fillRectangle(this.block.getDrawRectangle(), this.color);
    }

    @Override
    public void timePassed(double dt) {
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.block.getCollisionRectangle();
    }

    @Override
    public Velocity hit(Ball ball, Point collisionPoint, Velocity currentVelocity) {
        return this.block.hit(ball, collisionPoint, currentVelocity);
    }

    @Override
    public void setGameEnvironment(GameEnvironment environment) {
    }

    @Override
    public Velocity hit(Ball ball, Point collisionPoint, Velocity currentVelocity, Line collisionLine) {
        return this.block.hit(ball, collisionPoint, currentVelocity, collisionLine);
    }
}
