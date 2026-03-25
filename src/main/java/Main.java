import static com.raylib.Colors.RAYWHITE;
import static com.raylib.Raylib.*;

import scene.LevelScene;
import scene.Scene;
import util.AssetLoader;
import util.Settings;

public class Main {
    
    public static void main(String[] args) {
        SetTraceLogLevel(LOG_WARNING | LOG_ERROR | LOG_FATAL);

        InitWindow(1080, 720, "Beats Baby");
        InitAudioDevice();
        SetTargetFPS(60);

        Scene currentScene = AssetLoader.loadLevelScene("assets/levels/mammamia.txt", Settings.KeyLayouts.ARROW);

        while (!WindowShouldClose()) {
            currentScene.update(GetFrameTime());

            BeginDrawing();

            ClearBackground(RAYWHITE);

            // A relic of a simpler time
            // DrawText("Wyd? boss move boss move.", 100, 100, 20, BLACK);

            currentScene.render();

            EndDrawing();
        }

        CloseAudioDevice();
        CloseWindow();
    }
}