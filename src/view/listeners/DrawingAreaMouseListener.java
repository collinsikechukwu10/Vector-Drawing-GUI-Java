package view.listeners;

import controller.DrawAreaController;

import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;

/**
 * Drawing area mouse listener class.
 * author 210032207
 */
public class DrawingAreaMouseListener extends MouseInputAdapter {
    private final DrawAreaController drawAreaController;

    /**
     * Constructor initializing the mouse listener for the drawing area.
     *
     * @param drawAreaController drawing area controller
     */
    public DrawingAreaMouseListener(DrawAreaController drawAreaController) {
        super();
        this.drawAreaController = drawAreaController;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        // we need to check if shift is activated, if so , snap snappable shapes like rectangles to squares and ellipses to circles
        drawAreaController.controlSelectedShapeEndPoint(e.getPoint(), e.isShiftDown());
    }


    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);

        //create shape
        if (drawAreaController.hasBluePrint() && drawAreaController.getSelectedShape() == null) {
            drawAreaController.controlInitShape(e.getPoint());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        // this entails that the shape has been fully created, we can add this to the history.
        drawAreaController.controlDeselect();
        drawAreaController.controlAppendHistory();

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
}
