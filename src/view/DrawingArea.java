package view;

import config.ApplicationConfig;
import controller.DrawAreaController;

import javax.swing.JPanel;
import java.awt.Shape;
import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.BasicStroke;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.AffineTransform;

import model.MurrayPolygonCalculations;
import model.shapes.generic.GenericShape;
import model.shapes.Ellipse;
import model.shapes.Rectangle;
import model.shapes.Line;
import model.shapes.Cross;
import model.shapes.MurrayPolygon;


/**
 * Drawing Area JPanel.
 * This is the gui panel where the shapes are drawn.
 *
 * @author 210032207
 */
public class DrawingArea extends JPanel {
    private final DrawAreaController drawAreaController;
    private static final double CROSS_THICKNESS_RATIO = 0.35;

    /**
     * Constructor initializing the drawing area.
     *
     * @param drawAreaController drawing area controller
     */
    public DrawingArea(DrawAreaController drawAreaController) {
        super();
        setLayout(new BorderLayout());
        // get drawing functions
        this.drawAreaController = drawAreaController;
        setSize(ApplicationConfig.DRAWING_AREA_WIDTH, ApplicationConfig.DRAWING_AREA_HEIGHT);
        setBackground(ApplicationConfig.DRAWING_AREA_BACKGROUND);
    }

    /**
     * Generates awt shape to paint the jpanel canvas using blueprint shape instance.
     *
     * @param g2d         Panel graphics object
     * @param shapeToDraw Shape instance
     */
    private void shapeDraw(Graphics2D g2d, GenericShape shapeToDraw) {
        Shape shape = null;
        g2d.setColor(shapeToDraw.getColor());
        Point2D startPoint = shapeToDraw.getStartPoint();
        Point2D endPoint = shapeToDraw.getEndPoint();
        double x = Math.min(startPoint.getX(), endPoint.getX());
        double y = Math.min(startPoint.getY(), endPoint.getY());
        double width = Math.abs(startPoint.getX() - endPoint.getX());
        double height = Math.abs(startPoint.getY() - endPoint.getY());

        if (shapeToDraw instanceof Line) {
            shape = new Line2D.Float(startPoint, endPoint);
        } else if (shapeToDraw instanceof Rectangle) {
            shape = new Rectangle2D.Double(x, y, width, height);
        } else if (shapeToDraw instanceof Ellipse) {
            shape = new Ellipse2D.Double(x, y, width, height);
        } else if (shapeToDraw instanceof Cross) {
            // we draw a diagonal line from the start to the endpoint,
            // and then reflect the line on the x axis at the center of the shape.
            // define the thickness of the line, here I used 35% of the smallest
            // dimension of implicit bounds created by the start and end point of the shape
            BasicStroke stroke = new BasicStroke((int) (CROSS_THICKNESS_RATIO * Math.min(height, width)));
            Shape shape1 = stroke.createStrokedShape(new Line2D.Double(startPoint, endPoint));
            // Since we drew a diagonal line, the reflection of the
            // line on the x axis at its center would make a cross.
            AffineTransform transform1 = new AffineTransform();
            transform1.translate(shape1.getBounds2D().getCenterX(), 0);
            transform1.scale(-1, 1);
            transform1.translate(-shape1.getBounds2D().getCenterX(), 0);
            // perform reflection transform on shape and combine with original
            Area area1 = new Area(shape1);
            Area area2 = new Area(transform1.createTransformedShape(shape1));
            area1.add(area2);
            shape = area1;
        } else if (shapeToDraw instanceof MurrayPolygon) {
            // to maintain the start path of the murray polygon,
            // we always need to use the bottom right of the boundary
            // created by the start and endpoint to construct it
            MurrayPolygonCalculations murrayPolygon = MurrayPolygonCalculations.generateMurrayPolygon();
            //weirdly, the murray path is plotted the wrong way so we flip this
            Path2D path2D = new Path2D.Double();
            path2D.moveTo(x, y);
            double scaleFactorX = width / murrayPolygon.getWidth();
            double scaleFactorY = height / murrayPolygon.getHeight();
            for (Point2D point : murrayPolygon.getPath()) {
                path2D.lineTo(x + (point.getY() * scaleFactorX), y + (point.getX() * scaleFactorY));
            }
            AffineTransform transform1 = new AffineTransform();
            transform1.translate(0, path2D.getBounds2D().getCenterY());
            transform1.scale(1, -1);
            transform1.translate(0, -path2D.getBounds2D().getCenterY());
//            transform1.rotate(90.0);
            shape = path2D.createTransformedShape(transform1);
        }
        // do other post-processing like fill or color and such
        if (shapeToDraw.isFill() && !(shapeToDraw instanceof MurrayPolygon || shapeToDraw instanceof Line)) {
            g2d.setPaint(shapeToDraw.getColor());
            g2d.fill(shape);
        } else {
            g2d.draw(shape);
        }
    }

    /**
     * Paints shapes using graphics.
     *
     * @param g Panel graphics object
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // get current shape
        Graphics2D g2d = (Graphics2D) g;
        if (!drawAreaController.isEmpty()) {
            for (GenericShape shape : drawAreaController.getDrawnShapes()) {
                shapeDraw(g2d, shape);
            }
        }

    }
}
