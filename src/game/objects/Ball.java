package game.objects;

import game.collision.Collidable;
import game.collision.CollisionInfo;
import game.collision.Sprite;
import biuoop.DrawSurface;
import Geomtry.Line;
import Geomtry.Point;
import Geomtry.Rectangle;
import game.Game;
import game.GameEnvironment;

/**
 * Represents a Ball object with a center, radius, color, and velocity.
 */
public class Ball implements Sprite {
    private Point center;
    private int radius;
    private java.awt.Color color;
    private Velocity v;
    private GameEnvironment ge;

    /**
     * Constructor using a Point.
     * @param center the center point
     * @param r radius of the ball
     * @param color color of the ball
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = new Point(center.getX(), center.getY());
        this.radius = r;
        this.color = color;
    }

    /**
     * Constructor using x and y coordinates.
     * @param x x-coordinate
     * @param y y-coordinate
     * @param r radius of the ball
     * @param color color of the ball
     */
    public Ball(double x, double y, int r, java.awt.Color color) {
        this.center = new Point(x, y);
        this.radius = r;
        this.color = color;
    }

    /**
     * @return the x-coordinate of the ball's center
     */
    public int getX() {
        return (int) center.getX();
    }

    /**
     * @return the y-coordinate of the ball's center
     */
    public int getY() {
        return (int) center.getY();
    }

    /**
     * @return the ball's radius
     */
    public int getSize() {
        return radius;
    }

    /**
     * @return the ball's color
     */
    public java.awt.Color getColor() {
        return color;
    }

    /**
     * Draws the ball on the given surface.
     * @param surface the DrawSurface to draw on
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(color);
        surface.fillCircle((int) this.center.getX(), (int) this.center.getY(), this.radius);
    }

    /**
     * Sets the ball's velocity using a velocity object.
     * @param v the velocity to set
     */
    public void setVelocity(Velocity v) {
        this.v = new Velocity(v.getDx(), v.getDy());
    }

    /**
     * Sets the ball's velocity using dx and dy.
     * @param dx change in x
     * @param dy change in y
     */
    public void setVelocity(double dx, double dy) {
        this.v = new Velocity(dx, dy);
    }

    /**
     * @return the ball's velocity.
     */
    public Velocity getVelocity() {
        return v;
    }

    /**
     * Moves the ball one step based on its velocity.
     */
    public void moveOneStep() {
        Line trajectory = calculateTrajectory();
        CollisionInfo collision = ge.getClosestCollision(trajectory);

        if (collision == null) {
            this.center = this.v.applyToPoint(this.center);
        } else {
            moveToBeforeCollision(collision);
            this.v = collision.collisionObject().hit(this, collision.collisionPoint(), this.v);
        }

        handleFailSafeOverlap();
    }
    /**
     * Handles the case where the ball is overlapping with the frame.
     * @param collision the collision information
     */
    private void moveToBeforeCollision(CollisionInfo collision) {
        Point collisionPoint = collision.collisionPoint();
        double dx = this.v.getDx();
        double dy = this.v.getDy();
        double fudge = 0.1;

        double newX = collisionPoint.getX() - dx / this.v.getSpeed() * fudge;
        double newY = collisionPoint.getY() - dy / this.v.getSpeed() * fudge;
        this.center = new Point(newX, newY);
    }
    /**
     * Handles the case where the ball is overlapping with the frame.
     */
    private void handleFailSafeOverlap() {
        for (Collidable c : this.ge.getCollidables()) {
            Rectangle r = c.getCollisionRectangle();
            if (r.containsPoint(this.center)) {
                resolveOverlapWith(r);
            }
        }
    }
    /**
     * Resolves the overlap between the ball and a rectangle.
     * @param r the rectangle to resolve overlap with
     */
    private void resolveOverlapWith(Rectangle r) {
        double ballX = this.center.getX();
        double ballY = this.center.getY();
        double left = r.getUpperLeft().getX();
        double right = left + r.getWidth();
        double top = r.getUpperLeft().getY();
        double bottom = top + r.getHeight();

        double dxLeft = Math.abs(ballX - left);
        double dxRight = Math.abs(ballX - right);
        double dyTop = Math.abs(ballY - top);
        double dyBottom = Math.abs(ballY - bottom);

        boolean isSideHit = Math.min(dxLeft, dxRight) < Math.min(dyTop, dyBottom);

        if (isSideHit) {
            // Horizontal (side) correction
            if (dxLeft < dxRight) {
                this.center = new Point(left - this.radius, ballY);
                this.v = new Velocity(-Math.abs(this.v.getDx()), this.v.getDy());
            } else {
                this.center = new Point(right + this.radius, ballY);
                this.v = new Velocity(Math.abs(this.v.getDx()), this.v.getDy());
            }
        } else {
            // Vertical (top/bottom) correction
            if (dyTop < dyBottom) {
                this.center = new Point(ballX, top - this.radius);
                this.v = new Velocity(this.v.getDx(), -Math.abs(this.v.getDy()));
            } else {
                this.center = new Point(ballX, bottom + this.radius);
                this.v = new Velocity(this.v.getDx(), Math.abs(this.v.getDy()));
            }
        }
    }
    /**
     * @return the angle of the center point from origin (radians)
     */
    public double getAngle() {
        return Math.atan2(this.center.getY(), this.center.getX());
    }

    /**
     * @return the center point of the ball
     */
    public Point getCenter() {
        return this.center;
    }

    /**
     * Determines the direction to change the ball's velocity based on collisions with a frame.
     * @param p1 the first boundary point of the frame
     * @param p2 the second boundary point of the frame
     * @param p3 the third boundary point of the frame
     * @return a string representing the direction to change ("x", "y", "both", "none")
     */
    public String changeBallDirection(double p1, double p2, double p3) {
        Ball temp = new Ball(this.center, this.radius, this.color);
        temp.setVelocity(this.v);
        temp.moveOneStep();
        Point tempCenter = temp.getCenter();
        boolean bY = false;
        boolean bX = false;
        Line dx = new Line(0, 0, 0, 0);
        Line dy = new Line(0, 0, 0, 0);
        if ((tempCenter.distanceToLine(new Point(p1, p1), new Point(p2, p1)) <= this.radius)
                || tempCenter.distanceToLine(new Point(p1, p3), new Point(p2, p3)) <= this.radius) {
            bY = true;
            if ((tempCenter.distanceToLine(new Point(p1, p1), new Point(p2, p1))
                    < tempCenter.distanceToLine(new Point(p1, p3), new Point(p2, p3)))) {
                dy = new Line(new Point(p1, p1), new Point(p2, p1));
            } else {
                dy = new Line(new Point(p1, p3), new Point(p2, p3));
            }
        }
        if (tempCenter.distanceToLine(new Point(p1, p1), new Point(p1, p3)) <= this.radius
                || tempCenter.distanceToLine(new Point(p2, p1), new Point(p2, p3)) <= this.radius) {
            bX = true;
            if ((tempCenter.distanceToLine(new Point(p1, p1), new Point(p1, p3))
                    < tempCenter.distanceToLine(new Point(p2, p1), new Point(p2, p3)))) {
                dx = new Line(new Point(p1, p1), new Point(p1, p3));
            } else {
                dx = new Line(new Point(p2, p1), new Point(p2, p3));
            }
        }
        if (bY && bX) {
            Point p = this.center;
            if (p.rangeX(dy) && !p.rangeY(dx)) {
                return "y";
            } else if (p.rangeY(dx) && !p.rangeX(dy)) {
                return "x";
            } else {
                return "both";
            }
        } else if (bY) {
            return "y";
        } else if (bX) {
            return "x";
        }
        return "none";
    }

    /**
     * Updates the ball's velocity based on frame boundaries.
     * @param p1 first point of frame
     * @param p2 second point of frame
     * @param p3 third point of frame
     */
    public void updateVelocity(double p1, double p2, double p3) {
        Ball temp = new Ball(this.center, this.radius, this.color);
        temp.setVelocity(this.v);
        temp.moveOneStep();
        Point tempCenter = temp.getCenter();
        boolean bY = false;
        boolean bX = false;
        Line dx = new Line(0, 0, 0, 0);
        Line dy = new Line(0, 0, 0, 0);
        if ((tempCenter.distanceToLine(new Point(p1, p1), new Point(p2, p1)) <= this.radius)
                || tempCenter.distanceToLine(new Point(p1, p3), new Point(p2, p3)) <= this.radius) {
            bY = true;
            if ((tempCenter.distanceToLine(new Point(p1, p1), new Point(p2, p1))
                    < tempCenter.distanceToLine(new Point(p1, p3), new Point(p2, p3)))) {
                dy = new Line(new Point(p1, p1), new Point(p2, p1));
            } else {
                dy = new Line(new Point(p1, p3), new Point(p2, p3));
            }
        }
        if (tempCenter.distanceToLine(new Point(p1, p1), new Point(p1, p3)) <= this.radius
                || tempCenter.distanceToLine(new Point(p2, p1), new Point(p2, p3)) <= this.radius) {
            bX = true;
            if ((tempCenter.distanceToLine(new Point(p1, p1), new Point(p1, p3))
                    < tempCenter.distanceToLine(new Point(p2, p1), new Point(p2, p3)))) {
                dx = new Line(new Point(p1, p1), new Point(p1, p3));
            } else {
                dx = new Line(new Point(p2, p1), new Point(p2, p3));
            }
        }
        if (bY && bX) {
            Point p = this.center;
            if (p.rangeX(dy) && !p.rangeY(dx)) {
                bX = false;
            } else if (p.rangeY(dx) && !p.rangeX(dy)) {
                bY = false;
            }
        }
        if (bY) {
            this.setVelocity(this.v.getDx(), -this.v.getDy());
        }
        if (bX) {
            this.setVelocity(-this.v.getDx(), this.v.getDy());
        }
    }

    /**
     * Calculates the trajectory of the ball.
     * @return the line representing the trajectory
     */
    private Line calculateTrajectory() {
        Point start = new Point(this.center.getX(), this.center.getY());
        Point end = new Point(this.center.getX() + 2 * this.v.getDx(),
                this.center.getY() + 2 * this.v.getDy());
        return new Line(start, end);
    }

    /**
     * Notifies the ball that time has passed.
     */
    public void timePassed() {
        this.moveOneStep();
    }

    /**
     * Sets the game environment.
     * @param ge the game environment
     */
    public void setGameEnvironment(GameEnvironment ge) {
        if (ge == null) {
            throw new IllegalArgumentException("GameEnvironment cannot be null");
        }
        this.ge = ge;
    }

    /**
     * Adds the ball to the game.
     * @param g the game instance
     */
    public void addToGame(Game g) {
        if (g == null) {
            throw new IllegalArgumentException("Game cannot be null");
        }
        g.addSprite(this);
        setGameEnvironment(g.getEnvironment());
    }

    /**
     * Get color.
     * @return the color of the ball
     */
    public java.awt.Color getColorBall() {
        return color;
    }

    /**
     * Set color.
     * @param color the color to set
     */
    public void setColor(java.awt.Color color) {
        this.color = color;
    }

    /**
     * Remove the ball from the game.
     * @param g the game instance
     */
    public void removeFromGame(Game g) {
        if (g == null) {
            throw new IllegalArgumentException("Game cannot be null");
        }
        g.removeSprite(this);
    }
}
