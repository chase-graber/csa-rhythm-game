package scene.menustate;

import static com.raylib.Colors.WHITE;
import static com.raylib.Raylib.*;

import core.Game;
import core.Settings;
import scene.MenuScene;
import ui.AbstractUIComponent;
import ui.menu.UIMenuButton;
import util.AssetLoader;

import java.util.ArrayList;

import static com.raylib.Raylib.DrawText;

public class LevelSelectState extends AbstractMenuState {

    // Singleton
    private static LevelSelectState instance;

    private LevelSelectState() {
        // stuff
    }

    // Singleton pattern
    public static LevelSelectState get() {
        if (instance == null)
            instance = new LevelSelectState();
        return instance;
    }

    @Override
    public void init() {
        // Clear components
        uiComponents = new ArrayList<>();

        // Add new components
        // Level select buttons
        uiComponents.add(new UIMenuButton<>(
                150,
                new Vector2().x(400).y(75),
                "Toby Fox - Spooktune",
                Game::transitionToNextScene,
                AssetLoader.loadLevelScene("spooktune.lvl")));
        uiComponents.add(new UIMenuButton<>(
                250,
                new Vector2().x(400).y(75),
                "C418 - Moog City",
                Game::transitionToNextScene,
                AssetLoader.loadLevelScene("moogcity.lvl")));
        uiComponents.add(new UIMenuButton<>(
                350,
                new Vector2().x(400).y(75),
                "Daudre - Sandstorm",
                Game::transitionToNextScene,
                AssetLoader.loadLevelScene("sandstorm.lvl")));
        uiComponents.add(new UIMenuButton<>(
                450,
                new Vector2().x(400).y(75),
                "Toby Fox - It's TV Time!",
                Game::transitionToNextScene,
                AssetLoader.loadLevelScene("itstvtime.lvl")));
        uiComponents.add(new UIMenuButton<>(
                550,
                new Vector2().x(400).y(75),
                "LeaF - Aleph-0",
                Game::transitionToNextScene,
                AssetLoader.loadLevelScene("aleph_0.lvl")));

        // Return to main menu
        uiComponents.add(new UIMenuButton<>(
                new Vector2().x(25).y(Settings.SCREEN_HEIGHT - 100),
                new Vector2().x(200).y(75),
                "BACK",
                MenuScene::setCurrentState,
                MenuStateMachine.MENU.get()));
    }

    @Override
    public void update(float dt) {
        // Update ui
        for (AbstractUIComponent auic : uiComponents) {
            auic.update(dt);
        }
    }

    @Override
    public void render() {
        // Draw state header
        int textWidth = MeasureText("Level Select", 50);
        DrawText("Level Select", (Settings.SCREEN_WIDTH - textWidth) / 2, 50, 50, WHITE);

        // Render ui
        for (AbstractUIComponent auic : uiComponents) {
            auic.render();
        }
    }
}
