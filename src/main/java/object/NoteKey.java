package object;

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
                break;
            case KEY_LEFT:
                this.position = new Vector2().x(Settings.PADDING).y(Settings.PADDING + Settings.SPACING);
                break;
            case KEY_DOWN:
                this.position = new Vector2().x(Settings.PADDING).y(Settings.PADDING + 2 * Settings.SPACING);
                break;
            case KEY_RIGHT:
                this.position = new Vector2().x(Settings.PADDING).y(Settings.PADDING + 3 * Settings.SPACING);
                break;
            default:
                this.position = new Vector2().x(0).y(0);
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
        DrawCircleV(position, active ? radius + 5 : radius, active ? GRAY : BLACK);

        // Temp for before we add textures
        switch(key) {
            case KEY_UP:
                DrawText("U", (int)position.x(), (int)position.y(), 20, WHITE);
                break;
            case KEY_LEFT:
                DrawText("L", (int)position.x(), (int)position.y(), 20, WHITE);
                break;
            case KEY_DOWN:
                DrawText("D", (int)position.x(), (int)position.y(), 20, WHITE);
                break;
            case KEY_RIGHT:
                DrawText("R", (int)position.x(), (int)position.y(), 20, WHITE);
                break;
            default:
                break;
        }

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
