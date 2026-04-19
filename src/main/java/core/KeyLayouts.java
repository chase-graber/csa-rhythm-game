package core;

import static com.raylib.Raylib.*;

// Key layouts
public enum KeyLayouts {
    ARROW("ARROWS", new int[]{KEY_UP, KEY_LEFT, KEY_DOWN, KEY_RIGHT}),
    DFJK("DFJK", new int[]{KEY_D, KEY_F, KEY_J, KEY_K}),
    KJFD("KJFD", new int[]{KEY_K, KEY_J, KEY_F, KEY_D}),
    WASD("WASD", new int[]{KEY_W, KEY_A, KEY_S, KEY_D});

    private final String label; // Display label for settings page
    private final int[] keys; // Each key to be pressed, in order { UP, DOWN, LEFT, RIGHT }

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

    // Sets default layout from saved setting
    public static KeyLayouts getLayoutFromLabel(String label) {
        for (KeyLayouts kl : KeyLayouts.values()) {
            if (kl.getLabel().equals(label)) return kl;
        }
        return ARROW;
    }

    // Returns the array of labels for display
    public static String[] getLabelsAsArray() {
        String[] labels = new String[KeyLayouts.values().length];
        for (int i = 0; i < KeyLayouts.values().length; i++) {
            labels[i] = KeyLayouts.values()[i].getLabel();
        }
        return labels;
    }
}
