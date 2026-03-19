import static com.raylib.Raylib.*;

import java.util.HashMap;
import java.util.Map;

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
}
