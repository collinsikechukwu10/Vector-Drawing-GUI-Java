package view.listeners;

import controller.DrawAreaController;
import model.shapes.generic.GenericShape;

import javax.swing.JFrame;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


import java.util.List;

/**
 * Draw IO Listener class.
 * This class generates all the listeners needed to store and retrieve drawn shapes.
 *
 * @author 210032207
 */
@SuppressWarnings("unchecked")
public class DrawIOListener {
    private final DrawAreaController drawAreaController;

    /**
     * Constructor specifying the controller to use to initialize the listener and link io operations to it.
     *
     * @param drawAreaController draw area controller
     */
    public DrawIOListener(DrawAreaController drawAreaController) {
        this.drawAreaController = drawAreaController;
    }

    /**
     * Generates a file save listener to be used by a "Save" button.
     *
     * @param parent frame to render the file dialog message.
     * @return file save action listener
     */
    public ActionListener fileSaveListener(JFrame parent) {
        return actionEvent -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");

            int userSelection = fileChooser.showSaveDialog(parent);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                // get file history
                if (!fileToSave.exists()) {
                    try {
                        FileOutputStream fos = new FileOutputStream(fileToSave);
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(drawAreaController.getDrawnShapes());
                        oos.close();
                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }
            }
        };

    }

    /**
     * Generates a file load listener to be used by a "Load" button.
     *
     * @param parent frame to render the file dialog message.
     * @return file load action listener
     */
    public ActionListener fileLoadListener(JFrame parent) {
        return actionEvent -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to load");

            int userSelection = fileChooser.showOpenDialog(parent);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToLoad = fileChooser.getSelectedFile();
                // get file history
                try {
                    FileInputStream fis = new FileInputStream(fileToLoad);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    List<GenericShape> drawnShapes = (List<GenericShape>) ois.readObject();
                    // clear shapes
                    drawAreaController.controlClearShapes();
                    // set drawn shapes to imported  shapes
                    drawAreaController.controlSetDrawnShapes(drawnShapes);
                    // reset history
                    drawAreaController.controlResetHistory();
                    // this should clear the drawing area, create  new shapes based on the history object saved
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        };

    }
}
