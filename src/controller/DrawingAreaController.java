package controller;

import model.DrawAreaModel;
import model.shapes.generic.GenericShape;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.List;


public class DrawingAreaController extends HistoryController {
    DrawAreaModel model;

    public DrawingAreaController(DrawAreaModel drawAreaModel) {
        this.model = drawAreaModel;
    }

    public boolean isEmpty(){
        return getDrawnShapes().isEmpty();
    }

    public void controlSelectShape(GenericShape shape) {
        this.model.selectShape(shape);
    }

    public GenericShape getSelectedShape() {
        return this.model.getSelectedShape();
    }

    public void controlInitShape(Point2D startPoint) {
        this.model.addShape(startPoint);
    }

    public void controlUndo() {
        undo();
    }

    public void controlRedo() {
        redo();
    }

    public void controlColor(Color color) {
        this.model.setCurrentColor(color);
    }

    public void controlSelectedShapeColor(Color color) {
        this.model.setSelectedShapeColor(color);
    }

    public void controlSelectedShapeFill(boolean fill) {
        this.model.setSelectedShapeFill(fill);

    }

    public void controlSelectedShapeEndPoint(Point2D endpoint, boolean shiftDown) {
        this.model.setSelectedShapeEndPoint(endpoint,shiftDown);
    }

    public void controlSetDrawnShapes(List<GenericShape> shapes) {
        this.model.setDrawnShapes(shapes);
    }

    public void controlClearShapes() {
        this.model.clearDrawArea();
    }

    public void controlFill(boolean shouldFill) {
        this.model.setShouldFill(shouldFill);
    }

    public void controlDeselect() {
        this.model.deSelect();
    }

    public List<GenericShape> getDrawnShapes() {
        return model.getDrawnShapes();
    }

    public void controlSetBlueprint(String blueprintName) {
        model.setShapeToPrepare(blueprintName);
        System.out.println(blueprintName);
    }
    public boolean hasBluePrint(){
        return model.getShapeToPrepare() != null;
    }
}
