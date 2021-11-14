package controller;

import model.shapes.generic.GenericShape;

import java.awt.*;
import java.awt.geom.Point2D;

public class ShapeController {
    private GenericShape shape;

    ShapeController(GenericShape shape) {
        this.shape = shape;
    }

    public static ShapeController getController(GenericShape shape){
        return new ShapeController(shape);
    }

    public void controlColor(Color color) {
        this.shape.setColor(color);
    }

    public void controlFill(boolean fill) {
        this.shape.setFill(fill);
    }

    public void controlStartPoint(Point2D point) {
        this.shape.setStartPoint(point);
    }

    public void controlEndPoint(Point2D point) {
        this.shape.setEndPoint(point);
    }
}
