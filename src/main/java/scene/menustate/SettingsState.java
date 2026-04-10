package scene.menustate;

import static com.raylib.Colors.WHITE;
import static com.raylib.Raylib.*;

public class SettingsState extends AbstractMenuState {

    private static SettingsState settingsState;

    private SettingsState() {
        // Init code
    }

    public static SettingsState getInstance() {
        if (settingsState == null)
            settingsState = new SettingsState();
        return settingsState;
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render() {
        DrawText("Settings state", 10, 10, 20, WHITE);
    }
}
