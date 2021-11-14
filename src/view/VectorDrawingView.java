package view;

import config.ApplicationConfig;
import controller.DrawingAreaController;
import model.DrawAreaModel;
import view.io.DrawingImporterExporter;
import view.listeners.ApplicationHistoryListener;
import view.listeners.DrawingAreaMouseListener;

import javax.swing.*;
import javax.swing.plaf.ToolBarUI;
import javax.swing.plaf.metal.MetalButtonUI;
import javax.swing.plaf.metal.MetalToolBarUI;
import javax.swing.plaf.multi.MultiToolBarUI;
import javax.swing.plaf.synth.SynthButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class VectorDrawingView implements PropertyChangeListener {
    DrawAreaModel drawAreaModel;
    DrawingArea drawingAreaView;
    JFrame mainFrame;

    public VectorDrawingView(DrawAreaModel drawAreaModel, DrawingAreaController drawingAreaController) {
        // create a frame
        this.mainFrame = new JFrame();
        this.drawAreaModel = drawAreaModel;
        this.mainFrame.setLayout(new BorderLayout());
        this.drawingAreaView = new DrawingArea(drawingAreaController);

        // add key and mouse listeners to the gui.
        this.mainFrame.addKeyListener(new ApplicationHistoryListener(drawingAreaController));
        this.drawingAreaView.addMouseListener(new DrawingAreaMouseListener(drawingAreaController));
        this.drawingAreaView.addMouseMotionListener(new DrawingAreaMouseListener(drawingAreaController));

        // add sub components to the main frame
        mainFrame.setJMenuBar(createJMenuBar(drawingAreaController));
        mainFrame.getContentPane().add(createToolBar(drawingAreaController),BorderLayout.NORTH);
        mainFrame.getContentPane().add(this.drawingAreaView,BorderLayout.CENTER);

        // set model property change listener
        drawAreaModel.addListener(this);

        mainFrame.setVisible(true);
        mainFrame.setMinimumSize(new Dimension(ApplicationConfig.MAIN_FRAME_WIDTH,ApplicationConfig.MAIN_FRAME_HEIGHT));
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.paintAll(mainFrame.getGraphics());
        mainFrame.pack();

    }


    private JMenuBar createJMenuBar(DrawingAreaController controller) {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem saveAs = new JMenuItem("Save As");
        menu.setBackground(Color.ORANGE);

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
        toolBar.setOrientation(SwingConstants.HORIZONTAL);
        FlowLayout toolBarLayout = new FlowLayout();
        toolBarLayout.setHgap(16);
        toolBar.setLayout(toolBarLayout);
        toolBar.add(createColorButton(controller));
        toolBar.add(createFillButton(controller));
        toolBar.add(new JSeparator(SwingConstants.VERTICAL));
        createAllShapeButtons(controller).forEach(toolBar::add);
        toolBar.add(new JSeparator(SwingConstants.VERTICAL));
        toolBar.add(createClearButton(controller));
        toolBar.add(createUndoButton(controller));
        toolBar.add(createRedoButton(controller));
        return toolBar;

    }

    public JButton createColorButton(DrawingAreaController controller){
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
        return colorButton;
    }
    public JButton createUndoButton(DrawingAreaController controller){
        JButton undoButton  = new JButton("Undo");
        undoButton.addActionListener(actionEvent -> {
            controller.controlUndo();
        });
        return undoButton;
    }
    public JButton createRedoButton(DrawingAreaController controller){
        JButton redoButton  = new JButton("Redo");
        redoButton.addActionListener(actionEvent -> {
            controller.controlRedo();
        });
        return redoButton;
    }

    public JButton createClearButton(DrawingAreaController controller){
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(actionEvent -> {
            controller.controlClearShapes();
        });
        return clearButton;
    }

    public JToggleButton createFillButton(DrawingAreaController controller){
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
        return fillButton;
    }

    public ArrayList<JToggleButton> createAllShapeButtons(DrawingAreaController controller) {
        ArrayList<JToggleButton> shapeButtons = new ArrayList<>();
        List<String> shapeName = List.of("Line", "Cross", "Ellipse", "Rectangle","Murray Polygon");
        ActionListener shapeSelectListener = createtoolBarShapeSelectListener(controller);

        shapeName.forEach(name -> {
            JToggleButton button = new JToggleButton(name);
            button.addActionListener(shapeSelectListener);
//            button.setPreferredSize(new Dimension(200,100));
            // make the shape buttons distinct
            button.setBackground(Color.darkGray);
            button.setForeground(Color.WHITE);
            button.setFont(new Font(Font.MONOSPACED,Font.BOLD,14));
            button.setBorder(BorderFactory.createRaisedSoftBevelBorder());
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

    private ActionListener createtoolBarShapeSelectListener(DrawingAreaController controller) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AbstractButton button = (AbstractButton) actionEvent.getSource();
                if (button.isSelected()) {
                    if (controller.hasBluePrint()){
                        button.setSelected(false);
                        JOptionPane.showMessageDialog(mainFrame,"You cannot select more than 1 shape to draw");
                        return;
                    }
                    // this should remove a selected shape and allow for creation of a new shape
                    controller.controlDeselect();
                    // set the blueprint of the shape you want to create
                    controller.controlSetBlueprint(button.getActionCommand().trim().replace(" ",""));
                    // we need to set some sort of info to tell the model that the are going to create a shape
                } else {
                    controller.controlDeselect();
                    controller.controlSetBlueprint(null);
                    // this should allow for selection of existing shapes

                }

            }
        };
    }
}
