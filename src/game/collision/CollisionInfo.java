package game.collision;

import Geomtry.Point;


/**
 * The CollisionInfo class represents information about a collision event.
 * It contains the point of collision and the collidable object involved in the collision.
 */
public class CollisionInfo {
    private Point collisionPoint;
    private Collidable collisionObject;

    /**
     * Constructs a CollisionInfo object with the specified collision point
     * and collidable object.
     * @param collisionPoint the point at which the collision occurs
     * @param collisionObject the collidable object involved in the collision
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        if (collisionPoint == null
                || collisionObject == null) {
            throw new IllegalArgumentException("Collision point and object cannot be null");
        }
        this.collisionPoint = new Point(collisionPoint.getX(), collisionPoint.getY());
        this.collisionObject = collisionObject;
    }
    /**
     * Returns the point at which the collision occurs.
     * @return the collision point
     */
    // the point at which the collision occurs.
    public Point collisionPoint() {
        return new Point(collisionPoint.getX(), collisionPoint.getY());
    }
    /**
     * Returns the collidable object involved in the collision.
     * @return the collidable object
     */
    // the collidable object involved in the collision.
    public Collidable collisionObject() {
        return collisionObject;
    }
    /**
     * Returns the collision rectangle of the collidable object.
     * @return the collision rectangle
     */
    public Collidable getCollisionObject() {
        return null;
    }
}