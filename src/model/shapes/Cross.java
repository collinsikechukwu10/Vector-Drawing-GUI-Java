package model.shapes;

import model.shapes.generic.ShiftKeyEnabledShape;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Cross Shape class.
 *
 * @author 210032207
 */
public class Cross extends ShiftKeyEnabledShape {
    /**
     * Constructor initializing cross shape
     * @param color Color to initialize shape with.
     * @param startPoint
     * @param endPoint
     * @param fill
     */
    public Cross(Color color, Point2D startPoint, Point2D endPoint, boolean fill) {
        super(color, startPoint, endPoint, fill);
    }

}
