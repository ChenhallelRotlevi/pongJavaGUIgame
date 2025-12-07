package Geomtry;

/**
 * Rectangle class represents a rectangle in a 2D space.
 * It is defined by its upper left corner, width, and height.
 */
public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;

    /**
     * Constructor for Rectangle.
     *
     * @param upperLeft The upper left corner of the rectangle.
     * @param width     The width of the rectangle.
     * @param height    The height of the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = new Point(upperLeft.getX(), upperLeft.getY());
        this.width = width;
        this.height = height;
    }
    /**
     * Constructor for Rectangle using x and y coordinates.
     *
     * @param x      The x-coordinate of the upper left corner.
     * @param y      The y-coordinate of the upper left corner.
     * @param width  The width of the rectangle.
     * @param height The height of the rectangle.
     */
    public Rectangle(double x, double y, double width, double height) {
        this(new Point(x, y), width, height);
    }
    /**
     * Getter for the upper left corner of the rectangle.
     *
     * @return The upper left corner point.
     */
    public Point getUpperLeft() {
        return new Point(upperLeft.getX(), upperLeft.getY());
    }

    /**
     * Getter for the width of the rectangle.
     *
     * @return The width of the rectangle.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Getter for the height of the rectangle.
     *
     * @return The height of the rectangle.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Get the intersection points of the rectangle edges with a given line.
     * @param line
     * @return a list of the intersection points
     */
    public java.util.List<Point> intersectionPoints(Line line) {
         java.util.List<Point> intersectionPoints = new java.util.ArrayList<>();
         Line[] lines = {
             new Line(upperLeft.getX(), upperLeft.getY(), upperLeft.getX() + width, upperLeft.getY()), // Top
             new Line(upperLeft.getX() + width, upperLeft.getY(),
                     upperLeft.getX() + width, upperLeft.getY() + height), // Right
             new Line(upperLeft.getX() + width,
                     upperLeft.getY() + height, upperLeft.getX(), upperLeft.getY() + height), // Bottom
             new Line(upperLeft.getX(), upperLeft.getY() + height, upperLeft.getX(), upperLeft.getY()) // Left
         };
            for (Line rectLine : lines) {
                Point intersection = line.intersectionWith(rectLine);
                if (intersection != null && !intersectionPoints.contains(intersection)) {
                    intersectionPoints.add(intersection);
                }
            }
            return  intersectionPoints;
    }
    /**
     * Check if a point is inside the rectangle.
     *
     * @param p The point to check.
     * @return true if the point is inside the rectangle, false otherwise.
     */
    public boolean containsPoint(Point p) {
        double x = p.getX();
        double y = p.getY();
        double rx = this.upperLeft.getX();
        double ry = this.upperLeft.getY();
        return x >= rx && x <= rx + width
                && y >= ry && y <= ry + height;
    }

}
