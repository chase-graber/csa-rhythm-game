package object;

import util.Settings;

import static com.raylib.Colors.BLUE;
import static com.raylib.Raylib.*;

public class Note extends GameObject {

    private int track;
    private final int radius = 40;
    private float speed = 500;

    public boolean done = false;

    public Note(int track) {
        this.track = track;
        this.position = new Vector2().x(GetScreenWidth() + radius).y(Settings.PADDING + this.track * Settings.SPACING);
    }
 
    @Override
    public void update(float dt) {
        position = Vector2Subtract(position, new Vector2().x(speed * dt));
        if (position.x() < -radius) done = true;
    }

    @Override
    public void render() {
        DrawCircleV(position, radius, BLUE);
    }
}
