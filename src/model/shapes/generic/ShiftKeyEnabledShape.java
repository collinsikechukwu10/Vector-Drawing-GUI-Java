package model.shapes.generic;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class ShiftKeyEnabledShape extends GenericShape {
    public ShiftKeyEnabledShape(Color color, Point2D startPoint, Point2D endPoint, boolean fill) {
        super(color, startPoint, endPoint, fill);
    }

    @Override
    public void setEndPoint(Point2D endPoint, boolean shiftDown) {
        // we need to check if shift is down, if it is, we make the width and height of the shape equal
        if (shiftDown) {
            Point2D startPoint = getStartPoint();
            double width = endPoint.getX() - getStartPoint().getX();
            double height = endPoint.getY() - getStartPoint().getY();
            double minAbsoluteDistance = Math.min(Math.abs(width), Math.abs(height));
            endPoint = new Point2D.Double(
                    startPoint.getX() + (width < 0 ? -minAbsoluteDistance : minAbsoluteDistance),
                    startPoint.getY() + (height < 0 ? -minAbsoluteDistance : minAbsoluteDistance));
        }
        setEndPoint(endPoint);
    }
}
