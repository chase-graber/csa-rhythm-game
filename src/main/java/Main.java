import static com.raylib.Colors.BLACK;
import static com.raylib.Raylib.*;

import scene.AbstractScene;
import util.AssetLoader;
import util.Settings;

public class Main {
    
    public static void main(String[] args) {
        SetTraceLogLevel(LOG_WARNING | LOG_ERROR | LOG_FATAL);

        InitWindow(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT, "Beats Baby");
        InitAudioDevice();
        SetTargetFPS(60);

        AbstractScene currentScene = AssetLoader.loadLevelScene("spooktune.txt");

        while (!WindowShouldClose()) {
            // Debug toggle
            if (IsKeyPressed(KEY_F2)) Settings.DEBUG = !Settings.DEBUG;

            currentScene.update(GetFrameTime());

            BeginDrawing();
            ClearBackground(BLACK);
            // A relic of a simpler time
            // DrawText("Wyd? boss move boss move.", 100, 100, 20, BLACK);
            currentScene.render();
            DrawFPS(10, 10);
            EndDrawing();
        }

        CloseAudioDevice();
        CloseWindow();
    }
}