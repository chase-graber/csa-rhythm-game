package scene;

import static com.raylib.Colors.WHITE;
import static com.raylib.Raylib.*;

import scene.menustate.*;
import util.AssetLoader;

public class MenuScene extends AbstractScene {

    private static AbstractMenuState currentState = MenuState.getInstance();

    public MenuScene() {
        this.music = AssetLoader.getMusic("assets/sounds/temp_menu_music.mp3");
        this.background = AssetLoader.getTexture("assets/textures/backgrounds/demo_bg.png");
        PlayMusicStream(this.music);
    }

    @Override
    public void update(float dt) {
        UpdateMusicStream(music);
        DrawTextureV(background, Vector2Zero(), WHITE);

        currentState.update(dt);
    }

    @Override
    public void render() {
        currentState.render();
    }

    public static void setCurrentState(AbstractMenuState next) {
        currentState = next;
    }
}
