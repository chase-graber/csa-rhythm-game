package util;
import static com.raylib.Raylib.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.raylib.Raylib.Music;
import com.raylib.Raylib.Sound;
import com.raylib.Raylib.Texture;

import object.Note;
import scene.LevelScene;

// Loads assets from files, including levels from the .lvl format
public class AssetLoader {

    // Stores all loaded assets mapped to their filepaths so that duplicates aren't created
    public static Map<String, Texture> textures = new HashMap<>();
    public static Map<String, Sound> sounds = new HashMap<>();
    public static Map<String, Music> music = new HashMap<>();

    // Can add to this here to send more things to the level
    public record SceneMetadata(Music song, Texture background) { }

    // Loads textures and applies antialiasing to them
    public static Texture getTexture(String filepath) {
        if (!textures.containsKey(filepath)) {
            textures.put(filepath, LoadTexture(filepath));
            SetTextureFilter(textures.get(filepath), TEXTURE_FILTER_BILINEAR);
        }
        return textures.get(filepath);
    }

    // Loads sounds
    public static Sound getSound(String filepath) {
        if (!sounds.containsKey(filepath)) 
            sounds.put(filepath, LoadSound(filepath));
        return sounds.get(filepath);
    }

    // Loads music
    public static Music getMusic(String filepath) {
        if (!music.containsKey(filepath))
            music.put(filepath, LoadMusicStream(filepath));
        return music.get(filepath);
    }

    // Unloads every loaded object
    public static void cleanup() {
        for (Texture t : textures.values()) UnloadTexture(t);
        for (Sound s : sounds.values()) UnloadSound(s);
        for (Music m : music.values()) UnloadMusicStream(m);
    }

    // Loads a level from our custom .lvl format
    public static LevelScene loadLevelScene(String filepath) {
        // 4 tracks per level
        ArrayList<Note>[] tracks = new ArrayList[]{
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>()
        };

        try {
            // Load level file and create a scanner for it
            File level = new File("assets/levels/" + filepath);
            Scanner input = new Scanner(level);

            Music music = getMusic("assets/sounds/temp_menu_music.mp3"); // Default song is menu music
            Texture background = getTexture("assets/textures/backgrounds/menu_bg.png"); // Default background is menu bg
            float songSpeed = 500; // Default song speed is 500 px/s

            // Create level scene for note parenting
            LevelScene scene = new LevelScene();

            while (input.hasNext()) {
                // Split line data into chunks
                String[] data = input.nextLine().split(" ");
                if (data[0].isEmpty()) continue; // Skip empty lines

                String trackID;
                String lineData;

                // For testing song timings without mapping
                if (data.length == 1) {
                    trackID = "U";
                    lineData = data[0];
                } else {
                    trackID = data[0];
                    lineData = data[1];
                }

                // Add note to it's corresponding track
                switch(trackID) {
                    case "U" -> tracks[0].add(new Note(0, Float.parseFloat(lineData), scene, songSpeed));
                    case "L" -> tracks[1].add(new Note(1, Float.parseFloat(lineData), scene, songSpeed));
                    case "D" -> tracks[2].add(new Note(2, Float.parseFloat(lineData), scene, songSpeed));
                    case "R" -> tracks[3].add(new Note(3, Float.parseFloat(lineData), scene, songSpeed));
                    case "SPEED" -> songSpeed = Float.parseFloat(lineData); // Change speed for following notes
                    case "BACKGROUND" -> background = getTexture("assets/textures/backgrounds/" + lineData);// Override any previously established backgrounds
                    case "MUSIC" -> music = getMusic("assets/sounds/" + lineData); // Override any previously established music
                    default -> tracks[0].add(new Note(0, Float.parseFloat(lineData), scene, songSpeed));
                }
            }

            input.close();

            // Sort notes on each individual track based on hit time for proper furthest checking
            for (ArrayList<Note> track : tracks) {
                Collections.sort(track);
                track.get(0).furthest = true;
            }

            // Send tracks to level scene and return
            scene.applyMetadata(new SceneMetadata(music, background));
            scene.setLevelTracks(tracks);
            return scene;
        } catch (IOException e) {
            // Kill program if level isn't found
            TraceLog(LOG_ERROR, "Unable to load level " + filepath);
            System.exit(1);
        }

        // Will never reach this
        return null;
    }

    private AssetLoader() { } // No making objects of this class
}
