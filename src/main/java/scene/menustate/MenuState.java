package scene.menustate;

import static com.raylib.Colors.RED;
import static com.raylib.Colors.WHITE;
import static com.raylib.Raylib.*;

import core.Game;
import core.Settings;
import scene.MenuScene;
import ui.AbstractUIComponent;
import ui.menu.UIMenuButton;
import util.AssetLoader;
import util.UtilMethods;

import java.util.ArrayList;

public class MenuState extends AbstractMenuState {

    // Singleton
    private static MenuState instance;

    // This is the logo. Don't believe me? Alright fine. Delete this variable. See if I care. Your program is gonna crash and it's all gonna be your fault. You brought this upon yourself.
    private Texture logo;

    private MenuState() {
        this.logo = AssetLoader.getTexture("assets/textures/ui/logo.png");
    }

    // Singleton pattern
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
        uiComponents.add(new UIMenuButton<>(250, "LEVEL SELECT", MenuScene::setCurrentState, MenuStateMachine.LEVEL_SELECT.get()));
        uiComponents.add(new UIMenuButton<>(400, "SETTINGS", MenuScene::setCurrentState, MenuStateMachine.SETTINGS.get()));
        uiComponents.add(new UIMenuButton<>(550, "EXIT GAME", Game::setShouldClose, true));
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
        // Draw ui
        for (AbstractUIComponent auic : uiComponents) {
            auic.render();
        }

        // Draw logo, rotated by the sine of the current time so it isn't completely static
        Rectangle texRect = UtilMethods.getTextureRect(logo);
        DrawTexturePro(
                logo,
                texRect,
                new Rectangle().x(Settings.SCREEN_WIDTH / 2.0f).y(125).width(texRect.width()).height(texRect.height()),
                UtilMethods.getTextureMiddle(logo),
                (float)Math.sin(GetTime()),
                WHITE
                );

        // Debug drawing
        if (Settings.DEBUG) {
            // Logo positioning
            DrawRectangleLines((int)((Settings.SCREEN_WIDTH - texRect.width()) / 2), (int)(125 - texRect.height() / 2), (int)texRect.width(), (int)texRect.height(), RED);
        }
    }
}
