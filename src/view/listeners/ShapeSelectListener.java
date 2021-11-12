package view.listeners;

import model.DrawAreaModel;
import model.shapes.GenericShape;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class ShapeSelectListener extends MouseMotionAdapter {
    private DrawAreaModel model;

    public ShapeSelectListener(DrawAreaModel model) {
        this.model = model;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        GenericShape shape = this.model.closestInBounds(e.getX(), e.getY());
        if (shape != null) {
            highlightShape(shape);
        } else {
            this.model.getDrawnShapes().forEach(this::dehiglight);
        }
    }


    private void highlightShape(GenericShape genericShape) {

    }

    private void dehiglight(GenericShape genericShape) {

    }

    private void select(GenericShape genericShape, Graphics2D graphics2D) {

    }

    private void deselect(GenericShape genericShape) {

    }
}
