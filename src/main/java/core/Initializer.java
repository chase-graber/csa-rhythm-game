package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Initializer {

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

    private Initializer() { }
}
