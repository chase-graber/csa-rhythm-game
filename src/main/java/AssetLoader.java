import static com.raylib.Raylib.LoadImage;
import static com.raylib.Raylib.LoadSound;
import static com.raylib.Raylib.LoadTextureFromImage;
import static com.raylib.Raylib.UnloadImage;

import java.util.HashMap;
import java.util.Map;

import com.raylib.Raylib.Image;
import com.raylib.Raylib.Sound;
import com.raylib.Raylib.Texture;

public class AssetLoader {

    public static Map<String, Texture> textures = new HashMap<>();
    public static Map<String, Sound> sounds = new HashMap<>();

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
}
