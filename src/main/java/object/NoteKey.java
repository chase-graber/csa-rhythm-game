package object;

import util.AssetLoader;
import util.Settings;

import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

public class NoteKey extends GameObject {

    private int key;
    private boolean active;
    private int radius = 40;
    private float size = 40;
    private Color tint = WHITE;

    private Rectangle hitbox;

    public NoteKey(int key) {
        if (key == Settings.currentKeyLayout.getTrackKey(0)) {
            this.position = new Vector2().x(Settings.PADDING).y(Settings.PADDING);
            this.texture = AssetLoader.getTexture("assets/textures/up_arrow_main.png");
        } else if (key == Settings.currentKeyLayout.getTrackKey(1)) {
            this.position = new Vector2().x(Settings.PADDING).y(Settings.PADDING + Settings.SPACING);
            this.texture = AssetLoader.getTexture("assets/textures/left_arrow_main.png");
        } else if (key == Settings.currentKeyLayout.getTrackKey(2)) {
            this.position = new Vector2().x(Settings.PADDING).y(Settings.PADDING + 2 * Settings.SPACING);
            this.texture = AssetLoader.getTexture("assets/textures/down_arrow_main.png");
        } else if (key == Settings.currentKeyLayout.getTrackKey(3)) {
            this.position = new Vector2().x(Settings.PADDING).y(Settings.PADDING + 3 * Settings.SPACING);
            this.texture = AssetLoader.getTexture("assets/textures/right_arrow_main.png");
        } else {
            this.position = new Vector2().x(0).y(0);
            this.texture = AssetLoader.getTexture("assets/textures/up_arrow_main.png");
        }

        this.hitbox = new Rectangle()
                .x(this.position.x() - radius)
                .y(this.position.y() - radius)
                .width(radius * 2)
                .height(radius * 2);
        this.key = key;
    }

    @Override
    public void update(float dt) {
        size = Lerp(size, radius, 0.25f);
        tint = ColorLerp(tint, WHITE, 0.25f);

        active = IsKeyPressed(key);
        if (active) {
            size = 45;
            tint = GRAY;
        }
    }

    @Override
    public void render() {
        if (position.x() < GetScreenWidth())
            DrawTexturePro(texture,
                new Rectangle().x(0).y(0).width(texture.width()).height(texture.height()),
                new Rectangle().x(position.x() - size).y(position.y() - size).width(2 * size).height(2 * size),
                Vector2Zero(),
                0,
                tint);

        // Debug drawing
        if (Settings.DEBUG) DrawRectangleLinesEx(hitbox, 2, RED);
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public boolean isActive() {
        return active;
    }

    public int getRadius() {
        return radius;
    }
}
