package object;

import util.Settings;

import static com.raylib.Colors.BLUE;
import static com.raylib.Colors.RED;
import static com.raylib.Raylib.*;

public class Note extends GameObject {

    private int track;
    private final int radius = 40;
    private float speed = 500;
    private NoteKey trackKey;

    private Rectangle hitbox;

    public boolean done = false;
    public boolean furthest = false;

    // TODO: Create level class and stop passing in trackKey
    public Note(int track, NoteKey trackKey) {
        this.track = track;
        this.trackKey = trackKey;
        this.position = new Vector2().x(GetScreenWidth() + radius).y(Settings.PADDING + this.track * Settings.SPACING);
        this.hitbox = new Rectangle()
                .x(this.position.x() - radius)
                .y(this.position.y() - radius)
                .width(radius * 2)
                .height(radius * 2);
    }
 
    @Override
    public void update(float dt) {
        position = Vector2Subtract(position, new Vector2().x(speed * dt));
        hitbox.x(position.x() - radius).y(position.y() - radius);
        if (position.x() < -radius || (trackKey.isActive() && CheckCollisionRecs(trackKey.getHitbox(), hitbox) && furthest)) done = true;
    }

    @Override
    public void render() {
        DrawCircleV(position, radius, BLUE);

        if (Settings.DEBUG) DrawRectangleLinesEx(hitbox, 2, RED);
    }

    public int getRadius() {
        return radius;
    }
}
