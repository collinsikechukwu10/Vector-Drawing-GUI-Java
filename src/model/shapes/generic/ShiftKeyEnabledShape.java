package model.shapes.generic;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Shift Key Enabled Shape class.
 * Used for shapes that require locking aspect ratio.
 *
 * @author 210032207
 */
public abstract class ShiftKeyEnabledShape extends GenericShape {
    /**
     * Constructor initializing with color, fill option, start and end point.
     *
     * @param color      color
     * @param startPoint start point
     * @param endPoint   end point
     * @param fill       fill option
     */
    public ShiftKeyEnabledShape(Color color, Point2D startPoint, Point2D endPoint, boolean fill) {
        super(color, startPoint, endPoint, fill);
    }

    /**
     * Sets end point.
     * Implements shift key functionality to look aspect ratio.
     *
     * @param endPoint  end point
     * @param shiftDown shift key down
     */
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
