package core;

import static com.raylib.Colors.BLACK;
import static com.raylib.Colors.RED;
import static com.raylib.Raylib.*;

import scene.AbstractScene;
import scene.GameStartScene;
import scene.MenuScene;
import scene.menustate.MenuStateMachine;
import ui.UITransition;
import util.AssetLoader;

// IMPORTANT: EVERYTHING IN THIS CLASS (except for the private methods) HAS TO BE STATIC
public final class Game {

    // Singleton
    private static Game instance = new Game();

    // Scene and transition stuff
    public static MenuScene mainMenu;
    private static AbstractScene currentScene;
    private static AbstractScene nextScene;
    private static UITransition transition;

    private static boolean shouldClose = false;

    // Private constructor so that there aren't duplicate games
    private Game() {
        init();
    }

    // Singleton pattern
    public static Game get() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    // Initializes the game on startup
    private void init() {
        SetTraceLogLevel(Settings.DEBUG ? LOG_DEBUG : LOG_WARNING);

        // IMPORTANT: NEVER DO ANYTHING BEFORE InitWindow AND InitAudioDevice, REMEMBER WHAT HAPPENED THE LAST TIMES YOU TRIED
        InitWindow(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT, "Beats Baby");
        InitAudioDevice();
        SetTargetFPS(60);

        // General init code
        Initializer.loadSettings();
        MenuStateMachine.initValues();
        mainMenu = new MenuScene();
        mainMenu.init();

        // Game setup
        currentScene = new GameStartScene(); //AssetLoader.loadLevelScene("aleph_0.lvl");
        nextScene = null;
        transition = null;
    }

    // This is the whole game, its only 2 lines (ignore the other classes and methods in the project they're irrelevant and are only there for aesthetic purposes
    public void run() {
        loop();
        cleanup();
    }

    // Main game loop
    private void loop() {
        while (!WindowShouldClose() && !shouldClose) {
            // Debug toggle
            if (IsKeyPressed(KEY_F2)) {
                Settings.DEBUG = !Settings.DEBUG;
                SetTraceLogLevel(Settings.DEBUG ? LOG_DEBUG : LOG_WARNING);
            }

            // Update game
            float dt = GetFrameTime();
            if (transition != null) {
                transition.update(dt);
                if (transition.hasOpened && nextScene != null) {
                    currentScene.onTransition();
                    currentScene = nextScene;
                    nextScene = null;
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

            // Debug drawing
            if (Settings.DEBUG) {
                DrawFPS(10, 10);

                // Center of screen for positioning
                DrawLineEx(
                        new Vector2().x((float)Settings.SCREEN_WIDTH / 2).y(0),
                        new Vector2().x((float)Settings.SCREEN_WIDTH / 2).y(Settings.SCREEN_HEIGHT),
                        2, RED);
                DrawLineEx(
                        new Vector2().x(0).y((float)Settings.SCREEN_HEIGHT / 2),
                        new Vector2().x(Settings.SCREEN_WIDTH).y((float)Settings.SCREEN_HEIGHT / 2),
                        2, RED);
            }
            EndDrawing();
        }
    }

    // Cleans up memory since raylib doesn't do it automatically on close
    private void cleanup() {
        AssetLoader.cleanup();
        CloseAudioDevice();
        CloseWindow();
    }

    // Transitions
    public static void transitionToNextScene(AbstractScene scene) {
        nextScene = scene;
        // Ensure that the main menu always comes back to the "main" main menu
        if (scene.equals(mainMenu)) MenuScene.setCurrentState(MenuStateMachine.MENU.get());

        transition = new UITransition();

        // Debug logging
        TraceLog(LOG_DEBUG, "Transitioning from scene " + currentScene + " to " + scene);
    }

    // So that you can close the game from other classes
    public static void setShouldClose(boolean shouldClose) { Game.shouldClose = shouldClose; }
}
