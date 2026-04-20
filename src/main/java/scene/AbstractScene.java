package scene;

import core.TickableGameEntity;
import ui.AbstractUIComponent;
import util.AssetLoader;

import java.util.ArrayList;

import static com.raylib.Raylib.*;

// Every scene in the game extends this, can be swapped with Game.transitionToNextScene(AbstractScene scene)
public abstract class AbstractScene implements TickableGameEntity {

    protected SceneMetadata metadata = new SceneMetadata(AssetLoader.getMusic("assets/sounds/temp_menu_music.mp3"),
            AssetLoader.getTexture("assets/textures/backgrounds/menu_bg.png"));

    protected ArrayList<AbstractUIComponent> uiComponents = new ArrayList<>();
    
    public record SceneMetadata(Music music, Texture background) { }

    // So that other non-scene classes can add components
    public void addUIComponent(AbstractUIComponent auic) {
        uiComponents.add(auic);
    }

    // Stop music so that it doesn't continue from the place it stopped on next scene load
    public void onTransition() {
        if (metadata.music() != null) StopMusicStream(metadata.music());
    }
}
