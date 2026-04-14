package scene.menustate;

import core.Settings;
import scene.MenuScene;
import ui.AbstractUIComponent;
import ui.menu.UIMenuButton;
import ui.menu.UISlider;
import ui.menu.UISwappableOptionsMenu;

import java.util.ArrayList;

import static com.raylib.Colors.BLACK;
import static com.raylib.Raylib.*;

public class SettingsState extends AbstractMenuState {

    private static SettingsState instance;

    private SettingsState() {
        // stuff
    }

    public static SettingsState get() {
        if (instance == null)
            instance = new SettingsState();
        return instance;
    }

    @Override
    public void init() {
        // Clear components
        uiComponents = new ArrayList<>();

        // Add new components
        uiComponents.add(new UIMenuButton<>(
                new Vector2().x(25).y(Settings.SCREEN_HEIGHT - 100),
                new Vector2().x(200).y(75),
                "BACK",
                MenuScene::setCurrentState,
                MenuStateMachine.MENU.get()
        ));
        uiComponents.add(
                new UISlider("Volume", 150, 0, 100, Settings.volume, 200, Settings::updateVolume)
        );
        uiComponents.add(
                new UISwappableOptionsMenu<>(
                        300, 250,
                        Settings.KeyLayouts.values(),
                        Settings.KeyLayouts.getLabelsAsArray(),
                        Settings::updateCurrentKeyLayout,
                        Settings.keyLayoutIndex
                )
        );
    }

    @Override
    public void update(float dt) {
        for (AbstractUIComponent auic : uiComponents) {
            auic.update(dt);
        }
    }

    @Override
    public void render() {
        int textWidth = MeasureText("Settings", 50);
        DrawText("Settings", (Settings.SCREEN_WIDTH - textWidth) / 2, 50, 50, BLACK);

        for (AbstractUIComponent auic : uiComponents) {
            auic.render();
        }
    }
}
