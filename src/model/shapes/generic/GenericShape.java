package model.shapes.generic;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

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

    public GenericShape(Color color, Point2D startPoint, Point2D endPoint, boolean fill) {
        this.color = color;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.fill = fill;
    }

    public GenericShape copy() {
        try {
            return this.getClass().getConstructor(Color.class, Point2D.class, Point2D.class, boolean.class).newInstance(
                    getColor(), getStartPoint(), getEndPoint(), isFill());
        } catch (Exception e) {
            return null;
        }
    }


    public Point2D getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point2D startPoint) {
        this.startPoint = startPoint;
    }

    public Point2D getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point2D endPoint) {
        this.endPoint = endPoint;
    }

    /**
     * Overide this method to allow for shift key customized actions
     *
     * @param endPoint
     * @param shiftDown
     */
    public void setEndPoint(Point2D endPoint, boolean shiftDown) {
        setEndPoint(endPoint);
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }

    public boolean isFill() {
        return fill;
    }

    public boolean pointIsInShapeBoundaries(int x, int y) {
        Point2D startPoint = getStartPoint();
        Point2D endPoint = getEndPoint();
        return x > startPoint.getX() && x < endPoint.getX() && y > startPoint.getY() && y < endPoint.getY();
    }


}
