package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

// Settings saving and loading to keep beyond each game instance
public class Initializer {

    // Save current settings to text file settings.txt
    public static void saveSettings(boolean overwriteIgThisParameterIsOnlyHereBcAConsumerNeedsItToNotCrash) {
        try {
            FileWriter writer = new FileWriter("assets/settings.txt");

            writer.write(Settings.volume + "\n");
            writer.write(Settings.currentKeyLayout.getLabel());

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load settings from settings.txt on startup or set to defaults if file not found
    public static void loadSettings() {
        try {
            Scanner scanner = new Scanner(new File("assets/settings.txt"));

            Settings.updateVolume(Float.parseFloat(scanner.nextLine()) * 100);
            Settings.updateCurrentKeyLayout(KeyLayouts.getLayoutFromLabel(scanner.nextLine()));

            scanner.close();
        } catch (FileNotFoundException e) {
            Settings.volume = 1.0f;
            Settings.currentKeyLayout = KeyLayouts.ARROW;
        } finally {
            Settings.keyLayoutIndex = Settings.currentKeyLayout.ordinal();
        }
    }

    private Initializer() { } // No making objects of this class
}
