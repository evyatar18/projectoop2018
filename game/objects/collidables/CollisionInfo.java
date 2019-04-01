package project.game.objects.collidables;
import project.geometry.Line;
import project.geometry.Point;

/**
 * This class holds information about a collision that occurred.
 */
public class CollisionInfo {

    private final Point collisionPoint;
    private final Collidable collisionObject;
    private final Line collisionLine;

    /**
     * Construct a new {@link CollisionInfo} instance.
     * @param collisionPoint : the collision point
     * @param collisionObject : the colliding object
     */
    @SuppressWarnings("unused")
    private CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this(collisionPoint, collisionObject, null);
    }

    /**
     * Construct a new {@link CollisionInfo} instance.
     * @param collisionPoint : the collision point
     * @param collisionObject : the colliding object
     * @param collisionLine : the line of the object that was collided with
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject, Line collisionLine) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
        this.collisionLine = collisionLine;
    }

    /**
     * Get the collision point for this collision.
     * @return the collision point
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * Get the colliding object in this collision.
     * @return the colliding object
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }

    /**
     * Get the bounding line that was the cause of this collision.
     * @return the bounding line.
     */
    public Line collisionLine() {
        return this.collisionLine;
    }
}
