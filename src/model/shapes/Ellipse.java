package model.shapes;

import java.awt.*;
import java.awt.geom.Point2D;

public class Ellipse extends ShiftKeyEnabledShape{
    public Ellipse(Color color, Point2D startPoint, Point2D endPoint, boolean fill) {
        super(color, startPoint, endPoint, fill);
    }
}
