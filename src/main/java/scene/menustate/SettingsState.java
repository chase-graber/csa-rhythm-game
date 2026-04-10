package scene.menustate;

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

    }
}
