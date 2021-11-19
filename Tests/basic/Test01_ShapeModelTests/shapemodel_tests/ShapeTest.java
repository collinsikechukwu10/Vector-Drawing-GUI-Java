package shapemodel_tests;

import model.shapes.*;
import model.shapes.Rectangle;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.geom.Point2D;

import static org.junit.Assert.*;

public class ShapeTest {
    private Point2D randomStartPoint;
    private Point2D randomEndPoint;
    private Color randomColor;
    private boolean randomFill;
    private static final double EPSILON = 0.0001;

    @Before
    public void setUp() {
        randomStartPoint = new Point2D.Double(Math.random() * 200, Math.random() * 100);
        randomEndPoint = new Point2D.Double(Math.random() * 800, Math.random() * 750);
        randomColor = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
        randomFill = (Math.random() > 0.5);
    }

    @Test
    public void testCopyDifference() {
        Line line = new Line(randomColor, randomStartPoint, randomEndPoint, randomFill);
        assertNotEquals(line, line.copy());
    }

    @Test
    public void testCorrectlySetDescriptionValues() {
        Rectangle rectangle = new Rectangle(randomColor, randomStartPoint, randomEndPoint, randomFill);
        assertEquals(rectangle.getStartPoint(), randomStartPoint);
        assertEquals(rectangle.getEndPoint(), randomEndPoint);
        assertEquals(rectangle.isFill(), randomFill);
        assertEquals(rectangle.getColor(), randomColor);
    }

    @Test
    public void testShiftKeyShapeFuntionality() {
        Ellipse ellipse = new Ellipse(randomColor, randomStartPoint, randomStartPoint, randomFill);
        ellipse.setEndPoint(randomEndPoint, true);
        // check that the aspect ratio is 1 when the shift key is pressed,
        // for the ellipse, this would generate a circle
        double width = Math.abs(ellipse.getEndPoint().getX() - ellipse.getStartPoint().getX());
        double height = Math.abs(ellipse.getEndPoint().getY() - ellipse.getStartPoint().getY());
        assert height / width <= EPSILON;
    }


}