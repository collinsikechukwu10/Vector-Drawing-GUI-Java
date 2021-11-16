package view.listeners;

import controller.DrawAreaController;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ApplicationHistoryListener implements KeyListener {
    private final DrawAreaController drawAreaController;
    public ApplicationHistoryListener(DrawAreaController drawAreaController){
        this.drawAreaController = drawAreaController;
    }
    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.isShiftDown() && keyEvent.getKeyCode() == KeyEvent.VK_Z){
            this.drawAreaController.controlUndo();
        }else if(keyEvent.isShiftDown() && keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_Z){
            this.drawAreaController.controlRedo();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }
}
