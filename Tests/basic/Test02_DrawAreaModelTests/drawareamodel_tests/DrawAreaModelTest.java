package drawareamodel_tests;

import model.DrawAreaModel;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.awt.geom.Point2D;

import static org.junit.Assert.*;

public class DrawAreaModelTest {
    private DrawAreaModel model;

    @Before
    public void setUp() {
        model = new DrawAreaModel();
    }

    @Test
    public void testHistoryTracking() {
    }


    @Test
    public void testUndo() {
        model.setShapeToPrepare("Rectangle");
        model.addShape(new Point2D.Double(20, 20));
        model.appendHistory();
        model.setShapeToPrepare("Rectangle");
        model.addShape(new Point2D.Double(20, 20));
        model.appendHistory();
        model.setShapeToPrepare("Rectangle");
        model.addShape(new Point2D.Double(20, 20));
        model.appendHistory();
        model.undo();
        assertEquals(model.getDrawnShapes().size(), 2);
    }

    @Test
    public void testRedo() {
        model.setShapeToPrepare("Rectangle");
        model.addShape(new Point2D.Double(20, 20));
        model.appendHistory();
        model.setShapeToPrepare("Line");
        model.addShape(new Point2D.Double(20, 20));
        model.appendHistory();
        model.setShapeToPrepare("Rectangle");
        model.addShape(new Point2D.Double(20, 20));
        model.appendHistory();
        model.undo();
        model.undo();
        model.redo();
        assertEquals(model.getDrawnShapes().size(), 2);
    }

    @Test
    public void testAddShape() {
        model.setShapeToPrepare("Rectangle");
        model.addShape(new Point2D.Double(20, 20));
        assertEquals(model.getDrawnShapes().size(), 1);
    }

    @Test
    public void testClearingDrawArea() {
        model.setShapeToPrepare("Rectangle");
        model.addShape(new Point2D.Double(20, 20));
        model.appendHistory();
        model.setShapeToPrepare("MurrayPolygon");
        model.addShape(new Point2D.Double(20, 20));
        model.setShapeToPrepare("Line");
        model.addShape(new Point2D.Double(20, 20));
        assertEquals(model.getDrawnShapes().size(), 3);
        model.clearDrawArea();
        assertEquals(model.getDrawnShapes().size(), 0);

    }

    @Test
    public void testColorSetting() {
        model.setCurrentColor(Color.RED);
        model.setShapeToPrepare("MurrayPolygon");
        model.addShape(new Point2D.Double(20, 20));
        assertEquals(model.getSelectedShape().getColor(), Color.RED);
    }


    @Test
    public void testFill() {
        model.setShouldFill(false);
        model.setShapeToPrepare("Ellipse");
        model.addShape(new Point2D.Double(20, 20));
        assertFalse(model.getSelectedShape().isFill());
    }

    @Test
    public void setSelectedShapeEndPoint() {
        model.setShapeToPrepare("Ellipse");
        model.addShape(new Point2D.Double(20, 20));
        model.getSelectedShape().setEndPoint(new Point2D.Double(500, 500), false);
        assertEquals(model.getSelectedShape().getEndPoint(), new Point2D.Double(500, 500));
    }
}