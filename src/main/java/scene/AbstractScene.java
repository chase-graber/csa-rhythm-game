package scene;

import ui.AbstractUIComponent;

import java.util.ArrayList;

import static com.raylib.Raylib.*;

public abstract class AbstractScene {

    protected Music music;
    protected Texture background;

    protected ArrayList<AbstractUIComponent> uiComponents = new ArrayList<>();

    public void addUIComponent(AbstractUIComponent auic) {
        uiComponents.add(auic);
    }

    public abstract void update(float dt);
    public abstract void render();
}
