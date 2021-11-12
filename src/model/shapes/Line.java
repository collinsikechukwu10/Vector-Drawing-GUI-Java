package model.shapes;

import java.awt.*;
import java.awt.geom.Point2D;

public class Line extends GenericShape {


    public Line(Color color, Point2D startPoint, Point2D endPoint, boolean fill) {
        super(color, startPoint, endPoint, fill);
    }

    @Override
    public void setEndPoint(Point2D endPoint, boolean shiftDown) {
        setEndPoint(endPoint);
    }
}
