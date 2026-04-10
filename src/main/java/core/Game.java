package core;

import static com.raylib.Colors.BLACK;
import static com.raylib.Raylib.*;

import scene.AbstractScene;
import scene.MenuScene;
import ui.UITransition;
import util.AssetLoader;

public final class Game {

    // Singleton
    private static Game game;

    public static MenuScene mainMenu;

    private AbstractScene currentScene;
    private AbstractScene nextScene;
    private UITransition transition;

    private static boolean shouldClose = false;

    private Game() {
        init();
    }

    public static Game getInstance() {
        if (game == null) {
            game = new Game();
        }
        return game;
    }

    private void init() {
        SetTraceLogLevel(LOG_WARNING);

        InitWindow(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT, "Beats Baby");
        InitAudioDevice();
        SetTargetFPS(60);

        mainMenu = new MenuScene();

        this.currentScene = mainMenu; //AssetLoader.loadLevelScene("spooktune.txt");
        this.nextScene = null;
        this.transition = null;
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

    public void transitionToNextScene(AbstractScene scene) {
        nextScene = scene;
        transition = new UITransition(true);
    }

    public static void setShouldClose(boolean shouldClose) { Game.shouldClose = shouldClose; }
}
