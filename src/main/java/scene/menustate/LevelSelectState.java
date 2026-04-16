package scene.menustate;

import static com.raylib.Colors.BLACK;
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

    private static LevelSelectState instance;

    private LevelSelectState() {
        // stuff
    }

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
        uiComponents.add(new UIMenuButton<>(
                150,
                new Vector2().x(400).y(75),
                "Toby Fox - Spooktune",
                Game::transitionToNextScene,
                AssetLoader.loadLevelScene("spooktune.lvl")));
        uiComponents.add(new UIMenuButton<>(
                250,
                new Vector2().x(400).y(75),
                "ABBA - Mamma Mia",
                Game::transitionToNextScene,
                AssetLoader.loadLevelScene("mammamia.lvl")));
        uiComponents.add(new UIMenuButton<>(
                350,
                new Vector2().x(400).y(75),
                "Daudre - Sandstorm",
                Game::transitionToNextScene,
                AssetLoader.loadLevelScene("sandstorm.lvl")));
        uiComponents.add(new UIMenuButton<>(
                450,
                new Vector2().x(400).y(75),
                "To Be Determined",
                Game::transitionToNextScene,
                AssetLoader.loadLevelScene("spooktune.lvl")));
        uiComponents.add(new UIMenuButton<>(
                550,
                new Vector2().x(400).y(75),
                "LeaF - Aleph-0",
                Game::transitionToNextScene,
                AssetLoader.loadLevelScene("aleph_0.lvl")));

        uiComponents.add(new UIMenuButton<>(
                new Vector2().x(25).y(Settings.SCREEN_HEIGHT - 100),
                new Vector2().x(200).y(75),
                "BACK",
                MenuScene::setCurrentState,
                MenuStateMachine.MENU.get()));
    }

    @Override
    public void update(float dt) {
        for (AbstractUIComponent auic : uiComponents) {
            auic.update(dt);
        }
    }

    @Override
    public void render() {
        int textWidth = MeasureText("Level Select", 50);
        DrawText("Level Select", (Settings.SCREEN_WIDTH - textWidth) / 2, 50, 50, BLACK);

        for (AbstractUIComponent auic : uiComponents) {
            auic.render();
        }
    }
}
