package config;

import javax.swing.ImageIcon;
import java.awt.*;
import java.nio.file.Paths;

public abstract class ApplicationConfig {

    public static final Color DRAWING_AREA_BACKGROUND = Color.WHITE;
    public static final int DRAWING_AREA_HEIGHT = 600;
    public static final int DRAWING_AREA_WIDTH = 600;
    public static final int MAIN_FRAME_HEIGHT = 1000;
    public static final int MAIN_FRAME_WIDTH = 1000;
    public static final Color DEFAULT_SHAPE_COLOR = Color.BLACK;
    public static final int ICON_WIDTH = 40;
    public static final int ICON_HEIGHT = 40;

    public static ImageIcon getIcon(String iconName){
        // we want to lock all icons to a specific default size
        String filename = "icons/" + iconName.toLowerCase() + ".svg";
        ImageIcon imageIcon =  new ImageIcon(Paths.get(filename).toAbsolutePath().toString());
        Image newimg = imageIcon.getImage().getScaledInstance(ICON_WIDTH, ICON_HEIGHT, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newimg);
    }
}
