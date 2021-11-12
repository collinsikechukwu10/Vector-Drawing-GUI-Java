package view;

import controller.listeners.DrawingAreaController;
import model.DrawAreaModel;
import model.shapes.GenericShape;
import view.io.DrawingImporterExporter;
import view.listeners.DrawingAreaMouseListener;
import view.listeners.ShapeCreationAndEditListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class VectorDrawingView implements PropertyChangeListener {
    private static final int VIEW_HEIGHT = 1000;
    private static final int VIEW_WIDTH = 1000;
    DrawAreaModel drawAreaModel;
    DrawingArea drawingAreaView;
    JFrame mainFrame;

    public VectorDrawingView(DrawAreaModel drawAreaModel, DrawingAreaController drawingAreaController) {
        // create a frame
        this.mainFrame = new JFrame();
        this.drawAreaModel = drawAreaModel;
        mainFrame.setLayout(new GridLayout(1, 2));
        this.drawingAreaView = new DrawingArea(mainFrame, drawingAreaController);
        this.drawingAreaView.addMouseListener(new DrawingAreaMouseListener(drawingAreaController));

        mainFrame.setJMenuBar(createJMenuBar(drawingAreaController));

        mainFrame.getContentPane().add(createToolBar(drawingAreaController));
        mainFrame.getContentPane().add(this.drawingAreaView);

        // set model property change listener
        drawAreaModel.addListener(this);
        // we also need to attach this to every shape created dynamically.


        mainFrame.setVisible(true);
        mainFrame.setSize(VIEW_WIDTH, VIEW_HEIGHT);
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        mainFrame.paintAll(mainFrame.getGraphics());
        mainFrame.pack();

    }


    private JMenuBar createJMenuBar(DrawingAreaController controller) {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem saveAs = new JMenuItem("Save As");
        JMenuItem undo = new JMenuItem("Undo");
        JMenuItem redo = new JMenuItem("Redo");
        menu.add(undo);
        menu.setBackground(Color.ORANGE);
        menu.add(redo);

        // attach load listener and add the item to the menu
        DrawingImporterExporter drawingImporterExporter = new DrawingImporterExporter(controller);
        load.addActionListener(drawingImporterExporter.fileLoadListener(mainFrame));
        menu.add(load);
        // attach save listener and add the item to the menu
        saveAs.addActionListener(drawingImporterExporter.fileSaveListener(mainFrame));
        menu.add(saveAs);
        menuBar.add(menu);
        return menuBar;
    }

    private JToolBar createToolBar(DrawingAreaController controller) {
        JToolBar toolBar = new JToolBar();
        toolBar.setOrientation(SwingConstants.VERTICAL);
        toolBar.setLayout(new GridLayout(4,2));
        JButton colorButton = new JButton("Color");
        colorButton.addActionListener(actionEvent -> {
            Color color = JColorChooser.showDialog(mainFrame, "Choose a color", Color.RED);
            if (controller.getSelectedShape() != null) {
                controller.controlSelectedShapeColor(color);
                controller.controlDeselect();
            } else {
                controller.controlColor(color);
            }
        });

        JToggleButton fillButton = new JToggleButton("Fill");
        fillButton.addActionListener(actionEvent -> {
            AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
            if (controller.getSelectedShape() != null) {
                // set shape fill and reset fill button
                controller.controlSelectedShapeFill(abstractButton.isSelected());
                // TODO check to see if the line below deselects the fill button
                abstractButton.setSelected(false);
                controller.controlDeselect();
            } else {
                controller.controlFill(abstractButton.isSelected());
            }
            controller.controlFill(abstractButton.getModel().isSelected());
        });

        JButton resizeButton = new JButton("Resize");
        resizeButton.addActionListener(actionEvent -> {
            if (controller.getSelectedShape() != null) {
                // TODO dont know what to do there
                return;
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Select a shape first");
            }

        });
        JButton selectButton = new JButton("Select");
        toolBar.add(colorButton);
        toolBar.add(resizeButton);
        toolBar.add(fillButton);
        toolBar.add(selectButton);
        createAllShapeButtons(controller).forEach(toolBar::add);
        return toolBar;

    }

    public ArrayList<JToggleButton> createAllShapeButtons(DrawingAreaController controller) {
        ArrayList<JToggleButton> shapeButtons = new ArrayList();
        List<String> shapeName = List.of("Line", "Cross", "Ellipse", "Rectangle");
        ActionListener shapeSelectListener = createShapeSelectListener(controller);
        shapeName.forEach(name -> {
            JToggleButton button = new JToggleButton(name);
            button.addActionListener(shapeSelectListener);
            shapeButtons.add(button);
        });
        return shapeButtons;
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {

        SwingUtilities.invokeLater(() -> {
            System.out.println("[" + event.getPropertyName() + "] old: "+event.getOldValue() + ", new: " + event.getNewValue());
            drawingAreaView.repaint();
        });
    }

    private ActionListener createShapeSelectListener(DrawingAreaController controller) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AbstractButton button = (AbstractButton) actionEvent.getSource();
                if (controller.hasBluePrint()){
                    JOptionPane.showMessageDialog(mainFrame,"You cannot select more than 1 shape to draw");
                    button.setSelected(false);
                    return;
                }
                if (button.isSelected()) {
                    // this should remove a selected shape and allow for creation of a new shape
                    controller.controlDeselect();
                    // set the blueprint of the shape you want to create
                    controller.controlSetBlueprint(button.getActionCommand());
                    // we need to set some sort of info to tell the model that the are going to create a shape
                } else {
                    controller.controlSetBlueprint(null);
                    // this should allow for selection of existing shapes

                }
            }
        };
    }
}
