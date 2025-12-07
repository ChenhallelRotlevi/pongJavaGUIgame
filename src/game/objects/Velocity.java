package game.objects;


import Geomtry.Point;


/**
 * Velocity specifies the change in position on the `x` and the `y` axes.
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     * Constructor to initialize velocity with dx and dy values.
     *
     * @param dx The change in the x-axis.
     * @param dy The change in the y-axis.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }


    /**
     * Creates a velocity instance from an angle and speed.
     *
     * @param angle The angle of movement in radians.
     * @param speed The speed of movement.
     * @return A new velocity instance with calculated dx and dy.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double radians = Math.toRadians(angle);
        double dx = speed * Math.sin(radians);   // X is sin for standard game orientation
        double dy = -speed * Math.cos(radians);  // Y is -cos because 0° should be "up"
        return new Velocity(dx, dy);
    }


    /**
     * Getter for the dx value.
     *
     * @return The change in the x-axis.
     */
    public double getDx() {
        return dx;
    }

    /**
     * Getter for the dy value.
     *
     * @return The change in the y-axis.
     */
    public double getDy() {
        return dy;
    }

    /**
     * Applies the velocity to a given point and returns a new point.
     *
     * @param p The point to which velocity is applied.
     * @return A new point with updated x and y coordinates.
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + dx, p.getY() + dy);
    }

    /**
     * Calculates and returns the speed based on dx and dy.
     *
     * @return The speed of the velocity.
     */
    public double getSpeed() {
        return Math.sqrt(dx * dx + dy * dy);
    }
}
