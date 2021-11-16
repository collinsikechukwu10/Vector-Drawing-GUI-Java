package controller;

import model.DrawAreaModel;
import model.shapes.generic.GenericShape;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Draw Area Controller class.
 * This is the controller that provides access to the Draw Area model.
 *
 * @author 210032207
 */
public class DrawAreaController {
    DrawAreaModel model;

    public DrawAreaController(DrawAreaModel drawAreaModel) {
        this.model = drawAreaModel;
    }

    /**
     * Checks if no drawn shape exists.
     * From the GUI end, this checks if the drawing area is a blank canvas
     *
     * @return if draw area is empty
     */
    public boolean isEmpty() {
        return getDrawnShapes().isEmpty();
    }

    /**
     * Get the currently selected shape.
     *
     * @return current shape selected
     */
    public GenericShape getSelectedShape() {
        return this.model.getSelectedShape();
    }

    /**
     * Initialize a shape and add it to the model to be rendered by a GUI.
     *
     * @param startPoint 2D start point coordinate of the shape.
     */
    public void controlInitShape(Point2D startPoint) {
        this.model.addShape(startPoint);
    }

    /**
     * Performs undo task.
     */
    public void controlUndo() {
        this.model.undo();
    }

    /**
     * Performs Redo task.
     */
    public void controlRedo() {
        this.model.redo();
    }

    /**
     * Controls the color of the model.
     *
     * @param color color to set
     */
    public void controlColor(Color color) {
        this.model.setCurrentColor(color);
    }

    /**
     * Controls the color of the currently selected shape in the model.
     *
     * @param color color to set
     */
    public void controlSelectedShapeColor(Color color) {
        this.model.setSelectedShapeColor(color);
    }

    /**
     * Controls the fill property of the currently selected shape in the model.
     *
     * @param fill fill parameter
     */
    public void controlSelectedShapeFill(boolean fill) {
        this.model.setSelectedShapeFill(fill);

    }

    /**
     * Controls the end point of the currently selected shape in the model.
     *
     * @param endpoint  2D end point coordinate of the shape.
     * @param shiftDown if shift key is down
     */
    public void controlSelectedShapeEndPoint(Point2D endpoint, boolean shiftDown) {
        this.model.setSelectedShapeEndPoint(endpoint, shiftDown);
    }

    /**
     * Controls the shapes that have been added to the model and drawn.
     *
     * @param shapes shapes added and drawn
     */
    public void controlSetDrawnShapes(List<GenericShape> shapes) {
        this.model.setDrawnShapes(shapes);
    }

    /**
     * Clear all shapes in the model so a blank canvas can be rendered.
     */
    public void controlClearShapes() {
        this.model.clearDrawArea();
    }

    /**
     * Controls appending history to the model to allow for redo and undo operations.
     */
    public void controlAppendHistory() {
        this.model.appendHistory();
    }

    /**
     * Controls the fill property of the model.
     * This will be applied to subsequent new shapes.
     *
     * @param shouldFill fill parameter
     */
    public void controlFill(boolean shouldFill) {
        this.model.setShouldFill(shouldFill);
    }

    /**
     * Deselect if there is a currently selected shape.
     */
    public void controlDeselect() {
        this.model.deSelect();
    }

    /**
     * Get the shapes added to the model.
     *
     * @return shapes added to the model
     */
    public List<GenericShape> getDrawnShapes() {
        return model.getDrawnShapes();
    }

    /**
     * Sets the name of the blueprint which future shapes are based on.
     *
     * @param blueprintName name of shape
     */
    public void controlSetBlueprint(String blueprintName) {
        model.setShapeToPrepare(blueprintName);
    }

    /**
     * Check if a blueprint has currently been set.
     *
     * @return blueprint exists
     */
    public boolean hasBluePrint() {
        return model.getShapeToPrepare() != null;
    }
}
