package model.shapes;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class ShiftKeyEnabledShape extends GenericShape {
    ShiftKeyEnabledShape(Color color, Point2D startPoint, Point2D endPoint, boolean fill) {
        super(color, startPoint, endPoint, fill);
    }

    @Override
    public void setEndPoint(Point2D endPoint, boolean shiftDown) {
        // we need to check if shift is down, if it is, we make a square instead
        if (shiftDown) {
            double absoluteWidth = Math.abs(getStartPoint().getX() - endPoint.getX());
            double absoluteHeight = Math.abs(getStartPoint().getY() - endPoint.getY());
            double minDistance = Math.min(absoluteHeight, absoluteWidth);

            endPoint = new Point2D.Double(getStartPoint().getX() + minDistance, getStartPoint().getY() + minDistance);
        }
        setEndPoint(endPoint);
    }
}
