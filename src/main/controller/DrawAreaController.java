package main.controller;

import main.model.DrawAreaModel;
import main.model.shapes.HistoryStack;
import main.model.shapes.generic.GenericShape;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.List;

/**
 * Draw Area Controller class.
 * This is the main controller that provides access to the Draw Area main model.
 *
 * @author 210032207
 */
public class DrawAreaController {


    private DrawAreaModel model;

    /**
     * Constructor installing model to the controller.
     *
     * @param drawAreaModel model
     */
    public DrawAreaController(DrawAreaModel drawAreaModel) {
        this.model = drawAreaModel;
    }

    /**
     * Gets model.
     *
     * @return model
     */
    public DrawAreaModel getModel() {
        return model;
    }

    /**
     * Checks if no drawn shape exists.
     * From the GUI end, this checks if the drawing area is a blank canvas
     *
     * @return if draw area is empty
     */
    public boolean isEmpty() {
        return getDrawnShapes().size() == 0;
    }

    /**
     * Get the currently selected shape.
     *
     * @return current shape selected
     */
    public GenericShape getSelectedShape() {
        return getModel().getSelectedShape();
    }

    /**
     * Initialize a shape and add it to the main.model to be rendered by a GUI.
     *
     * @param startPoint 2D start point coordinate of the shape.
     */
    public void controlInitShape(Point2D startPoint) {
        getModel().addShape(startPoint);
    }

    /**
     * Performs undo task.
     */
    public void controlUndo() {
        getModel().undo();
    }

    /**
     * Performs Redo task.
     */
    public void controlRedo() {
        getModel().redo();
    }

    /**
     * Controls the color of the main.model.
     *
     * @param color color to set
     */
    public void controlColor(Color color) {
        getModel().setCurrentColor(color);
    }

    /**
     * Controls the color of the currently selected shape in the main.model.
     *
     * @param color color to set
     */
    public void controlSelectedShapeColor(Color color) {
        getModel().setSelectedShapeColor(color);
    }

    /**
     * Controls the fill property of the currently selected shape in the main.model.
     *
     * @param fill fill parameter
     */
    public void controlSelectedShapeFill(boolean fill) {
        getModel().setSelectedShapeFill(fill);

    }

    /**
     * Controls the end point of the currently selected shape in the main.model.
     *
     * @param endpoint  2D end point coordinate of the shape.
     * @param shiftDown if shift key is down
     */
    public void controlSelectedShapeEndPoint(Point2D endpoint, boolean shiftDown) {
        getModel().setSelectedShapeEndPoint(endpoint, shiftDown);
    }

    /**
     * Controls the shapes that have been added to the main.model and drawn.
     *
     * @param shapes shapes added and drawn
     */
    public void controlSetDrawnShapes(List<GenericShape> shapes) {
        getModel().setDrawnShapes(shapes);
    }

    /**
     * Clear all shapes in the main.model so a blank canvas can be rendered.
     */
    public void controlClearShapes() {
        getModel().clearDrawArea();
    }

    /**
     * Controls appending history to the main.model to allow for redo and undo operations.
     */
    public void controlAppendHistory() {
        getModel().appendHistory();
    }

    /**
     * Controls resetting the history.
     */
    public void controlResetHistory() {
        getModel().resetHistory();
    }

    /**
     * Controls the fill property of the main.model.
     * This will be applied to subsequent new shapes.
     *
     * @param shouldFill fill parameter
     */
    public void controlFill(boolean shouldFill) {
        getModel().setShouldFill(shouldFill);
    }

    /**
     * Deselect if there is a currently selected shape.
     */
    public void controlDeselect() {
        getModel().deSelect();
    }

    /**
     * Get the shapes added to the main.model.
     *
     * @return shapes added to the main.model
     */
    public List<GenericShape> getDrawnShapes() {
        return getModel().getDrawnShapes();
    }

    /**
     * Sets the name of the blueprint which future shapes are based on.
     *
     * @param blueprintName name of shape
     */
    public void controlSetBlueprint(String blueprintName) {
        getModel().setShapeToPrepare(blueprintName);
    }

    /**
     * Check if a blueprint has currently been set.
     *
     * @return blueprint exists
     */
    public boolean hasBluePrint() {
        return getModel().getShapeToPrepare() != null;
    }
}
