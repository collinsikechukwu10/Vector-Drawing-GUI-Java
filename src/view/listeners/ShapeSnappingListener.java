package view.listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ShapeSnappingListener extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {
        if ((e.getModifiersEx() & KeyEvent.SHIFT_DOWN_MASK) != 0) {
            // SNAP WHATEVER SHAPE IS CURRENTLY SELECTED TO A SQUARE
        }
    }
}

