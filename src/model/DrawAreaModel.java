package model;

import config.ApplicationConfig;
import model.shapes.*;
import model.shapes.Rectangle;
import model.shapes.generic.GenericShape;

import java.awt.*;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;
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
    List<GenericShape> drawnShapes = new ArrayList<>();
    private int selectedShapeIndex = -1;
    private final Map<String, Class<? extends GenericShape>> blueprints = new HashMap<>();
    private String shapeToPrepare = null;
    private HistoryStack<List<GenericShape>> history;

    /**
     * Constructor initializing model.
     */
    public DrawAreaModel() {

        // les set a default color
        this.currentColor = ApplicationConfig.DEFAULT_SHAPE_COLOR;
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
     * Register a listener to be notified of any changes to this model.
     *
     * @param listener Listener class.
     */
    public void addListener(PropertyChangeListener listener) {
        notifier.addPropertyChangeListener(listener);
    }

    /**
     * Broadcast change to all listeners
     *
     * @param property property name
     * @param oldValue old value of property
     * @param newValue new value of property
     */
    private void update(String property, Object oldValue, Object newValue) {
        notifier.firePropertyChange(property, oldValue, newValue);

    }


    public void undo() {
        this.history.undo();
        if (history.hasState()) {
            setDrawnShapes(new ArrayList<>(this.history.getState()));
        }
    }

    public void redo() {
        this.history.redo();
        if (history.hasState()) {
            setDrawnShapes(new ArrayList<>(this.history.getState()));
        }
    }

    /**
     * Initializes a shape to the model.
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

    public String getShapeToPrepare() {
        return shapeToPrepare;
    }

    public void setShapeToPrepare(String shapeToPrepare) {
        this.shapeToPrepare = shapeToPrepare;
    }

    /**
     * Clears all shapes in the model.
     */
    public void clearDrawArea() {
        List<GenericShape> previousDrawnShapes = getDrawnShapes();
        System.out.println(previousDrawnShapes);
        this.drawnShapes.clear();
        System.out.println(this.drawnShapes);

        this.selectedShapeIndex = -1;
        // we want to repaint after this soooooo
        appendHistory();
        update("drawnShapes", previousDrawnShapes, this.drawnShapes);
    }

    public void appendHistory() {
        history.push(getDrawnShapes().stream().map(GenericShape::copy).collect(Collectors.toList()));
    }

    public void setDrawnShapes(List<GenericShape> drawnShapes) {
        System.out.println("Drawn shapes:" + drawnShapes);
        List<GenericShape> previousDrawnShapes = getDrawnShapes();
        this.drawnShapes = drawnShapes;
        update("drawnShapes", previousDrawnShapes, this.drawnShapes);
    }


    public List<GenericShape> getDrawnShapes() {
        return drawnShapes;
    }

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

    public void selectShape(GenericShape genericShape) {
        if (this.drawnShapes.contains(genericShape)) {
            this.selectedShapeIndex = this.drawnShapes.indexOf(genericShape);
        }
    }

    public GenericShape getSelectedShape() {
        return (hasSelected()) ? this.drawnShapes.get(selectedShapeIndex) : null;
    }

    public boolean hasSelected() {
        return !(selectedShapeIndex < 0);
    }

    public void deSelect() {
        selectedShapeIndex = -1;
    }


    public Color getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(Color currentColor) {
        Color previousSelectedColor = this.currentColor;
        if (previousSelectedColor != currentColor) {
            this.currentColor = currentColor;
            update("currentColor", previousSelectedColor, currentColor);
        }
    }

    public boolean isShouldFill() {
        return shouldFill;
    }

    public void setShouldFill(boolean shouldFill) {
        boolean previousShouldFill = this.shouldFill;
        if (previousShouldFill != shouldFill) {
            this.shouldFill = shouldFill;
            update("shouldFill", previousShouldFill, shouldFill);
        }
    }

    /**
     * SHAPE SPECIFIC FUNCTIONS
     */

    public void setSelectedShapeColor(Color color) {
        if (hasSelected()) {
            Color previousColor = getSelectedShape().getColor();
            getSelectedShape().setColor(color);
            update("selectedShapeColor", previousColor, color);
            appendHistory();
        }
    }

    public void setSelectedShapeFill(boolean fill) {
        if (hasSelected()) {
            boolean previousFill = getSelectedShape().isFill();
            getSelectedShape().setFill(fill);
            appendHistory();
            update("selectedShapeFill", previousFill, fill);
        }
    }

    public void setSelectedShapeEndPoint(Point2D endPoint, boolean shiftDown) {
        // we dont append history here, we do it in when the mouse has been released to signify that a shape has been fully created
        if (hasSelected()) {
            Point2D previousEndPoint = getSelectedShape().getEndPoint();
            getSelectedShape().setEndPoint(endPoint, shiftDown);
            update("selectedShapeEndPoint", previousEndPoint, endPoint);

        }
    }
}
