package view.listeners;

import controller.listeners.DrawingAreaController;

import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;

public class DrawingAreaMouseListener extends MouseInputAdapter {
    DrawingAreaController drawingAreaController;

    public DrawingAreaMouseListener(DrawingAreaController drawingAreaController) {
        super();
        this.drawingAreaController = drawingAreaController;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        // we need to check if shift is activated, if so , snap snappable shapes like rectangles to squares and ellipses to circles
        drawingAreaController.controlSelectedShapeEndPoint(e.getPoint(),e.isShiftDown());
    }


    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        System.out.println("Pressed");

        //create shape
        if(drawingAreaController.hasBluePrint() && drawingAreaController.getSelectedShape()==null){
            drawingAreaController.controlInitShape(e.getPoint());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
//        this.drawingAreaController.controlSetBlueprint(null);
        System.out.println("Released");
        // we need to deactivate the button

    }
}
