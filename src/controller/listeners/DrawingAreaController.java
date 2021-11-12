package controller.listeners;

import history.HistoryController;
import model.DrawAreaModel;
import model.shapes.GenericShape;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.List;


public class DrawingAreaController extends HistoryController {
    DrawAreaModel model;

    public DrawingAreaController(DrawAreaModel drawAreaModel) {
        this.model = drawAreaModel;
    }


    public void controlSelectShape(GenericShape shape) {
        this.model.selectShape(shape);
    }

    public GenericShape getSelectedShape() {
        return this.model.getSelectedShape();
    }

    public void controlInitShape(Point2D startPoint) {
        this.model.addShape(startPoint);
//        this.model.addShape(shape);
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
        ShapeController.getController(getSelectedShape()).controlColor(color);
    }

    public void controlSelectedShapeFill(boolean fill) {
        ShapeController.getController(getSelectedShape()).controlFill(fill);
    }

    public void controlSelectedShapeEndPoint(Point2D endpoint) {
        ShapeController.getController(getSelectedShape()).controlEndPoint(endpoint);
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
