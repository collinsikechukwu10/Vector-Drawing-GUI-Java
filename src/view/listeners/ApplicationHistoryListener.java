package view.listeners;

import controller.DrawingAreaController;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;

public class ApplicationHistoryListener extends MouseAdapter implements KeyListener {
    private DrawingAreaController drawingAreaController;
    public ApplicationHistoryListener(DrawingAreaController drawingAreaController){
        this.drawingAreaController = drawingAreaController;
    }
    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.isShiftDown() && keyEvent.getKeyCode() == KeyEvent.VK_Z){
            this.drawingAreaController.controlUndo();
        }else if(keyEvent.isShiftDown() && keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_Z){
            this.drawingAreaController.controlRedo();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }
}
