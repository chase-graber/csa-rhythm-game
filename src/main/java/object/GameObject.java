package object;

import static com.raylib.Raylib.*;

public abstract class GameObject {

    protected Vector2 position;
    protected Texture texture;

    public abstract void update(float dt);
    public abstract void render();

    public Vector2 getPosition() { return position; }
}