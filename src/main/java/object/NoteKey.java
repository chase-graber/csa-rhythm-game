package object;

import static com.raylib.Colors.BLACK;
import static com.raylib.Colors.GRAY;
import static com.raylib.Raylib.*;

public class NoteKey extends GameObject {

    private int key;
    private boolean active;

    public NoteKey(int key) {
        switch(key) {
            case KEY_UP:
                this.position = new Vector2().x(150).y(150);
                break;
            case KEY_LEFT:
                this.position = new Vector2().x(150).y(290);
                break;
            case KEY_RIGHT:
                this.position = new Vector2().x(150).y(420);
                break;
            case KEY_DOWN:
                this.position = new Vector2().x(150).y(570);
                break;
            default:
                this.position = new Vector2().x(0).y(0);
                break;
        }
        this.key = key;
    }

    @Override
    public void update(float dt) {
        active = IsKeyDown(key);
    }

    @Override
    public void render() {
        DrawCircleV(position, active ? 45 : 40, active ? GRAY : BLACK);
    }
}
