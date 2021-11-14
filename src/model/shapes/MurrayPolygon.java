package model.shapes;

import model.shapes.generic.GenericShape;

import java.awt.*;
import java.awt.geom.Point2D;

public class MurrayPolygon extends GenericShape {
    public MurrayPolygon(Color color, Point2D startPoint, Point2D endPoint, boolean fill) {
        super(color, startPoint, endPoint, fill);
    }

    @Override
    public void setEndPoint(Point2D endPoint, boolean shiftDown) {
        setEndPoint(endPoint);
    }
}
