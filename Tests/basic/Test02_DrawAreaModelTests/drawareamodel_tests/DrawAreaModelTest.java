package drawareamodel_tests;

import model.DrawAreaModel;
import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Point2D;

import static org.junit.Assert.*;

public class DrawAreaModelTest {
    private DrawAreaModel model;

    @Before
    public void setUp() throws Exception {
        model = new DrawAreaModel();
    }

    @Test
    public void testHistoryTracking() {
    }



    @Test
    public void undoAndRedo() {

    }

    @Test
    public void testAddShape() {
        model.setShapeToPrepare("Rectangle");
        model.addShape(new Point2D.Double(20,20));
        assertEquals(model.getDrawnShapes().size(),1);
    }

    @Test
    public void testClearingDrawArea() {
        model.setShapeToPrepare("Rectangle");
        model.addShape(new Point2D.Double(20,20));
        model.setShapeToPrepare("MurrayPolygon");
        model.addShape(new Point2D.Double(20,20));
        model.setShapeToPrepare("Line");
        model.addShape(new Point2D.Double(20,20));
        assertEquals(model.getDrawnShapes().size(),3);
        model.clearDrawArea();
        assertEquals(model.getDrawnShapes().size(),0);

    }

    @Test
    public void testColorSetting() {
    }


    @Test
    public void testFill() {
    }

    @Test
    public void setSelectedShapeEndPoint() {
    }
}