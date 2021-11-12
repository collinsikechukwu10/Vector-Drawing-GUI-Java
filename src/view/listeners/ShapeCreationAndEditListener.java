package view.listeners;

import controller.listeners.DrawingAreaController;
import controller.listeners.ShapeController;
import model.shapes.GenericShape;

import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public class ShapeCreationAndEditListener extends MouseInputAdapter implements Command{
    private final DrawingAreaController drawingAreaController;

    public ShapeCreationAndEditListener(DrawingAreaController drawingAreaController) {
        this.drawingAreaController = drawingAreaController;
    }

    private ShapeController getCurrentWorkingController() {
        GenericShape shape = this.drawingAreaController.getSelectedShape();
        return ShapeController.getController(shape);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        GenericShape shape = this.drawingAreaController.getSelectedShape();
        ShapeController controller = ShapeController.getController(shape);
        controller.controlStartPoint(e.getPoint());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        GenericShape shape = this.drawingAreaController.getSelectedShape();
        ShapeController controller = ShapeController.getController(shape);
        controller.controlEndPoint(new Point2D.Double(e.getX(),e.getY()));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // add object to list cuz its done neing created
        // this is also the only time that a shape is added to the history
    }

    @Override
    public void execute() {

    }


}
