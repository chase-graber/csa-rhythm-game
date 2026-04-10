package core;
import static com.raylib.Raylib.*;

public final class Settings {

    // Screen settings
    public static final int SCREEN_WIDTH = 1080;
    public static final int SCREEN_HEIGHT = 720;

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
    public static KeyLayouts currentKeyLayout = KeyLayouts.DFJK;

    // Key spacing
    public static final int PADDING = 150;
    public static final float SPACING = (float)(SCREEN_HEIGHT - 2 * PADDING) / 3;

    // Level UI
    public static final Vector2 PROGRESS_BAR_DIMENSIONS = new Vector2().x(410).y(45);
    public static final Vector2 PROGRESS_BAR_POSITION = new Vector2().x((SCREEN_WIDTH - PROGRESS_BAR_DIMENSIONS.x()) / 2).y(25);

    // Completion UI
    public static final int COMPLETION_PADDING = 100;

    // Menu UI
    // MenuState
    public static final Vector2 MENU_BUTTON_DIMENSIONS = new Vector2().x(250).y(100);
    public static final float MENU_BUTTON_X = (SCREEN_WIDTH - MENU_BUTTON_DIMENSIONS.x()) / 2;

    // Transition UI
    // Old version, might work on later
//    public static final Vector2 UI_TRANSITION_BOX_DIMENSIONS = new Vector2().x(128).y(128);
//    public static final Vector2 UI_TRANSITION_DIMENSIONS_IN_BOXES = new Vector2()
//            .x((SCREEN_WIDTH / UI_TRANSITION_BOX_DIMENSIONS.x()) + 1)
//            .y((SCREEN_HEIGHT / UI_TRANSITION_BOX_DIMENSIONS.y()) + 1);
    
    private Settings() { } // No making objects of this class
}
