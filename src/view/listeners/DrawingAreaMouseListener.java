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
        drawingAreaController.controlSelectedShapeEndPoint(e.getPoint());
    }


    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        //create shape
        if(drawingAreaController.hasBluePrint() && drawingAreaController.getSelectedShape()==null){
            drawingAreaController.controlInitShape(e.getPoint());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
    }
}
