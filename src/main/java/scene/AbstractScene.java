package scene;

import core.TickableGameEntity;
import ui.AbstractUIComponent;

import java.util.ArrayList;

import static com.raylib.Raylib.*;

// Every scene in the game extends this, can be swapped with Game.transitionToNextScene(AbstractScene scene)
public abstract class AbstractScene implements TickableGameEntity {

    protected Music music;
    protected Texture background;

    protected ArrayList<AbstractUIComponent> uiComponents = new ArrayList<>();

    // So that other non-scene classes can add components
    public void addUIComponent(AbstractUIComponent auic) {
        uiComponents.add(auic);
    }

    // Stop music so that it doesn't continue from the place it stopped on next scene load
    public void onTransition() {
        if (music != null) StopMusicStream(music);
    }
}
