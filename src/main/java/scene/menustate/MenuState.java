package scene.menustate;

import core.Game;
import scene.MenuScene;
import ui.AbstractUIComponent;
import ui.menu.UIMenuButton;

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
        addUIComponent(new UIMenuButton<>(250, "LEVEL SELECT", MenuScene::setCurrentState, MenuStateMachine.LEVEL_SELECT.get()));
        addUIComponent(new UIMenuButton<>(400, "SETTINGS", MenuScene::setCurrentState, MenuStateMachine.SETTINGS.get()));
        addUIComponent(new UIMenuButton<>(550, "EXIT GAME", Game::setShouldClose, true));
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
