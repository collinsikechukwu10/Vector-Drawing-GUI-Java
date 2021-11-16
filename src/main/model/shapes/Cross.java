package main.model.shapes;

import main.model.shapes.generic.ShiftKeyEnabledShape;

import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * Cross Shape class.
 *
 * @author 210032207
 */
public class Cross extends ShiftKeyEnabledShape {
    /**
     * Constructor initializing with color, fill option, start and end point.
     *
     * @param color      color
     * @param startPoint start point
     * @param endPoint   end point
     * @param fill       fill option
     */
    public Cross(Color color, Point2D startPoint, Point2D endPoint, boolean fill) {
        super(color, startPoint, endPoint, fill);
    }

}
