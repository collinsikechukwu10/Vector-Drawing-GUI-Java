package main.model;

import main.config.ApplicationConfig;
import main.model.shapes.*;
import main.model.shapes.Rectangle;
import main.model.shapes.generic.GenericShape;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Draw Area Model.
 * This is the main model that performs creation, updating and deletion of shapes.
 * It also saves states to allow for undo and redo functionality.
 *
 * @author 210032207
 */
public class DrawAreaModel {
    private Color currentColor;
    private boolean shouldFill;
    private PropertyChangeSupport notifier;  // tracks and notifies listeners
    private List<GenericShape> drawnShapes = new ArrayList<>();
    private int selectedShapeIndex = -1;
    private final Map<String, Class<? extends GenericShape>> blueprints = new HashMap<>();
    private String shapeToPrepare = null;
    private HistoryStack<List<GenericShape>> history;

    /**
     * Constructor initializing main model.
     */
    public DrawAreaModel() {

        // les set a default color
        currentColor = ApplicationConfig.DEFAULT_SHAPE_COLOR;
        this.shouldFill = false;
        // prepare blueprints
        List.of(Line.class, Rectangle.class, Ellipse.class, Cross.class, MurrayPolygon.class).forEach(aClass -> {
            blueprints.put(aClass.getSimpleName().toLowerCase(), aClass);
        });
        // initialize history
        history = new HistoryStack<>();
        history.push(getDrawnShapes());
        this.notifier = new PropertyChangeSupport(this);
    }

    /**
     * Register a listener to be notified of any changes to this main model.
     *
     * @param listener Listener class.
     */
    public void addListener(PropertyChangeListener listener) {
        notifier.addPropertyChangeListener(listener);
    }

    /**
     * Broadcast change to all listeners.
     *
     * @param property property name
     * @param oldValue old value of property
     * @param newValue new value of property
     */
    private void update(String property, Object oldValue, Object newValue) {
        notifier.firePropertyChange(property, oldValue, newValue);

    }

    /**
     * Get history object.
     *
     * @return history object
     */
    public HistoryStack<List<GenericShape>> getHistory() {
        return history;
    }

    public void setHistory(HistoryStack<List<GenericShape>> history) {
        this.history = history;
    }

    /**
     * Performs undo operation.
     */
    public void undo() {
        getHistory().undo();
        if (getHistory().hasState()) {
            setDrawnShapes(new ArrayList<>(getHistory().getState()));
        }
    }

    /**
     * Performs redo operation.
     */
    public void redo() {
        getHistory().redo();
        if (getHistory().hasState()) {
            setDrawnShapes(new ArrayList<>(getHistory().getState()));
        }
    }

    /**
     * Initializes a shape to the main model.
     *
     * @param startPoint startpoint edge of the shape
     */
    public void addShape(Point2D startPoint) {
        GenericShape shape;
        if (getShapeToPrepare() != null) {
            Class<? extends GenericShape> bluePrint = blueprints.get(getShapeToPrepare().toLowerCase());
            System.out.println(bluePrint);
            try {
                // initialize shape with both the start and end point at the same location
                shape = bluePrint.getConstructor(Color.class, Point2D.class, Point2D.class, boolean.class).newInstance(
                        getCurrentColor(), startPoint, startPoint, isShouldFill()
                );
                List<GenericShape> previousDrawnShapes = getDrawnShapes();
                this.drawnShapes.add(shape);
                selectedShapeIndex = this.drawnShapes.size() - 1;
                update("drawnShapes", previousDrawnShapes, this.drawnShapes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * Gets shape blueprint  to prepare.
     *
     * @return shape blueprint
     */
    public String getShapeToPrepare() {
        return shapeToPrepare;
    }

    /**
     * Sets shape blueprint.
     *
     * @param shapeToPrepare shape blueprint
     */
    public void setShapeToPrepare(String shapeToPrepare) {
        this.shapeToPrepare = shapeToPrepare;
    }

    /**
     * Clears all shapes in the main model.
     */
    public void clearDrawArea() {
        List<GenericShape> previousDrawnShapes = getDrawnShapes();
        this.drawnShapes = new ArrayList<>();
        this.selectedShapeIndex = -1;
        // we want to repaint after this soooooo
        appendHistory();
        update("drawnShapes", previousDrawnShapes, this.drawnShapes);
    }

    /**
     * Appends history.
     */
    public void appendHistory() {
        history.push(getDrawnShapes().stream().map(GenericShape::copy).collect(Collectors.toList()));
    }

    /**
     * Resets history.
     */
    public void resetHistory() {
        history = new HistoryStack<>();
        history.push(new ArrayList<>());
    }

    /**
     * Sets drawn shapes in the model.
     *
     * @param drawnShapes list of drawn shapes
     */
    public void setDrawnShapes(List<GenericShape> drawnShapes) {
        List<GenericShape> previousDrawnShapes = getDrawnShapes();
        this.drawnShapes = drawnShapes;
        update("drawnShapes", previousDrawnShapes, this.drawnShapes);
    }

    /**
     * Gets the drawn shapes.
     *
     * @return list of drawn shapes
     */
    public List<GenericShape> getDrawnShapes() {
        return drawnShapes;
    }

    /**
     * Gets nearest drawn shape to point.
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return nearest drawn shape
     */
    public GenericShape closestInBounds(int x, int y) {
        Point2D currentPoint = new Point2D.Double(x, y);
        TreeMap<Double, GenericShape> closestFinder = new TreeMap<>();
        for (GenericShape drawnShape : drawnShapes) {
            if (drawnShape.pointIsInShapeBoundaries(x, y)) {
                closestFinder.put(
                        Math.min(
                                drawnShape.getStartPoint().distance(currentPoint),
                                drawnShape.getEndPoint().distance(currentPoint)),
                        drawnShape);
            }
        }
        if (closestFinder.isEmpty()) {
            return null;
        } else {
            return closestFinder.get(closestFinder.firstKey());
        }
    }

    /**
     * Gets the selected shape.
     *
     * @return selected shape
     */
    public GenericShape getSelectedShape() {
        return (hasSelected()) ? this.drawnShapes.get(selectedShapeIndex) : null;
    }

    /**
     * Checks if any shape is selected.
     *
     * @return true if any shape is selected
     */
    public boolean hasSelected() {
        return !(selectedShapeIndex < 0);
    }

    /**
     * Deselects shape.
     */
    public void deSelect() {
        selectedShapeIndex = -1;
    }

    /**
     * Gets the current color of the model.
     *
     * @return current color
     */
    public Color getCurrentColor() {
        return currentColor;
    }

    /**
     * Sets the current color of the model.
     *
     * @param currentColor current color
     */
    public void setCurrentColor(Color currentColor) {
        Color previousSelectedColor = this.currentColor;
        if (previousSelectedColor != currentColor) {
            this.currentColor = currentColor;
            update("currentColor", previousSelectedColor, currentColor);
        }
    }

    /**
     * Gets the fill option of the model.
     *
     * @return fill option
     */
    public boolean isShouldFill() {
        return shouldFill;
    }

    /**
     * Sets the fill option of the model.
     *
     * @param shouldFill fill option
     */
    public void setShouldFill(boolean shouldFill) {
        boolean previousShouldFill = this.shouldFill;
        if (previousShouldFill != shouldFill) {
            this.shouldFill = shouldFill;
            update("shouldFill", previousShouldFill, shouldFill);
        }
    }

    /**
     * Sets the color of the selected shape.
     *
     * @param color color of the selected shape
     */
    public void setSelectedShapeColor(Color color) {
        if (hasSelected()) {
            Color previousColor = getSelectedShape().getColor();
            getSelectedShape().setColor(color);
            update("selectedShapeColor", previousColor, color);
            appendHistory();
        }
    }

    /**
     * Sets the fill option of the selected shape.
     *
     * @param fill fill option of the selected shape
     */
    public void setSelectedShapeFill(boolean fill) {
        if (hasSelected()) {
            boolean previousFill = getSelectedShape().isFill();
            getSelectedShape().setFill(fill);
            appendHistory();
            update("selectedShapeFill", previousFill, fill);
        }
    }

    /**
     * Sets the end point of the selected shape.
     *
     * @param endPoint  end point
     * @param shiftDown true if shift key was pressed down
     */
    public void setSelectedShapeEndPoint(Point2D endPoint, boolean shiftDown) {
        // we dont append history here, we do it in when the mouse has been released to signify that a shape has been fully created
        if (hasSelected()) {
            Point2D previousEndPoint = getSelectedShape().getEndPoint();
            getSelectedShape().setEndPoint(endPoint, shiftDown);
            update("selectedShapeEndPoint", previousEndPoint, endPoint);

        }
    }
}
