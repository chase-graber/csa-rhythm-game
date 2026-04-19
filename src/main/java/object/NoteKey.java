package object;

import util.AssetLoader;
import core.Settings;

import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

public class NoteKey extends AbstractGameObject {

    // General attributes
    private int key;
    private boolean active;
    private int radius = 40; // Should be double, never refactored display code from when we turned the circles into textures

    // Change when relevant key is pressed
    private float size = 40;
    private Color tint = WHITE;

    // To furthest note to check intersection with
    private Rectangle hitbox;

    public NoteKey(int key) {
        // Determine positioning and texture based on which key is set
        if (key == Settings.currentKeyLayout.getTrackKey(0)) {
            this.position = new Vector2().x(Settings.PADDING).y(Settings.PADDING);
            this.texture = AssetLoader.getTexture("assets/textures/keys/up_arrow_main.png");
        } else if (key == Settings.currentKeyLayout.getTrackKey(1)) {
            this.position = new Vector2().x(Settings.PADDING).y(Settings.PADDING + Settings.SPACING);
            this.texture = AssetLoader.getTexture("assets/textures/keys/left_arrow_main.png");
        } else if (key == Settings.currentKeyLayout.getTrackKey(2)) {
            this.position = new Vector2().x(Settings.PADDING).y(Settings.PADDING + 2 * Settings.SPACING);
            this.texture = AssetLoader.getTexture("assets/textures/keys/down_arrow_main.png");
        } else if (key == Settings.currentKeyLayout.getTrackKey(3)) {
            this.position = new Vector2().x(Settings.PADDING).y(Settings.PADDING + 3 * Settings.SPACING);
            this.texture = AssetLoader.getTexture("assets/textures/keys/right_arrow_main.png");
        }

        // General init code
        this.hitbox = new Rectangle()
                .x(this.position.x() - radius)
                .y(this.position.y() - radius)
                .width(radius * 2)
                .height(radius * 2);
        this.key = key;
    }

    @Override
    public void update(float dt) {
        // Get size and tint closer to default
        size = Lerp(size, radius, 0.25f);
        tint = ColorLerp(tint, WHITE, 0.25f);

        // So that notes know when to delete themselves
        active = IsKeyPressed(key);

        // Visual indicator that note has been pressed so that players don't wonder if their keyboards are working right
        if (active) {
            size = 45;
            tint = GRAY;
        }
    }

    @Override
    public void render() {
        if (position.x() < Settings.SCREEN_WIDTH)
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
