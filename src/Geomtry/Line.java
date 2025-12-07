package Geomtry;

/**
 * The Line class represents a line segment in a 2D space defined by two points.
 */
public class Line {
    private Point a;
    private Point b;

    /**
     * Constructor that creates a line segment using two points.
     *
     * @param a The starting point of the line.
     * @param b The ending point of the line.
     */
    public Line(Point a, Point b) {
        this.a = new Point(a.getX(), a.getY());
        this.b = new Point(b.getX(), b.getY());
    }

    /**
     * Constructor that creates a line segment using coordinates.
     *
     * @param x1 The x-coordinate of the starting point.
     * @param y1 The y-coordinate of the starting point.
     * @param x2 The x-coordinate of the ending point.
     * @param y2 The y-coordinate of the ending point.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.a = new Point(x1, y1);
        this.b = new Point(x2, y2);
    }

    /**
     * Calculates the length of the line segment.
     *
     * @return The length of the line.
     */
    public double length() {
        return a.distance(b);
    }

    /**
     * Calculates the middle point of the line segment.
     *
     * @return The middle point of the line.
     */
    public Point middle() {
        return new Point((a.getX() + b.getX()) / 2, (a.getY() + b.getY()) / 2);
    }

    /**
     * Gets the starting point of the line.
     *
     * @return The starting point.
     */
    public Point startingPoint() {
        return a;
    }

    /**
     * Gets the ending point of the line.
     *
     * @return The ending point.
     */
    public Point endingPoint() {
        return b;
    }

    /**
     * Checks if this line intersects with another line.
     *
     * @param line The other line.
     * @return True if the lines intersect, false otherwise.
     */
    public boolean isIntersecting(Line line) {
        if (this.isVertical() && line.isVertical()) {
            return this.a.getX() == line.a.getX()
                    && (this.a.rangeY(line)
                    || this.b.rangeY(line)
                    || line.a.rangeY(this)
                    || line.b.rangeY(this));
        }
        if (this.isParallel(line)) {
            return this.isOverlapping(line)
                    || this.a.equals(line.b)
                    || this.b.equals(line.a);
        }
        return intersection(line) != null;
    }

    /**
     * Checks if the line is vertical.
     *
     * @return True if the line is vertical, false otherwise.
     */
    private boolean isVertical() {
        return a.getX() == b.getX();
    }

    /**
     * Checks if this line is parallel to another line.
     *
     * @param line The other line.
     * @return True if the lines are parallel, false otherwise.
     */
    private boolean isParallel(Line line) {
        return (b.getY() - a.getY()) * (line.b.getX() - line.a.getX())
                == (line.b.getY() - line.a.getY()) * (b.getX() - a.getX());
    }

    /**
     * Finds the intersection point of this line with another line.
     *
     * @param line The other line.
     * @return The intersection point or null if no intersection exists.
     */
    private Point intersection(Line line) {
        if (this.isParallel(line)) {
            return (this.isOverlapping(line)) ? null : null;
        }

        if (this.isVertical()) {
            double m2 = (line.b.getY() - line.a.getY()) / (line.b.getX() - line.a.getX());
            double b2 = line.a.getY() - m2 * line.a.getX();
            double x = this.a.getX();
            double y = m2 * x + b2;
            Point intersectionPoint = new Point(x, y);
            return (isPointOnSegment(intersectionPoint) && line.isPointOnSegment(intersectionPoint))
                    ? intersectionPoint : null;
        }

        if (line.isVertical()) {
            double m1 = (b.getY() - a.getY()) / (b.getX() - a.getX());
            double b1 = a.getY() - m1 * a.getX();
            double x = line.a.getX();
            double y = m1 * x + b1;
            Point intersectionPoint = new Point(x, y);
            return (isPointOnSegment(intersectionPoint) && line.isPointOnSegment(intersectionPoint))
                    ? intersectionPoint : null;
        }

        double a1 = b.getY() - a.getY();
        double b1 = a.getX() - b.getX();
        double c1 = a1 * a.getX() + b1 * a.getY();

        double a2 = line.b.getY() - line.a.getY();
        double b2 = line.a.getX() - line.b.getX();
        double c2 = a2 * line.a.getX() + b2 * line.a.getY();

        double det = a1 * b2 - a2 * b1;
        if (det == 0) {
            return null;
        }

        double x = Math.round((b2 * c1 - b1 * c2) / det * 1e9) / 1e9;
        double y = Math.round((a1 * c2 - a2 * c1) / det * 1e9) / 1e9;

        Point intersectionPoint = new Point(x, y);
        return (isPointOnSegment(intersectionPoint) && line.isPointOnSegment(intersectionPoint))
                ? intersectionPoint : null;
    }

    /**
     * Checks if a point is on the line segment.
     *
     * @param p The point to check.
     * @return True if the point is on the segment, false otherwise.
     */
    private boolean isPointOnSegment(Point p) {
        return (Math.min(a.getX(), b.getX()) <= p.getX() && p.getX() <= Math.max(a.getX(), b.getX()))
                && (Math.min(a.getY(), b.getY()) <= p.getY() && p.getY() <= Math.max(a.getY(), b.getY()));
    }

    /**
     * Checks if this line overlaps with another line.
     *
     * @param line The other line.
     * @return True if the lines overlap, false otherwise.
     */
    private boolean isOverlapping(Line line) {
        return this.a.rangeX(line)
                || this.b.rangeX(line)
                || line.a.rangeX(this)
                || line.b.rangeX(this);
    }

    /**
     * Finds the intersection point with another line.
     *
     * @param other The other line.
     * @return The intersection point or null if no intersection exists.
     */
    public Point intersectionWith(Line other) {
        return intersection(other);
    }

    /**
     * Checks if this line intersects with two other lines.
     *
     * @param line1 The first line.
     * @param line2 The second line.
     * @return True if this line intersects with both lines, false otherwise.
     */
    public boolean isIntersecting(Line line1, Line line2) {
        return isIntersecting(line1) && isIntersecting(line2);
    }

    /**
     * Checks if this line is equal to another line.
     *
     * @param line The other line.
     * @return True if the lines are equal, false otherwise.
     */
    public boolean equals(Line line) {
        return (a.equals(line.startingPoint()) && b.equals(line.endingPoint()))
                || (a.equals(line.endingPoint()) && b.equals(line.startingPoint()));
    }

    /**
     * Finds the closest intersection point to the start of the line with a rectangle.
     *
     * @param rect The rectangle to check for intersections.
     * @return The closest intersection point or null if no intersection exists.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        java.util.List<Point> intersectionPoints = rect.intersectionPoints(this);
        if (intersectionPoints.isEmpty()) {
            return null;
        }
        Point closestPoint = intersectionPoints.get(0);
        double minDistance = this.a.distance(closestPoint);
        for (Point point : intersectionPoints) {
            double distance = this.a.distance(point);
            if (distance < minDistance) {
                closestPoint = point;
                minDistance = distance;
            }
        }
        return closestPoint;
    }

}