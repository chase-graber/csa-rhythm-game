package scene;

import core.TickableGameEntity;
import ui.AbstractUIComponent;

import java.util.ArrayList;

import static com.raylib.Raylib.*;

public abstract class AbstractScene implements TickableGameEntity {

    protected Music music;
    protected Texture background;

    protected ArrayList<AbstractUIComponent> uiComponents = new ArrayList<>();

    public void addUIComponent(AbstractUIComponent auic) {
        uiComponents.add(auic);
    }

    public void onTransition() {
        StopMusicStream(music);
    }
}
