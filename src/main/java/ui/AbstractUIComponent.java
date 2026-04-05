package ui;

import static com.raylib.Raylib.*;

public abstract class AbstractUIComponent {

    protected Vector2 position;
    protected Texture texture;

    public boolean done = false;

    public abstract void update(float dt);
    public abstract void render();
}
