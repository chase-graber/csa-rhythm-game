package util;
import static com.raylib.Raylib.*;

public class Settings {

    // Utility
    public static boolean DEBUG = false;

    // Key layouts
    public enum KeyLayouts {
        ARROW(new int[]{KEY_UP, KEY_LEFT, KEY_DOWN, KEY_RIGHT}),
        DFJK(new int[]{KEY_D, KEY_F, KEY_J, KEY_K}),
        WASD(new int[]{KEY_W, KEY_A, KEY_S, KEY_D});

        private final int[] keys;

        KeyLayouts(int[] keys) {
            this.keys = keys;
        }

        public int getTrackKey(int track) {
            return keys[track];
        }
    }
    public static KeyLayouts currentKeyLayout = KeyLayouts.ARROW;

    // Key spacing
    public static final int PADDING = 150;
    public static final float SPACING = (float)(GetScreenHeight() - 2 * PADDING) / 3;

    // Level UI
    public static final Vector2 PROGRESS_BAR_DIMENSIONS = new Vector2().x(410).y(45);
    public static final Vector2 PROGRESS_BAR_POSITION = new Vector2().x((GetScreenWidth() - PROGRESS_BAR_DIMENSIONS.x()) / 2).y(25);
    
    private Settings() { } // No making objects of this class
}
