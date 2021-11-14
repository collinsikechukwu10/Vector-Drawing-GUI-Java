package view;

import config.ApplicationConfig;
import controller.DrawingAreaController;
import model.MurrayPolygonCalculations;
import model.shapes.*;
import model.shapes.Rectangle;
import model.shapes.generic.GenericShape;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class DrawingArea extends JPanel {
    DrawingAreaController drawingAreaController;

    public DrawingArea(DrawingAreaController drawingAreaController) {
        super();
        // get drawing functions
        this.drawingAreaController = drawingAreaController;
        setSize(ApplicationConfig.DRAWING_AREA_WIDTH, ApplicationConfig.DRAWING_AREA_HEIGHT);
        setBackground(ApplicationConfig.DRAWING_AREA_BACKGROUND);
    }


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
            BasicStroke stroke = new BasicStroke((int) (0.35 * Math.min(height, width)));
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
            ArrayList<Point2D> murrayPolygonPoints = MurrayPolygonCalculations.generatePoints();
            Path2D path2D = new Path2D.Double();
            path2D.moveTo(x, y);
            double scalefactorX = width / 25;
            double scalefactorY = height / 15;
            for (Point2D point : murrayPolygonPoints) {
                path2D.lineTo(x + point.getX() * scalefactorX, y + point.getY() * scalefactorY);
            }
            shape = path2D.createTransformedShape(AffineTransform.getScaleInstance(1, 1));
            System.out.println("sx:" + width / 25 + " sy: " + height / 15);
        }
        // do other post processing like fill or color and such
        if (shapeToDraw.isFill()) {
            g2d.setPaint(shapeToDraw.getColor());
            g2d.fill(shape);
        } else {
            g2d.draw(shape);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // get current shape
        Graphics2D g2d = (Graphics2D) g;
        if(!drawingAreaController.isEmpty()){
            for (GenericShape shape : drawingAreaController.getDrawnShapes()) {
                shapeDraw(g2d, shape);
            }
        }

    }
}
