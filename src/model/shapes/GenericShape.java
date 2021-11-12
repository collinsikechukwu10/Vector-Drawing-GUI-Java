package model.shapes;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.Serializable;

public abstract class GenericShape implements Serializable {
    private Color color;
    private boolean fill;
    private Point2D startPoint;
    private Point2D endPoint;

    GenericShape(Color color, Point2D startPoint, Point2D endPoint, boolean fill) {
        this.color = color;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.fill = fill;
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

    public abstract void setEndPoint(Point2D endPoint, boolean shiftDown);

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
