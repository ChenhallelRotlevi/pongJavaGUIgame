package game.collision;
import Geomtry.Point;
import Geomtry.Rectangle;
import game.objects.Ball;
import game.objects.Velocity;

/**
 * Collidable interface for objects that can be collided with.
 * This interface defines the methods required to handle collision detection
 * and response in a 2D space.
 */
public interface Collidable {
    /**
     * Return the "collision shape" of the object.
     * @return the collision rectangle of the object`
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint
     * with a given velocity.
     * @param hitter the ball that is colliding with this object
     * @param collisionPoint the point of collision
     * @param currentVelocity the current velocity before collision
     * @return the updated velocity after the collision
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}

