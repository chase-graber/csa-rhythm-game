package scene;

import static com.raylib.Raylib.*;

public abstract class AbstractScene {

    protected Music music;
    protected Texture background;

    public abstract void update(float dt);
    public abstract void render();
}
