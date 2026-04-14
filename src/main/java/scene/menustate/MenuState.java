package scene.menustate;

import core.Game;
import scene.MenuScene;
import ui.AbstractUIComponent;
import ui.menu.UIMenuButton;
import util.AssetLoader;

import java.util.ArrayList;

public class MenuState extends AbstractMenuState {

    private static MenuState instance;

    private MenuState() {
        // stuff
    }

    public static MenuState get() {
        if (instance == null)
            instance = new MenuState();
        return instance;
    }

    @Override
    public void init() {
        // Clear components
        uiComponents = new ArrayList<>();

        // Add new components
        uiComponents.add(new UIMenuButton<>(250, "LEVEL SELECT", Game::transitionToNextScene, AssetLoader.loadLevelScene("aleph_0.lvl")));
        uiComponents.add(new UIMenuButton<>(400, "SETTINGS", MenuScene::setCurrentState, MenuStateMachine.SETTINGS.get()));
        uiComponents.add(new UIMenuButton<>(550, "EXIT GAME", Game::setShouldClose, true));
    }

    @Override
    public void update(float dt) {
        for (AbstractUIComponent auic : uiComponents) {
            auic.update(dt);
        }
    }

    @Override
    public void render() {
        for (AbstractUIComponent auic : uiComponents) {
            auic.render();
        }
    }
}
