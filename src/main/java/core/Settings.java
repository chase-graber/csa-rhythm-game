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
        ARROW("ARROWS", new int[]{KEY_UP, KEY_LEFT, KEY_DOWN, KEY_RIGHT}),
        DFJK("DFJK", new int[]{KEY_D, KEY_F, KEY_J, KEY_K}),
        KJFD("KJFD", new int[]{KEY_K, KEY_J, KEY_F, KEY_D}),
        WASD("WASD", new int[]{KEY_W, KEY_A, KEY_S, KEY_D});

        private final String label;
        private final int[] keys;

        KeyLayouts(String label, int[] keys) {
            this.label = label;
            this.keys = keys;
        }

        public int getTrackKey(int track) {
            return keys[track];
        }

        public String getLabel() {
            return label;
        }

        public static String[] getLabelsAsArray() {
            String[] labels = new String[KeyLayouts.values().length];
            for (int i = 0; i < KeyLayouts.values().length; i++) {
                labels[i] = KeyLayouts.values()[i].getLabel();
            }
            return labels;
        }
    }

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

    // SettingsState
    public static final float SLIDER_TRACK_HEIGHT = 10;
    public static final float SLIDER_RADIUS = 10;
    public static final float SLIDER_PADDING = 15;

    public static final float OPTION_MENU_HEIGHT = 50;
    public static final float OPTION_MENU_SIDE_PADDING = 35;
    public static final float OPTION_MENU_ARROW_SIZE = 20;

    // Values to be messed with in SettingsState
    public static float volume = 1;
    public static KeyLayouts currentKeyLayout = KeyLayouts.DFJK;
    public static int keyLayoutIndex = 1;

    public static void updateVolume(float newVal) {
        volume = newVal / 100;
        SetMasterVolume(volume);
    }

    public static void updateCurrentKeyLayout(KeyLayouts layout) {
        currentKeyLayout = layout;
    }

    // Transition UI
    // Old version, might work on later
//    public static final Vector2 UI_TRANSITION_BOX_DIMENSIONS = new Vector2().x(128).y(128);
//    public static final Vector2 UI_TRANSITION_DIMENSIONS_IN_BOXES = new Vector2()
//            .x((SCREEN_WIDTH / UI_TRANSITION_BOX_DIMENSIONS.x()) + 1)
//            .y((SCREEN_HEIGHT / UI_TRANSITION_BOX_DIMENSIONS.y()) + 1);
    
    private Settings() { } // No making objects of this class
}
