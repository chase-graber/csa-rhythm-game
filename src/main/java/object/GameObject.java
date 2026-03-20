package object;

import com.raylib.Raylib.Vector2;

public abstract class GameObject {

    protected Vector2 position;

    public abstract void update(float dt);
    public abstract void render();

    public Vector2 getPosition() { return position; }
}