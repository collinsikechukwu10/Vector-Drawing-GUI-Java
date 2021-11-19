package model.shapes.generic;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 * Generic shape class.
 * This is the base class for all implemented shapes to be drawn.
 *
 * @author 210032207
 */
public abstract class GenericShape implements Serializable {
    private Color color;
    private boolean fill;
    private Point2D startPoint;
    private Point2D endPoint;

    /**
     * Constructor initializing with color, fill option, start and end point.
     *
     * @param color      color
     * @param startPoint start point
     * @param endPoint   end point
     * @param fill       fill option
     */
    public GenericShape(Color color, Point2D startPoint, Point2D endPoint, boolean fill) {
        this.color = color;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.fill = fill;
    }

    /**
     * Deep copy of the shape.
     *
     * @return copy of the shape
     */
    public GenericShape copy() {
        try {
            return this.getClass().getConstructor(Color.class, Point2D.class, Point2D.class, boolean.class).newInstance(
                    getColor(), getStartPoint(), getEndPoint(), isFill());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Gets start point of the shape.
     *
     * @return start point
     */
    public Point2D getStartPoint() {
        return startPoint;
    }

    /**
     * Gets end point of the shape.
     *
     * @return end point
     */
    public Point2D getEndPoint() {
        return endPoint;
    }

    /**
     * Sets end point of the shape.
     *
     * @param endPoint end point
     */
    public void setEndPoint(Point2D endPoint) {
        this.endPoint = endPoint;
    }

    /**
     * Overide this method to allow for shift key customized actions.
     *
     * @param endPoint  end point
     * @param shiftDown shift key down
     */
    public void setEndPoint(Point2D endPoint, boolean shiftDown) {
        setEndPoint(endPoint);
    }

    /**
     * Gets the color of the shape.
     *
     * @return shape color
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Sets the color of the shape.
     *
     * @param color color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Sets the fill option of the shape.
     *
     * @param fill should fill
     */
    public void setFill(boolean fill) {
        this.fill = fill;
    }

    /**
     * Gets the fill option set in the shape.
     *
     * @return fill option
     */
    public boolean isFill() {
        return fill;
    }

    /**
     * Check if a certain point is in the shapes boundaries.
     *
     * @param x x coordinate of the point
     * @param y y coordinate of the point
     * @return true if point is within the boundary of the shape
     */
    public boolean pointIsInShapeBoundaries(int x, int y) {
        Point2D startPoint = getStartPoint();
        Point2D endPoint = getEndPoint();
        return x > startPoint.getX() && x < endPoint.getX() && y > startPoint.getY() && y < endPoint.getY();
    }

    /**
     * String representation of shape instance.
     *
     * @return string representation
     */
    @Override
    public String toString() {
        return String.format("%s{color=%s, fill=%s, start=%s, end=%s,}", this.getClass().getSimpleName(), color, fill, startPoint, endPoint);
    }
}
