package model.shapes;

import model.shapes.generic.GenericShape;

import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * Murray Polygon Shape class.
 * Inspired by Jack Cole.
 *
 * @author 210032207
 */
public class MurrayPolygon extends GenericShape {
    /**
     * Constructor initializing with color, fill option, start and end point.
     *
     * @param color      color
     * @param startPoint start point
     * @param endPoint   end point
     * @param fill       fill option
     */
    public MurrayPolygon(Color color, Point2D startPoint, Point2D endPoint, boolean fill) {
        super(color, startPoint, endPoint, fill);
    }

    /**
     * Sets end point.
     *
     * @param endPoint  end point
     * @param shiftDown true if shift key pressed
     */
    @Override
    public void setEndPoint(Point2D endPoint, boolean shiftDown) {
        setEndPoint(endPoint);
    }
}
