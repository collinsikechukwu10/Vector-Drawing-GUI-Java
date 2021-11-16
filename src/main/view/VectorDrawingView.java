package main.view;

import main.config.ApplicationConfig;
import main.controller.DrawAreaController;
import main.model.DrawAreaModel;
import main.view.listeners.DrawIOListener;
import main.view.listeners.HistoryListener;
import main.view.listeners.DrawingAreaMouseListener;

import javax.swing.JToolBar;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.AbstractButton;
import javax.swing.JOptionPane;
import javax.swing.JColorChooser;
import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * Vector Drawing GUI View.
 * This class renders the GUI application for drawing shapes.
 * It implements the PropertyChangeListener to watch for changes in the model.
 * It also makes changes to the model through the controller.
 *
 * @author 210032207
 */
public class VectorDrawingView implements PropertyChangeListener {
    private final JFrame mainFrame;
    private final DrawingArea drawingAreaView;
    private final String multipleShapesSelectedErrorMessage = "You cannot select more than 1 shape to draw";

    /**
     * Constructor to generate view using the draw area model and controller.
     *
     * @param drawAreaModel      model to watch for property changes
     * @param drawAreaController controller to manipulate model
     */
    public VectorDrawingView(DrawAreaModel drawAreaModel, DrawAreaController drawAreaController) {
        // create a frame
        mainFrame = new JFrame();
        mainFrame.setLayout(new BorderLayout());
        this.drawingAreaView = new DrawingArea(drawAreaController);

        // add key and mouse listeners to the gui.
        mainFrame.addKeyListener(new HistoryListener(drawAreaController));
        drawingAreaView.addMouseListener(new DrawingAreaMouseListener(drawAreaController));
        drawingAreaView.addMouseMotionListener(new DrawingAreaMouseListener(drawAreaController));

        // add sub components to the main frame
        mainFrame.getContentPane().add(createToolBar(drawAreaController), BorderLayout.NORTH);
        mainFrame.getContentPane().add(new JScrollPane(
                drawingAreaView,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        ), BorderLayout.CENTER);

        // set model property change listener
        drawAreaModel.addListener(this);

        mainFrame.setVisible(true);
        mainFrame.setMinimumSize(new Dimension(ApplicationConfig.MAIN_FRAME_WIDTH, ApplicationConfig.MAIN_FRAME_HEIGHT));
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.paintAll(mainFrame.getGraphics());
        mainFrame.pack();

    }

    /**
     * Get drawing panel view.
     *
     * @return drawing panel view
     */
    public DrawingArea getDrawingAreaView() {
        return drawingAreaView;
    }

    /**
     * Creates toolbar that houses all the action buttons.
     *
     * @param controller drawing area controller
     * @return toolbar
     */
    private JToolBar createToolBar(DrawAreaController controller) {
        JToolBar toolBar = new JToolBar();
        toolBar.setOrientation(SwingConstants.HORIZONTAL);
        FlowLayout toolBarLayout = new FlowLayout();
        toolBarLayout.setHgap(8);
        toolBar.setLayout(toolBarLayout);
        // add all the required buttons
        toolBar.add(createLoadButton(controller));
        toolBar.add(createSaveButton(controller));
        toolBar.add(createColorButton(controller));
        toolBar.add(createFillButton(controller));
        toolBar.add(new JSeparator(SwingConstants.VERTICAL));
        toolBar.add(createClearButton(controller));
        toolBar.add(createUndoButton(controller));
        toolBar.add(createRedoButton(controller));
        toolBar.add(new JSeparator(SwingConstants.VERTICAL));
        createAllShapeButtons(controller).forEach(toolBar::add);
        return toolBar;

    }

    /**
     * Creates load button for importing saved drawings.
     *
     * @param controller drawing area controller
     * @return load button
     */
    public JButton createLoadButton(DrawAreaController controller) {
        JButton loadButton = new JButton("Load");

        loadButton.setIcon(ApplicationConfig.getIcon(loadButton.getText()));
        DrawIOListener drawIOListener = new DrawIOListener(controller);
        loadButton.addActionListener(drawIOListener.fileLoadListener(mainFrame));
        return loadButton;
    }

    /**
     * Creates save button for exporting drawings to a file.
     *
     * @param controller drawing area controller
     * @return save button
     */
    public JButton createSaveButton(DrawAreaController controller) {
        JButton saveButton = new JButton("Save");
        DrawIOListener drawIOListener = new DrawIOListener(controller);
        saveButton.setIcon(ApplicationConfig.getIcon(saveButton.getText()));
        saveButton.addActionListener(drawIOListener.fileSaveListener(mainFrame));
        return saveButton;
    }

    /**
     * Creates the color button which controls the color used to draw the shapes.
     *
     * @param controller drawing area controller
     * @return color button
     */
    public JButton createColorButton(DrawAreaController controller) {
        JButton colorButton = new JButton("Color");
        System.out.println(colorButton.getText());
        colorButton.setIcon(ApplicationConfig.getIcon(colorButton.getText()));
        colorButton.setBackground(ApplicationConfig.DEFAULT_SHAPE_COLOR);
        colorButton.addActionListener(actionEvent -> {
            Color color = JColorChooser.showDialog(mainFrame, "Choose a color", ApplicationConfig.DEFAULT_SHAPE_COLOR);
            if (controller.getSelectedShape() != null) {
                controller.controlSelectedShapeColor(color);
                controller.controlDeselect();
            } else {
                controller.controlColor(color);
            }
            colorButton.setBackground(color);
            colorButton.setForeground((color.equals(Color.BLACK)) ? Color.WHITE : Color.BLACK);

        });
        return colorButton;
    }

    /**
     * Creates the undo button.
     *
     * @param controller drawing area controller
     * @return undo button
     */
    public JButton createUndoButton(DrawAreaController controller) {
        JButton undoButton = new JButton("Undo");
        undoButton.setIcon(ApplicationConfig.getIcon(undoButton.getText()));
        undoButton.setToolTipText("Undo");
        undoButton.addActionListener(actionEvent -> controller.controlUndo());
        return undoButton;
    }

    /**
     * Creates the redo button.
     *
     * @param controller drawing area controller.
     * @return redo button
     */
    public JButton createRedoButton(DrawAreaController controller) {
        JButton redoButton = new JButton("Redo");
        redoButton.setIcon(ApplicationConfig.getIcon(redoButton.getText()));
        redoButton.addActionListener(actionEvent -> {
            controller.controlRedo();
        });
        return redoButton;
    }

    /**
     * Creates the clear button for removing drawn shapes from the drawing area.
     *
     * @param controller drawing area controller
     * @return clear button
     */
    public JButton createClearButton(DrawAreaController controller) {
        JButton clearButton = new JButton("Clear");
        clearButton.setIcon(ApplicationConfig.getIcon(clearButton.getText()));
        clearButton.addActionListener(actionEvent -> {
            controller.controlClearShapes();
        });
        return clearButton;
    }

    /**
     * Creates the fill button with fills up subsequently created shapes.
     *
     * @param controller drawing area controller
     * @return fill button
     */
    public JToggleButton createFillButton(DrawAreaController controller) {
        JToggleButton fillButton = new JToggleButton("Fill");
        fillButton.setIcon(ApplicationConfig.getIcon(fillButton.getText()));
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

    /**
     * Creates all the buttons needed to generate all the shapes.
     *
     * @param controller drawing area controller
     * @return list of shape buttons
     */
    public ArrayList<JToggleButton> createAllShapeButtons(DrawAreaController controller) {
        ArrayList<JToggleButton> shapeButtons = new ArrayList<>();
        List<String> shapeName = List.of("Line", "Cross", "Ellipse", "Rectangle", "Murray Polygon");
        ActionListener shapeSelectListener = createToolBarShapeSelectListener(controller);

        shapeName.forEach(name -> {
            JToggleButton button = new JToggleButton(name);
            button.addActionListener(shapeSelectListener);
            button.setBorder(BorderFactory.createLoweredSoftBevelBorder());
            button.setIcon(ApplicationConfig.getIcon(name));
            button.setBackground(Color.PINK.darker());
            button.setForeground(Color.WHITE);
            shapeButtons.add(button);

        });
        return shapeButtons;
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        SwingUtilities.invokeLater(() -> {
            System.out.println("[" + event.getPropertyName() + "] old: " + event.getOldValue() + ", new: " + event.getNewValue());
            getDrawingAreaView().repaint();
        });
    }

    /**
     * Creates the toolbar action listener for setting blueprint of shape to create.
     *
     * @param controller drawing area controller
     * @return shape select action listener
     */
    private ActionListener createToolBarShapeSelectListener(DrawAreaController controller) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AbstractButton button = (AbstractButton) actionEvent.getSource();
                if (button.isSelected()) {
                    if (controller.hasBluePrint()) {
                        button.setSelected(false);
                        JOptionPane.showMessageDialog(mainFrame, multipleShapesSelectedErrorMessage);
                        return;
                    }
                    // this should remove a selected shape and allow for creation of a new shape
                    controller.controlDeselect();
                    // set the blueprint of the shape you want to create
                    controller.controlSetBlueprint(button.getActionCommand().trim().replace(" ", ""));
                } else {
                    controller.controlDeselect();
                    controller.controlSetBlueprint(null);
                }

            }
        };
    }
}
