package ui;

import static com.raylib.Colors.WHITE;
import static com.raylib.Raylib.*;

import util.AssetLoader;
import util.Settings;

public class UITextPopup extends AbstractUIComponent {

    private float scale = 0.01f;
    private float scaleSpeed = 0.065f;
    private final float scaleAccel = -0.005f;
    private float rotation = 0.0f;
    private final float rotSpeed;

    public UITextPopup(int track, int score) {
        this.texture = AssetLoader.getTexture("assets/textures/ui/textPopup_" + score + ".png");
        this.position = new Vector2().x(Settings.PADDING - 110).y(Settings.PADDING + (Settings.SPACING * track) - 20.0f);

        this.rotSpeed = (float)Math.random() - 0.5f;
    }

    public void update(float dt) {
        scaleSpeed += scaleAccel;
        scale += scaleSpeed;
        rotation += rotSpeed;

        if (scale <= 0.0f) done = true;
    }

    public void render() {
        DrawTextureEx(texture, position, rotation, scale, WHITE);
    }
}
