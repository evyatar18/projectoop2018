package project.game.objects.collidables;
import project.game.GameEnvironment;
import project.geometry.Line;
import project.geometry.Point;
import project.geometry.Rectangle;
import project.geometry.Velocity;

/**
 * An interface representing an object that can collide with other {@link Collidable} objects.
 */
public interface Collidable {

    String ERR_NO_COLLISION_LINE = "Cannot calculate velocity after hit if not supplied with collision line.";

    /**
     * Get the collision rectangle for this object.
     * @return a rectangle in which this object collides
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify this object that it was hit by another collidable object.
     * @param ball : the ball that hit this object
     * @param collisionPoint : the point where the collision occurred
     * @param currentVelocity : the collision velocity
     * @return the new velocity for the other collidable object
     */
    Velocity hit(Ball ball, Point collisionPoint, Velocity currentVelocity);

    /**
     * Notify this object that it was hit by another collidable object.
     * @param ball : the ball that hit this object
     * @param collisionPoint : the point where the collision occurred
     * @param currentVelocity : the collision velocity
     * @param collisionLine : the line of this collision rectangle which was collided with
     * @return the new velocity for the other collidable object
     */
    Velocity hit(Ball ball, Point collisionPoint, Velocity currentVelocity, Line collisionLine);

    /**
     * Sets the game environment for this Collidable.
     * @param environment : the environment to set
     */
    void setGameEnvironment(GameEnvironment environment);
}
