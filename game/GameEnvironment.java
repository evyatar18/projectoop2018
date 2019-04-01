package project.game;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import project.game.objects.collidables.Collidable;
import project.game.objects.collidables.CollisionInfo;
import project.geometry.Line;
import project.geometry.LineIntersection;
import project.geometry.Point;

/**
 * A class representing a 2D plane which is a game environment.
 */
public class GameEnvironment {

    public static final double MINIMUM_MOVEMENT = 1D;

    private List<Collidable> collidables;

    /**
     * Construct a new {@link GameEnvironment} instance.
     */
    public GameEnvironment() {
        // construct the collidables list
        collidables = new ArrayList<>();
    }

    /**
     * Get all the Collidable objects.
     * @return an array containing them
     */
    public Collidable[] getCollidables() {
        return collidables.toArray(new Collidable[collidables.size()]);
    }

    /**
     * Add a new collidable object to this game environment.
     * @param object : the object to be added
     */
    public void addCollidable(Collidable object) {
        collidables.add(object);

        // set the environment
        object.setGameEnvironment(this);
    }

    /**
     * Get the closest collision for a given trajectory line(if there is a collision).
     * @param trajectory : the trajectory line.
     * @return a {@link CollisionInfo} instance describing the expected collision
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        return getClosestCollision(trajectory, new Predicate<Collidable>() {

            @Override
            public boolean test(Collidable t) {
                return true;
            }

        });
    }

    /**
     * Get the closest collision for a given trajectory line and a
     * predicate telling which collidables should be included.
     * @param trajectory : the trajectory
     * @param include : a predicate telling which collidables should be collided with
     * @return a {@link CollisionInfo} if a collision occurred. otherwise null.
     */
    public CollisionInfo getClosestCollision(Line trajectory, Predicate<Collidable> include) {
        return getClosestCollision(trajectory, include, 1, 0);
    }

    /**
     * Get the closest collision for a given trajectory line and a
     *  predicate telling which collidables should be included.
     *  as well as adding multiple 'sensors' that sense whether a collision
     *  occurs at different positions.
     * @param trajectory : the trajectory
     * @param include : a predicate telling which collidables to include
     * @param sensorsPairs : the amount of sensors pairs to have
     * @param sensorsDistance : the distance of the sensors from the normal trajectory
     * @return a {@link CollisionInfo} if a collision occurred. otherwise null.
     */
    public CollisionInfo getClosestCollision(Line trajectory, Predicate<Collidable> include,
            int sensorsPairs, double sensorsDistance) {
        // get additional lines to check intersection using
        Line[] lineSensors = trajectory.equallySpacedParallelLines(sensorsDistance, sensorsPairs);

        // if there are no such lines(since the trajectory is 0, just quit)
        if (lineSensors == null) {
            return null;
        }

        // get the closest intersection to the start of the trajectory
        return getClosestCollision(trajectory.start(), lineSensors, include);
    }

    /**
     * Get the closest intersection to a given point from a given set of trajectories.
     * @param to : the point to find the closest collision to
     * @param trajectories : the trajectories
     * @param include : a predicate telling which collidables should be collided with
     * @return a {@link CollisionInfo} instance or null if no collision occurred
     */
    private CollisionInfo getClosestCollision(Point to, Line[] trajectories, Predicate<Collidable> include) {
        CollisionInfo info = null;
        double minimumDistance = Double.POSITIVE_INFINITY;

        /* check for each trajectory line */
        for (Line trajectory : trajectories) {
            /* go over all the collidable objects in this environment */
            for (Collidable object : this.getCollidables()) {
                // if this object shouldn't be collided with
                if (!include.test(object)) {
                    continue;
                }

                // get the desired collision
                LineIntersection intersection = trajectory
                        .closestIntersectionToStartLine(object.getCollisionRectangle());

                // if there is a collision
                if (intersection != null) {
                    // get the distance from the 'to' position
                    double collisionDistance = intersection.intersectionPoint().distance(to);

                    // if this collision is the closest one
                    if (collisionDistance < minimumDistance) {
                        // set the collision info
                        info = new CollisionInfo(intersection.intersectionPoint(),
                                object, intersection.intersected());
                    }
                }
            }
        }

        return info;
    }

    /**
     * Query whether a point is surrounded by a collidable.
     * @param point : the point to check
     * @return true if it is surrounded. false otherwise.
     */
    public boolean isSurrounded(Point point) {
        for (Collidable c : collidables) {
            if (c.getCollisionRectangle().contains(point)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Remove a collidable from this environment.
     * @param collidable : the collidable to remove
     */
    public void removeCollidable(Collidable collidable) {
        collidables.remove(collidable);
    }
}
