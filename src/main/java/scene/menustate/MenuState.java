package scene.menustate;

import core.Game;
import scene.MenuScene;
import ui.AbstractUIComponent;
import ui.menu.UIMenuButton;

public class MenuState extends AbstractMenuState {

    private static MenuState menuState;

    private MenuState() {
        addUIComponent(new UIMenuButton<>(250, "LEVEL SELECT", MenuScene::setCurrentState, LevelSelectState.getInstance()));
        addUIComponent(new UIMenuButton<>(400, "SETTINGS", MenuScene::setCurrentState, SettingsState.getInstance()));
        addUIComponent(new UIMenuButton<>(550, "EXIT GAME", Game::setShouldClose, true));
    }

    public static MenuState getInstance() {
        if (menuState == null)
            menuState = new MenuState();
        return menuState;
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
