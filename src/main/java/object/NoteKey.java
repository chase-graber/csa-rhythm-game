package object;

import util.AssetLoader;
import util.Settings;

import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

public class NoteKey extends GameObject {

    private int key;
    private boolean active;
    private int radius = 40;

    private Rectangle hitbox;

    public NoteKey(int key) {
        switch(key) {
            case KEY_UP:
                this.position = new Vector2().x(Settings.PADDING).y(Settings.PADDING);
                this.texture = AssetLoader.getTexture("assets/textures/up_arrow_main.png");
                break;
            case KEY_LEFT:
                this.position = new Vector2().x(Settings.PADDING).y(Settings.PADDING + Settings.SPACING);
                this.texture = AssetLoader.getTexture("assets/textures/left_arrow_main.png");
                break;
            case KEY_DOWN:
                this.position = new Vector2().x(Settings.PADDING).y(Settings.PADDING + 2 * Settings.SPACING);
                this.texture = AssetLoader.getTexture("assets/textures/down_arrow_main.png");
                break;
            case KEY_RIGHT:
                this.position = new Vector2().x(Settings.PADDING).y(Settings.PADDING + 3 * Settings.SPACING);
                this.texture = AssetLoader.getTexture("assets/textures/right_arrow_main.png");
                break;
            default:
                this.position = new Vector2().x(0).y(0);
                this.texture = AssetLoader.getTexture("assets/textures/up_arrow_main.png");
                break;
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
        active = IsKeyPressed(key);
    }

    @Override
    public void render() {
        DrawTexturePro(texture,
            new Rectangle().x(0).y(0).width(texture.width()).height(texture.height()),
            new Rectangle().x(position.x() - radius - 5).y(position.y() - radius - 5).width(2 * radius + 10).height(2 * radius + 10),
            Vector2Zero(),
            0,
            active ? GRAY : WHITE);

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
