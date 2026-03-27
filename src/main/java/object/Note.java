package object;

import util.AssetLoader;
import util.Settings;

import static com.raylib.Colors.RED; 
import static com.raylib.Colors.WHITE;
import static com.raylib.Raylib.*;

import com.raylib.Raylib.Rectangle;
import com.raylib.Raylib.Vector2;

import scene.LevelScene;

public class Note extends GameObject {

    private int track;
    private final int radius = 40;
    private float speed;

    LevelScene parentScene;
    private NoteKey trackKey;
    private Rectangle hitbox;

    public boolean done = false;
    public boolean furthest = false;

    public Note(int track, float hitTime, LevelScene parentScene, float speed) {
        switch(track) {
            case 0 -> this.texture = AssetLoader.getTexture("assets/textures/up_arrow.png");
            case 1 -> this.texture = AssetLoader.getTexture("assets/textures/left_arrow.png");
            case 2 -> this.texture = AssetLoader.getTexture("assets/textures/down_arrow.png");
            case 3 -> this.texture = AssetLoader.getTexture("assets/textures/right_arrow.png");
            default -> this.texture = AssetLoader.getTexture("assets/textures/up_arrow.png");
        }

        this.track = track;
        this.speed = speed;
        this.parentScene = parentScene;
        this.trackKey = parentScene.getTrackKey(track);
        this.position = new Vector2().x(Settings.PADDING + (3 + hitTime) * speed).y(Settings.PADDING + this.track * Settings.SPACING);
        this.hitbox = new Rectangle()
                .x(this.position.x() - radius)
                .y(this.position.y() - radius)
                .width(radius * 2)
                .height(radius * 2);
    }
 
    @Override
    public void update(float dt) {
        position.x(position.x() - speed * dt);
        hitbox.x(position.x() - radius).y(position.y() - radius);
        if (position.x() < -radius || (trackKey.isActive() && CheckCollisionRecs(trackKey.getHitbox(), hitbox) && furthest)) done = true;
    }

    @Override
    public void render() {
        DrawTexturePro(texture,
            new Rectangle().x(0).y(0).width(texture.width()).height(texture.height()),
            new Rectangle().x(position.x() - radius - 5).y(position.y() - radius - 5).width(2 * radius + 10).height(2 * radius + 10),
            Vector2Zero(),
            0,
            Settings.DEBUG && furthest ? Fade(RED, 0.75f) : WHITE);

        if (Settings.DEBUG) DrawRectangleLinesEx(hitbox, 2, RED);
    }

    public int getRadius() {
        return radius;
    }
}
