package controller_tests;

import controller.DrawAreaController;
import model.DrawAreaModel;
import model.shapes.Line;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.geom.Point2D;

import static org.junit.Assert.*;

public class DrawAreaControllerTest {

    private DrawAreaModel model;
    private DrawAreaController controller;

    @Before
    public void setUp() throws Exception {
        model = new DrawAreaModel();
        controller = new DrawAreaController(model);
    }

    @Test
    public void testControllerModel() {
        assertEquals(model, controller.getModel());
    }

    @Test
    public void testCreateShape() {
        controller.controlSetBlueprint("Line");
        controller.controlInitShape(new Point2D.Double(20, 20));
        assertEquals(controller.getDrawnShapes().size(), 1);
        assertEquals(controller.getDrawnShapes().get(0).getClass(), Line.class);
    }

    @Test
    public void testUndo() {
        controller.controlSetBlueprint("Line");
        controller.controlInitShape(new Point2D.Double(20, 20));
        controller.controlAppendHistory();
        assertEquals(controller.getDrawnShapes().size(), 1);
        controller.controlSetBlueprint("Rectangle");
        controller.controlInitShape(new Point2D.Double(20, 40));
        controller.controlAppendHistory();
        assertEquals(controller.getDrawnShapes().size(), 2);
        controller.controlUndo();
        assertEquals(controller.getDrawnShapes().size(), 1);
    }

    @Test
    public void testUndoAndRedo() {
        controller.controlSetBlueprint("Line");
        controller.controlInitShape(new Point2D.Double(20, 20));
        controller.controlAppendHistory();
        controller.controlSetBlueprint("Rectangle");
        controller.controlInitShape(new Point2D.Double(20, 40));
        controller.controlAppendHistory();
        controller.controlSetBlueprint("Ellipse");
        controller.controlInitShape(new Point2D.Double(23, 43));
        controller.controlAppendHistory();
        controller.controlSetBlueprint("Rectangle");
        controller.controlInitShape(new Point2D.Double(30, 400));
        controller.controlAppendHistory();
        controller.controlUndo();
        controller.controlUndo();
        controller.controlRedo();
        assertEquals(controller.getDrawnShapes().size(), 3);
    }

    @Test
    public void testColor() {
        controller.controlColor(Color.BLUE);
        controller.controlSetBlueprint("Line");
        controller.controlInitShape(new Point2D.Double(20, 20));
        assertEquals(controller.getSelectedShape().getColor(), Color.BLUE);
    }

    @Test
    public void testFill() {
        controller.controlFill(true);
        controller.controlSetBlueprint("Line");
        controller.controlInitShape(new Point2D.Double(20, 20));
        assertTrue(controller.getSelectedShape().isFill());
    }

    @Test
    public void testSelectedShapeEndPoint() {
        Point2D endPoint = new Point2D.Double(40, 50);
        controller.controlSetBlueprint("Line");
        controller.controlInitShape(new Point2D.Double(20, 20));
        controller.controlSelectedShapeEndPoint(endPoint, false);
        assertEquals(controller.getSelectedShape().getEndPoint(), endPoint);
    }

    @Test
    public void controlSetDrawnShapes() {
    }

    @Test
    public void testClearShapes() {
        controller.controlSetBlueprint("Line");
        controller.controlInitShape(new Point2D.Double(20, 20));
        controller.controlSetBlueprint("Rectangle");
        controller.controlInitShape(new Point2D.Double(20, 40));
        assertEquals(controller.getDrawnShapes().size(), 2);
        controller.controlClearShapes();
        assertEquals(controller.getDrawnShapes().size(), 0);
        assertTrue(controller.isEmpty());
    }


    @Test
    public void testHistoryReset() {
        controller.controlSetBlueprint("Line");
        controller.controlInitShape(new Point2D.Double(20, 20));
        controller.controlSetBlueprint("Rectangle");
        controller.controlInitShape(new Point2D.Double(20, 40));
        controller.controlResetHistory();
        assertEquals(controller.getModel().getHistory().size(), 1);
    }

    @Test
    public void testShapeSelection() {
        controller.controlSetBlueprint("Line");
        controller.controlInitShape(new Point2D.Double(20, 20));
        assertNotNull(controller.getSelectedShape());
        controller.controlDeselect();
        assertNull(controller.getSelectedShape());
    }


}