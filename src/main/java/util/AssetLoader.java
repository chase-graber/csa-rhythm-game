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

    private AssetLoader() { } // No making objects of this class
    
    public static Texture getTexture(String filepath) {
        if (!textures.containsKey(filepath)) {
            Image temp = LoadImage(filepath);
            textures.put(filepath, LoadTextureFromImage(temp));
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

    public static LevelScene loadLevelScene(String filepath, Settings.KeyLayouts keyLayout) {
        ArrayList<Note>[] tracks = new ArrayList[]{
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>()
        };

        try {
            File level = new File(filepath);
            Scanner input = new Scanner(level);
            String songpath = "assets/sounds/" + input.nextLine(); // First line always song path
            String bgpath = "assets/textures/" + input.nextLine(); // Second line always bg path
            LevelScene scene = new LevelScene(songpath, bgpath, keyLayout);

            while (input.hasNext()) {
                String[] data = input.nextLine().split(" ");
                String trackID = data[0];
                float noteTime = Float.parseFloat(data[1]);

                switch(trackID) {
                    case "U":
                        tracks[0].add(new Note(0, noteTime, scene));
                        if (tracks[0].size() == 1) tracks[0].get(0).furthest = true;
                        break;
                    case "L":
                        tracks[1].add(new Note(1, noteTime, scene));
                        if (tracks[1].size() == 1) tracks[1].get(0).furthest = true;
                        break;
                    case "D":
                        tracks[2].add(new Note(2, noteTime, scene));
                        if (tracks[2].size() == 1) tracks[2].get(0).furthest = true;
                        break;
                    case "R":
                        tracks[3].add(new Note(3, noteTime, scene));
                        if (tracks[3].size() == 1) tracks[3].get(0).furthest = true;
                        break;
                    default:
                        tracks[0].add(new Note(0, noteTime, scene));
                        if (tracks[0].size() == 1) tracks[0].get(0).furthest = true;
                        break;
                }
            }

            input.close();

            scene.setLevelTracks(tracks);
            return scene;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }
}
