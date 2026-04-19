package scene;

import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

import core.Settings;
import scene.menustate.*;
import util.AssetLoader;

// Main menu
public class MenuScene extends AbstractScene {

    // Keeps track of which state is displayed
    private static AbstractMenuState currentState;

    // For scrolling background
    private float bgOffset;

    public MenuScene() {
        // General non-static init
        this.bgOffset = 0.0f;
    }

    // Static init
    public void init() {
        metadata = new SceneMetadata(AssetLoader.getMusic("assets/sounds/temp_menu_music.mp3"),
                AssetLoader.getTexture("assets/textures/backgrounds/menu_bg.png"));
        PlayMusicStream(this.metadata.music());
        currentState = MenuStateMachine.MENU.get();
    }

    @Override
    public void update(float dt) {
        if (!IsMusicStreamPlaying(metadata.music())) PlayMusicStream(metadata.music());
        UpdateMusicStream(metadata.music());

        currentState.update(dt);

        // Move background
        bgOffset += Settings.BACKGROUND_SCROLL_SPEED * dt;
        if (bgOffset <= -2 * Settings.SCREEN_WIDTH) bgOffset += 2 * Settings.SCREEN_WIDTH;
    }

    @Override
    public void render() {
        // Draw two backgrounds for seamless looping
        DrawTexture(metadata.background(), (int)bgOffset, 0, DARKBLUE);
        if (bgOffset <= -Settings.SCREEN_WIDTH) DrawTexture(metadata.background(), (int)bgOffset + 2 * Settings.SCREEN_WIDTH, 0, DARKBLUE);

        currentState.render();
    }

    // Reset music
    @Override
    public void onTransition() {
        StopMusicStream(metadata.music());
        PlayMusicStream(metadata.music());
    }

    public static void setCurrentState(AbstractMenuState next) {
        currentState = next;

        // Clear any previous interaction (e.g. level has already been played)
        currentState.init();
    }
}
