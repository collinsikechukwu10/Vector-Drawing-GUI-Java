package model;

import model.shapes.*;
import model.shapes.Rectangle;

import java.awt.*;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

public class DrawAreaModel {
    private Color currentColor;
    private boolean shouldFill;
    private PropertyChangeSupport notifier;  // tracks and notifies listeners
    List<GenericShape> drawnShapes = new ArrayList<>();
    private int selectedShapeIndex = -1;
    private LocalDateTime lastChangedOn = LocalDateTime.now();
    private final Map<String, Class<? extends GenericShape>> blueprints = new HashMap<>();
    private String shapeToPrepare = null;


    public DrawAreaModel() {
        // les set a default color
        this.currentColor = Color.BLACK;
        this.shouldFill = false;
        // prepare blueprints
        List.of(Line.class, Rectangle.class, Ellipse.class, Cross.class).forEach(aClass -> {
            blueprints.put(aClass.getSimpleName().toLowerCase(), aClass);
        });

        this.notifier = new PropertyChangeSupport(this);
    }

    /**
     * Register a listener so it will be notified of any changes.
     */
    public void addListener(PropertyChangeListener listener) {
        notifier.addPropertyChangeListener(listener);
    }

    /**
     * Broadcast most recent change to all listeners
     */
    private void update(String property, Object oldValue, Object newValue) {
        notifier.firePropertyChange(property, oldValue, newValue);
    }

    private void update() {
        // only doing this to catch changes to the shapes themselves
        notifier.firePropertyChange("lastChangedOn", this.lastChangedOn, LocalDateTime.now());

    }

    public void addShape(Point2D startPoint) {
        // TODO CHECK this please
        GenericShape shape;
        if (getShapeToPrepare() != null) {
            Class<? extends GenericShape> bluePrint = blueprints.get(getShapeToPrepare().toLowerCase());
            try {
                shape = bluePrint.getConstructor(Color.class, Point2D.class, Point2D.class, boolean.class).newInstance(
                        getCurrentColor(), startPoint, startPoint, isShouldFill()
                );
                // add shape to existing and inform app.
                List<GenericShape> previousDrawnShapes = getDrawnShapes();
                this.drawnShapes.add(shape);
                selectedShapeIndex = this.drawnShapes.size() - 1;
                notifier.firePropertyChange("drawnShapes", previousDrawnShapes, this.drawnShapes);
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

    public void remove(GenericShape genericShape) {
        this.drawnShapes.remove(genericShape);
    }

    public void clearDrawArea() {
        List<GenericShape> previousDrawnShapes = getDrawnShapes();
        this.drawnShapes.clear();
        // we want to repaint after this soooooo
        notifier.firePropertyChange("drawnShapes", previousDrawnShapes, this.drawnShapes);
    }

    public void setDrawnShapes(List<GenericShape> drawnShapes) {
        List<GenericShape> previousDrawnShapes = getDrawnShapes();
        this.drawnShapes = drawnShapes;
        notifier.firePropertyChange("drawnShapes", previousDrawnShapes, this.drawnShapes);
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

    public void selectShape(int selectedShapeIndex) {
        if (selectedShapeIndex < this.drawnShapes.size()) {
            this.selectedShapeIndex = selectedShapeIndex;
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
            update("currentSelectedColor", previousSelectedColor, currentColor);
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
            getSelectedShape().setColor(color);
            update();
        }
    }

    public void setSelectedShapeFill(boolean fill) {
        if (hasSelected()) {
            getSelectedShape().setFill(fill);
            update();
        }
    }

    public void setSelectedShapeStartPoint(Point2D startPoint) {
        if (hasSelected()) {
            getSelectedShape().setStartPoint(startPoint);
            update();
        }
    }

    public void setSelectedShapeEndPoint(Point2D endPoint) {
        if (hasSelected()) {
            getSelectedShape().setEndPoint(endPoint);
            update();
        }
    }
}
