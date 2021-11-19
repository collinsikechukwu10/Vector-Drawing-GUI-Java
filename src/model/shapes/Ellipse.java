package model.shapes;

import model.shapes.generic.ShiftKeyEnabledShape;

import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * Ellipse Shape class.
 *
 * @author 210032207
 */
public class Ellipse extends ShiftKeyEnabledShape {
    /**
     * Constructor initializing with color, fill option, start and end point.
     *
     * @param color      color
     * @param startPoint start point
     * @param endPoint   end point
     * @param fill       fill option
     */
    public Ellipse(Color color, Point2D startPoint, Point2D endPoint, boolean fill) {
        super(color, startPoint, endPoint, fill);
    }
}
