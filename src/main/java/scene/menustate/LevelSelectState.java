package scene.menustate;

import static com.raylib.Raylib.*;

import core.Settings;
import scene.MenuScene;
import ui.AbstractUIComponent;
import ui.menu.UIMenuButton;

import static com.raylib.Colors.WHITE;
import static com.raylib.Raylib.DrawText;

public class LevelSelectState extends AbstractMenuState {

    private static LevelSelectState instance;

    private LevelSelectState() {
        // Init code
    }

    public static LevelSelectState get() {
        if (instance == null)
            instance = new LevelSelectState();
        return instance;
    }

    @Override
    public void init() {
        addUIComponent(new UIMenuButton<>(
                new Vector2().x(25).y(Settings.SCREEN_HEIGHT - 100),
                new Vector2().x(200).y(75),
                "BACK",
                MenuScene::setCurrentState,
                MenuStateMachine.MENU.get()
        ));
    }

    @Override
    public void update(float dt) {
        for (AbstractUIComponent auic : uiComponents) {
            auic.update(dt);
        }
    }

    @Override
    public void render() {
        DrawText("Level select state", 10, 10, 20, WHITE);

        for (AbstractUIComponent auic : uiComponents) {
            auic.render();
        }
    }
}
