package scene.menustate;

public enum MenuStateMachine {
    MENU(MenuState.get()),
    SETTINGS(SettingsState.get()),
    LEVEL_SELECT(LevelSelectState.get());

    private final AbstractMenuState state;

    MenuStateMachine(AbstractMenuState state) {
        this.state = state;
    }

    public AbstractMenuState get() { return state; }

    public static void initValues() {
        for (MenuStateMachine msm : MenuStateMachine.values()) {
            msm.get().init();
        }
    }
}
