package game;

import Geomtry.Line;
import Geomtry.Point;
import Geomtry.Rectangle;
import game.collision.CollisionInfo;
import game.collision.Collidable;

/**
 * The GameEnvironment class holds a collection of objects a ball can collide with.
 */
public class GameEnvironment {
    private java.util.List<Collidable> collidables;

    /**
     * Constructor to initialize the collidables list.
     */
    public GameEnvironment() {
        this.collidables = new java.util.ArrayList<>();
    }

    /**
     * Adds the given collidable to the environment.
     * @param c the collidable to add
     */
    public void addCollidable(Collidable c) {
        if (c == null) {
            throw new IllegalArgumentException("Collidable cannot be null");
        }
        collidables.add(c);
    }

    /**
     * Returns the information about the closest collision that is going to occur.
     * If no collision will occur, returns null.
     *
     * @param trajectory the path the object is moving along
     * @return the closest collision info or null if none
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        if (trajectory == null) {
            throw new IllegalArgumentException("Trajectory cannot be null");
        }

        Point closestPoint = null;
        Collidable closestCollidable = null;

        for (Collidable c : collidables) {
            Rectangle rect = c.getCollisionRectangle();
            Point intersectionPoint = trajectory.closestIntersectionToStartOfLine(rect);

            if (intersectionPoint != null) {
                if (closestPoint == null
                        || trajectory.startingPoint().distance(intersectionPoint)
                        < trajectory.startingPoint().distance(closestPoint)) {
                    closestPoint = intersectionPoint;
                    closestCollidable = c;
                }
            }
        }

        if (closestPoint != null && closestCollidable != null) {
            return new CollisionInfo(closestPoint, closestCollidable);
        }

        return null;
    }
    /**
     * get the collidables in the environment.
     * @return the collidables
     */
    public java.util.List<Collidable> getCollidables() {
        return this.collidables;
    }

    /**
     * Removes the given collidable from the environment.
     * @param c the collidable to remove
     */
    public void removeCollidable(Collidable c) {
        if (c == null) {
            throw new IllegalArgumentException("Collidable cannot be null");
        }
        collidables.remove(c);
    }
}
