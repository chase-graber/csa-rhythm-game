package object;

import core.TickableGameEntity;

import static com.raylib.Raylib.*;

// Game object parent class
public abstract class AbstractGameObject implements TickableGameEntity {

    protected Vector2 position;
    protected Texture texture;

    public Vector2 getPosition() { return position; }
}