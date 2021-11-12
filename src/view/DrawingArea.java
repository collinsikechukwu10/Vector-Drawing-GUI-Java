package view;

import controller.listeners.DrawingAreaController;
import model.DrawAreaModel;
import model.shapes.*;
import model.shapes.Rectangle;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class DrawingArea extends JPanel {
    DrawingAreaController drawingAreaController;
    JFrame parent;

    public DrawingArea(JFrame parent, DrawingAreaController drawingAreaController) {
        super();
        this.parent = parent;
        // get drawing functions
        this.drawingAreaController = drawingAreaController;
        setSize(600, 600);
        setBackground(Color.LIGHT_GRAY);
    }




    private void shapeDraw(Graphics2D g2d, GenericShape shapeToDraw) {
        Shape shape = null;
        g2d.setColor(shapeToDraw.getColor());
        if (shapeToDraw instanceof Line) {
            shape = new Line2D.Float(shapeToDraw.getStartPoint(), shapeToDraw.getEndPoint());
        } else if (shapeToDraw instanceof Rectangle) {
            Point2D startPoint = shapeToDraw.getStartPoint();
            Point2D endPoint = shapeToDraw.getEndPoint();
            shape = new Rectangle2D.Double(startPoint.getX(), startPoint.getY(), (endPoint.getX() - startPoint.getX()), (endPoint.getY() - startPoint.getY()));
        } else if (shapeToDraw instanceof Ellipse) {
            Point2D startPoint = shapeToDraw.getStartPoint();
            Point2D endPoint = shapeToDraw.getEndPoint();
            shape = new Ellipse2D.Double(startPoint.getX(), startPoint.getY(), (endPoint.getX() - startPoint.getX()), (endPoint.getY() - startPoint.getY()));
        } else if (shapeToDraw instanceof Cross) {
            Point2D startPoint = shapeToDraw.getStartPoint();
            Point2D endPoint = shapeToDraw.getEndPoint();
            Shape shape1 = new Line2D.Double(startPoint.getX(), startPoint.getY(), (endPoint.getX() - startPoint.getX()), (endPoint.getY() - startPoint.getY()));
            Shape shape2 = new Line2D.Double(startPoint.getX(), (endPoint.getY() - startPoint.getY()), (endPoint.getX() - startPoint.getX()), startPoint.getY());
            Area area = new Area(shape1);
            area.add(new Area(shape2));
            shape = area;
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
        for (GenericShape shape: drawingAreaController.getDrawnShapes()){
            shapeDraw(g2d, shape);
        }
    }
}
