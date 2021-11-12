package model.shapes;

import java.awt.*;
import java.awt.geom.Point2D;

public class Rectangle extends GenericShape{
    public Rectangle(Color color, Point2D startPoint, Point2D endPoint, boolean fill) {
        super(color, startPoint, endPoint, fill);
    }
}
