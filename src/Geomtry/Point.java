package Geomtry;

/**
 * The Point class represents a point in a 2D space with x and y coordinates.
 */
public class Point {
    private double x;
    private double y;

    /**
     * Constructor to initialize a point with x and y coordinates.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Calculates the distance between this point and another point.
     *
     * @param other The other point.
     * @return The distance between the two points.
     */
    public double distance(Point other) {
        return Math.sqrt(Math.pow(this.x - other.getX(), 2) + Math.pow(this.y - other.getY(), 2));
    }

    /**
     * Checks if this point is equal to another point.
     *
     * @param other The other point.
     * @return True if the points are equal, false otherwise.
     */
    public boolean equals(Point other) {
        return this.x == other.getX() && this.y == other.getY();
    }

    /**
     * Checks if this point's x-coordinate is within the range of a line segment.
     *
     * @param line The line segment.
     * @return True if the x-coordinate is within the range, false otherwise.
     */
    public boolean rangeX(Line line) {
        double x1 = line.startingPoint().getX();
        double x2 = line.endingPoint().getX();
        return (x1 < this.x && x2 > this.x) || (x1 > this.x && x2 < this.x);
    }

    /**
     * Checks if this point's y-coordinate is within the range of a line segment.
     *
     * @param line The line segment.
     * @return True if the y-coordinate is within the range, false otherwise.
     */
    public boolean rangeY(Line line) {
        double y1 = line.startingPoint().getY();
        double y2 = line.endingPoint().getY();
        return (y1 < this.y && y2 > this.y) || (y1 > this.y && y2 < this.y);
    }

    /**
     * Checks if this point's x-coordinate is within a given range.
     *
     * @param x1 The lower bound of the range.
     * @param x2 The upper bound of the range.
     * @return True if the x-coordinate is within the range, false otherwise.
     */
    public boolean rangeX(double x1, double x2) {
        return x1 < this.x && x2 > this.x;
    }

    /**
     * Checks if this point's y-coordinate is within a given range.
     *
     * @param y1 The lower bound of the range.
     * @param y2 The upper bound of the range.
     * @return True if the y-coordinate is within the range, false otherwise.
     */
    public boolean rangeY(double y1, double y2) {
        return y1 < this.y && y2 > this.y;
    }

    /**
     * Getter for the x-coordinate of the point.
     *
     * @return The x-coordinate of the point.
     */
    public double getX() {
        return x;
    }

    /**
     * Getter for the y-coordinate of the point.
     *
     * @return The y-coordinate of the point.
     */
    public double getY() {
        return y;
    }

    /**
     * Calculates the distance from this point to a line segment defined by two points.
     *
     * @param lineStart The starting point of the line segment.
     * @param lineEnd The ending point of the line segment.
     * @return The distance from this point to the line segment.
     */
    public double distanceToLine(Point lineStart, Point lineEnd) {
        double x0 = this.x;
        double y0 = this.y;
        double x1 = lineStart.getX();
        double y1 = lineStart.getY();
        double x2 = lineEnd.getX();
        double y2 = lineEnd.getY();

        // If the line segment is degenerate (i.e., a single point)
        if (x1 == x2 && y1 == y2) {
            return this.distance(lineStart);
        }

        // Calculate the squared length of the line segment
        double segmentLengthSquared = Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2);

        // Calculate the projection of the point onto the line segment
        double t = ((x0 - x1) * (x2 - x1) + (y0 - y1) * (y2 - y1)) / segmentLengthSquared;

        // Check if the projection falls outside the line segment
        if (t < 0.0) {
            // Closest point is lineStart
            return this.distance(lineStart);
        } else if (t > 1.0) {
            // Closest point is lineEnd
            return this.distance(lineEnd);
        } else {
            // Projection falls within the line segment, calculate the distance to the line
            double numerator = Math.abs((y2 - y1) * x0 - (x2 - x1) * y0 + x2 * y1 - y2 * x1);
            double denominator = Math.sqrt(segmentLengthSquared);
            return numerator / denominator;
        }
    }
}