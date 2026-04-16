package scene;

import static com.raylib.Colors.RED;
import static com.raylib.Colors.WHITE;
import static com.raylib.Raylib.*;

import core.Settings;
import scene.menustate.*;
import util.AssetLoader;

public class MenuScene extends AbstractScene {

    private static AbstractMenuState currentState;

    private float bgOffset;

    public MenuScene() {
        this.music = AssetLoader.getMusic("assets/sounds/temp_menu_music.mp3");
        this.background = AssetLoader.getTexture("assets/textures/backgrounds/menu_bg.png");
        PlayMusicStream(this.music);
        currentState = MenuStateMachine.MENU.get();

        this.bgOffset = 0.0f;
    }

    @Override
    public void update(float dt) {
        UpdateMusicStream(music);

        currentState.update(dt);

        bgOffset += Settings.BACKGROUND_SCROLL_SPEED * dt;
        if (bgOffset <= -2 * Settings.SCREEN_WIDTH) bgOffset += 2 * Settings.SCREEN_WIDTH;
    }

    @Override
    public void render() {
        DrawTexture(background, (int)bgOffset, 0, WHITE);
        DrawTexture(background, (int)bgOffset + 2 * Settings.SCREEN_WIDTH, 0, WHITE);

        currentState.render();

        if (Settings.DEBUG) {
            // Center of screen for positioning
            DrawLineEx(
                    new Vector2().x((float)Settings.SCREEN_WIDTH / 2).y(0),
                    new Vector2().x((float)Settings.SCREEN_WIDTH / 2).y(Settings.SCREEN_HEIGHT),
                    2, RED);
            DrawLineEx(
                    new Vector2().x(0).y((float)Settings.SCREEN_HEIGHT / 2),
                    new Vector2().x(Settings.SCREEN_WIDTH).y((float)Settings.SCREEN_HEIGHT / 2),
                    2, RED);
        }
    }

    @Override
    public void onTransition() {
        StopMusicStream(music);
        PlayMusicStream(music);
    }

    public static void setCurrentState(AbstractMenuState next) {
        currentState = next;

        // Clear any previous interaction
        currentState.init();
    }
}
