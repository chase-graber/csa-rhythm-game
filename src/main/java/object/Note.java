package object;

import util.AssetLoader;
import core.Settings;

import static com.raylib.Colors.RED; 
import static com.raylib.Colors.WHITE;
import static com.raylib.Raylib.*;

import com.raylib.Raylib.Rectangle;
import com.raylib.Raylib.Vector2;

import scene.LevelScene;
import ui.level.UITextPopup;

// Notes to be hit by NoteKeys
public class Note extends AbstractGameObject implements Comparable<Note> {

    // Positioning and display
    private int track;
    private final int radius = 40;
    private float speed;
    private final float hitTime;

    // References to parent scene and the note's track key
    LevelScene parentScene;
    private NoteKey trackKey;

    // Hitboxes (hitbox is dest rect for drawing, others used for scoring)
    private Rectangle hitbox;
    private Rectangle[] scoreHitboxes;

    // Can you hit it?
    public boolean furthest = false;

    // Delete or no?
    public boolean done = false;

    public Note(int track, float hitTime, LevelScene parentScene, float speed) {
        // Get texture depending on which track the note is on
        switch(track) {
            case 0 -> this.texture = AssetLoader.getTexture("assets/textures/keys/up_arrow.png");
            case 1 -> this.texture = AssetLoader.getTexture("assets/textures/keys/left_arrow.png");
            case 2 -> this.texture = AssetLoader.getTexture("assets/textures/keys/down_arrow.png");
            case 3 -> this.texture = AssetLoader.getTexture("assets/textures/keys/right_arrow.png");
        }

        // General init code
        this.track = track;
        this.speed = speed;
        this.hitTime = hitTime;
        this.parentScene = parentScene;
        this.trackKey = parentScene.getTrackKey(track);
        this.position = new Vector2().x(Settings.PADDING + (3 + hitTime) * speed).y(Settings.PADDING + this.track * Settings.SPACING);
        this.hitbox = new Rectangle()
                .x(this.position.x() - radius)
                .y(this.position.y() - radius)
                .width(radius * 2)
                .height(radius * 2);

        this.scoreHitboxes = new Rectangle[]{
                new Rectangle()
                        .x(this.position.x() - radius)
                        .y(this.position.y() - radius)
                        .width((float)(radius * 2) / 3)
                        .height(radius * 2),
                new Rectangle()
                        .x(this.position.x() - (float)radius / 3)
                        .y(this.position.y() - radius)
                        .width((float)(radius * 2) / 3)
                        .height(radius * 2),
                new Rectangle()
                        .x(this.position.x() + (float)radius / 3)
                        .y(this.position.y() - radius)
                        .width((float)(radius * 2) / 3)
                        .height(radius * 2)
        };
    }

 
    @Override
    public void update(float dt) {
        // Update texture and hitbox positions
        position.x(position.x() - speed * dt);

        hitbox.x(hitbox.x() - speed * dt);
        for (Rectangle scoreHitbox : scoreHitboxes) {
            scoreHitbox.x(scoreHitbox.x() - speed * dt);
        }

        // Check if note has gone offscreen
        if (position.x() < -radius) {
            // Delete, add miss
            done = true;
            parentScene.addUIComponent(new UITextPopup(track, 0));
            parentScene.numScoreTypes[0]++;
        } else if (trackKey.isActive() && CheckCollisionRecs(trackKey.getHitbox(), hitbox) && furthest) { // Check if note has been hit by its NoteKey
            // Count how many hitboxes were hit for scoring
            int numHitboxesHit = 0;
            for (Rectangle hitbox : scoreHitboxes) {
                if (CheckCollisionRecs(trackKey.getHitbox(), hitbox)) numHitboxesHit++;
            }

            // Increment relevant score counter
            parentScene.numScoreTypes[numHitboxesHit]++;

            // Delete, add relevant text popup
            done = true;
            parentScene.addUIComponent(new UITextPopup(track, numHitboxesHit));
        }
    }

    @Override
    public void render() {
        DrawTexturePro(texture,
            new Rectangle().x(0).y(0).width(texture.width()).height(texture.height()),
            new Rectangle().x(position.x() - radius - 5).y(position.y() - radius - 5).width(2 * radius + 10).height(2 * radius + 10),
            Vector2Zero(),
            0,
            Settings.DEBUG && furthest ? Fade(RED, 0.75f) : WHITE);

        if (Settings.DEBUG) {
            DrawRectangleLinesEx(hitbox, 2, RED);
            for (Rectangle hitbox : scoreHitboxes)
                DrawRectangleLinesEx(hitbox, 2, RED);
        }
    }

    // For sorting keys by hit time on level load
    @Override
    public int compareTo(Note o) {
        return Float.compare(this.hitTime, o.getHitTime());
    }

    public float getHitTime() { return hitTime; }

    public int getRadius() {
        return radius;
    }
}
