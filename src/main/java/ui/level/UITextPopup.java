package ui.level;

import static com.raylib.Colors.WHITE;
import static com.raylib.Raylib.*;

import ui.AbstractUIComponent;
import util.AssetLoader;
import core.Settings;
import util.UtilMethods;

// Perfect, Good, Poor, Miss text that shows up on note deletion
public class UITextPopup extends AbstractUIComponent {

    // Scaling stuff bc it looks cool
    private float scale = 0.01f;
    private float scaleSpeed = 0.065f;
    private final float scaleAccel = -0.005f;
    private float rotation = 0.0f;
    private final float rotSpeed;

    public UITextPopup(int track, int score) {
        this.texture = AssetLoader.getTexture("assets/textures/ui/textPopup_" + score + ".png"); // Get right texture based on scoring type
        this.position = new Vector2().x(Settings.PADDING - 90).y(Settings.PADDING + (Settings.SPACING * track) - 20.0f); // Put behind the key so that it isn't in the way

        this.rotSpeed = (float)Math.random() - 0.5f; // Random rotation speed for variety
    }

    public void update(float dt) {
        // This is calculus. No I will not elaborate.
        scaleSpeed += scaleAccel;
        scale += scaleSpeed;

        rotation += rotSpeed;

        // Set deletion flag after it can't be seen
        if (scale <= 0.0f) done = true;
    }

    public void render() {
        DrawTexturePro(
                texture,
                UtilMethods.getTextureRect(texture),
                UtilMethods.scaleRectangleAroundCenter(UtilMethods.getTextureRect(texture, position), scale), // Texture is scaled around it's center
                UtilMethods.getTextureMiddle(texture), // Texture is offset by its center (lowk bugged, don't know why, won't change it because it looks cooler bugged than it would working right)
                rotation,
                WHITE);
    }
}
