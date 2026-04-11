package util;
import static com.raylib.Raylib.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.raylib.Raylib.Image;
import com.raylib.Raylib.Music;
import com.raylib.Raylib.Sound;
import com.raylib.Raylib.Texture;

import object.Note;
import scene.LevelScene;

public class AssetLoader {

    public static Map<String, Texture> textures = new HashMap<>();
    public static Map<String, Sound> sounds = new HashMap<>();
    public static Map<String, Music> music = new HashMap<>();

    public record LevelMetadata(Music song, Texture background) { }
    
    public static Texture getTexture(String filepath) {
        if (!textures.containsKey(filepath)) {
            Image temp = LoadImage(filepath);
            textures.put(filepath, LoadTextureFromImage(temp));
            SetTextureFilter(textures.get(filepath), TEXTURE_FILTER_BILINEAR);
            UnloadImage(temp);
        }
        return textures.get(filepath);
    }

    public static Sound getSound(String filepath) {
        if (!sounds.containsKey(filepath)) 
            sounds.put(filepath, LoadSound(filepath));
        return sounds.get(filepath);
    }

    public static Music getMusic(String filepath) {
        if (!music.containsKey(filepath))
            music.put(filepath, LoadMusicStream(filepath));
        return music.get(filepath);
    }

    public static void cleanup() {
        for (Texture t : textures.values()) UnloadTexture(t);
        for (Sound s : sounds.values()) UnloadSound(s);
        for (Music m : music.values()) UnloadMusicStream(m);
    }

    public static LevelScene loadLevelScene(String filepath) {
        ArrayList<Note>[] tracks = new ArrayList[]{
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>()
        };

        try {
            File level = new File("assets/levels/" + filepath);
            Scanner input = new Scanner(level);

            // First line always song path
            // Second line always bg path
            LevelMetadata md = new LevelMetadata(
                    getMusic("assets/sounds/" + input.nextLine()),
                    getTexture("assets/textures/backgrounds/" + input.nextLine()));
            input.nextLine(); // Cross gap between config and data

            float songSpeed = 500; // Third line always song speed (default/1x is 500)

            LevelScene scene = new LevelScene(md);

            while (input.hasNext()) {
                String[] data = input.nextLine().split(" ");
                if (data[0].isEmpty()) continue;
                String trackID;
                float noteData;

                // For testing song timings without mapping
                if (data.length == 1) {
                    trackID = "U";
                    noteData = Float.parseFloat(data[0]);
                } else {
                    trackID = data[0];
                    noteData = Float.parseFloat(data[1]);
                }

                switch(trackID) {
                    case "U":
                        tracks[0].add(new Note(0, noteData, scene, songSpeed));
                        if (tracks[0].size() == 1) tracks[0].get(0).furthest = true;
                        break;
                    case "L":
                        tracks[1].add(new Note(1, noteData, scene, songSpeed));
                        if (tracks[1].size() == 1) tracks[1].get(0).furthest = true;
                        break;
                    case "D":
                        tracks[2].add(new Note(2, noteData, scene, songSpeed));
                        if (tracks[2].size() == 1) tracks[2].get(0).furthest = true;
                        break;
                    case "R":
                        tracks[3].add(new Note(3, noteData, scene, songSpeed));
                        if (tracks[3].size() == 1) tracks[3].get(0).furthest = true;
                        break;
                    case "SPEED":
                        songSpeed = noteData;
                        break;
                    default:
                        tracks[0].add(new Note(0, noteData, scene, songSpeed));
                        if (tracks[0].size() == 1) tracks[0].get(0).furthest = true;
                        break;
                }
            }

            input.close();

            scene.setLevelTracks(tracks);
            return scene;
        } catch (IOException e) {
            TraceLog(LOG_ERROR, "Unable to load level " + filepath);
            System.exit(1);
        }

        return null;
    }

    private AssetLoader() { } // No making objects of this class
}
