package util;
import static com.raylib.Raylib.GetScreenHeight;

public class Settings {

    // Utility
    public static boolean DEBUG = true;

    // Key spacing
    public static final int PADDING = 150;
    public static final float SPACING = (float)(GetScreenHeight() - 2 * PADDING) / 3;
    
    private Settings() { } // No making objects of this class
}
