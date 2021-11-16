package main.config;

import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Image;
import java.nio.file.Paths;

/**
 * Application Configuration class.
 *
 * @author 210032207
 */
public abstract class ApplicationConfig {
    /**
     * Drawing area background color.
     */
    public static final Color DRAWING_AREA_BACKGROUND = Color.WHITE;
    /**
     * Drawing area height.
     */
    public static final int DRAWING_AREA_HEIGHT = 600;
    /**
     * Drawing area width.
     */
    public static final int DRAWING_AREA_WIDTH = 600;
    /**
     * Main frame height.
     */
    public static final int MAIN_FRAME_HEIGHT = 900;
    /**
     * Main frame width.
     */
    public static final int MAIN_FRAME_WIDTH = 900;
    /**
     * Default shape color.
     */
    public static final Color DEFAULT_SHAPE_COLOR = Color.RED;
    /**
     * Default button icon width.
     */
    public static final int ICON_WIDTH = 40;
    /**
     * Default button icon height.
     */
    public static final int ICON_HEIGHT = 40;

    /**
     * Gets icon for GUI buttons.
     *
     * @param iconName name of icon
     * @return image icon object
     */
    public static ImageIcon getIcon(String iconName) {
        // we want to lock all icons to a specific default size
        String filename = "icons/" + iconName.toLowerCase() + ".png";
        ImageIcon imageIcon = new ImageIcon(Paths.get(filename).toAbsolutePath().toString());

        Image newimg = imageIcon.getImage().getScaledInstance(ICON_WIDTH, ICON_HEIGHT, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newimg);
    }
}
