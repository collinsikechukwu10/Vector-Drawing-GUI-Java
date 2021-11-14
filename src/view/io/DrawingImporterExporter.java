package view.io;

import controller.DrawingAreaController;
import model.shapes.generic.GenericShape;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;

public class DrawingImporterExporter {
    DrawingAreaController drawingAreaController;
    public DrawingImporterExporter(DrawingAreaController drawingAreaController){
        this.drawingAreaController = drawingAreaController;
    }

    public ActionListener fileSaveListener(JFrame parent) {
        return actionEvent -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");

            int userSelection = fileChooser.showSaveDialog(parent);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                System.out.println("Save as file: " + fileToSave.getAbsolutePath());
                // get file history
                if (!fileToSave.exists()) {
                    try {
                        FileOutputStream fos = new FileOutputStream(fileToSave);
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(drawingAreaController.getDrawnShapes());
                        oos.close();
                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }
            }
        };

    }

    public ActionListener fileLoadListener(JFrame parent) {
        return actionEvent -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to load");

            int userSelection = fileChooser.showOpenDialog(parent);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToLoad = fileChooser.getSelectedFile();
                System.out.println("Loading file..." + fileToLoad.getAbsolutePath());
                // get file history
                try {
                    FileInputStream fis = new FileInputStream(fileToLoad);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    List<GenericShape> drawnShapes = (List<GenericShape>) ois.readObject();
                    drawingAreaController.controlClearShapes();
                    drawingAreaController.controlSetDrawnShapes(drawnShapes);
                    // this should clear the drawing area, create  new shapes based on the history object saved
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        };

    }
}
