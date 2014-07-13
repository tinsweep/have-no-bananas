package gui;

import java.awt.*;

/**
 * Utility class to hold GUI colors and fonts
 */
public class GUIColors {

    //Colors
    private static final Color BANANA_COLOR = new Color(255, 255, 184);
    private static final Color FONT_BROWN = new Color(76, 54, 33);
    private static Font TITLE_FONT = new Font("Cooper Black", Font.BOLD, 36);
    private static Font HEADING_FONT = new Font("Cooper Black", Font.PLAIN, 18);

    public static Color getBANANA_COLOR() {
        return BANANA_COLOR;
    }

    public static Color getFONT_BROWN(){
        return FONT_BROWN;
    }

    public static Font getTITLE_FONT() {
        return TITLE_FONT;
    }

    public static Font getHEADING_FONT() {
        return HEADING_FONT;
    }
}
