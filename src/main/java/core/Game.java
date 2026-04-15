package core;

import static com.raylib.Colors.BLACK;
import static com.raylib.Raylib.*;

import scene.AbstractScene;
import scene.MenuScene;
import scene.menustate.MenuStateMachine;
import ui.UITransition;
import util.AssetLoader;

public final class Game {

    // Singleton
    private static Game instance = new Game();

    // IMPORTANT: EVERYTHING IN THIS CLASS (except for the private methods) HAS TO BE STATIC
    public static MenuScene mainMenu;

    private static AbstractScene currentScene;
    private static AbstractScene nextScene;
    private static UITransition transition;

    private static boolean shouldClose = false;

    private Game() {
        init();
    }

    public static Game get() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    private void init() {
        SetTraceLogLevel(LOG_WARNING);

        // IMPORTANT: NEVER DO ANYTHING BEFORE InitWindow AND InitAudioDevice, REMEMBER WHAT HAPPENED THE LAST TIMES YOU TRIED
        InitWindow(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT, "Beats Baby");
        InitAudioDevice();
        SetTargetFPS(60);

        // General init code
        Initializer.loadSettings();
        MenuStateMachine.initValues();
        mainMenu = new MenuScene();

        // Game setup
        currentScene = mainMenu; //AssetLoader.loadLevelScene("aleph_0.lvl");
        nextScene = null;
        transition = null;
    }

    public void run() {
        loop();
        cleanup();
    }

    private void loop() {
        while (!WindowShouldClose() && !shouldClose) {
            // Debug toggle
            if (IsKeyPressed(KEY_F2)) Settings.DEBUG = !Settings.DEBUG;

            // Update game
            float dt = GetFrameTime();
            if (transition != null) {
                transition.update(dt);
                if (transition.done && nextScene != null) {
                    currentScene.onTransition();
                    currentScene = nextScene;
                    nextScene = null;
                    transition = new UITransition(false);
                } else if (transition.done) transition = null;
            }
            currentScene.update(dt);

            // Render
            BeginDrawing();
            ClearBackground(BLACK);

            // A relic of a simpler time
            // DrawText("Wyd? boss move boss move.", 100, 100, 20, BLACK);
            currentScene.render();
            if (transition != null) transition.render();

            if (Settings.DEBUG) DrawFPS(10, 10);
            EndDrawing();
        }
    }

    private void cleanup() {
        AssetLoader.cleanup();
        CloseAudioDevice();
        CloseWindow();
    }

    public static void transitionToNextScene(AbstractScene scene) {
        nextScene = scene;
        // Ensure that the main menu always comes back to the "main" main menu
        if (scene.equals(mainMenu)) MenuScene.setCurrentState(MenuStateMachine.MENU.get());

        transition = new UITransition(true);
    }

    public static void setShouldClose(boolean shouldClose) { Game.shouldClose = shouldClose; }
}
