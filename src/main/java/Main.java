import static com.raylib.Colors.RAYWHITE;
import static com.raylib.Raylib.*;

import scene.LevelScene;
import scene.Scene;

public class Main {
    
    public static void main(String[] args) {
        SetTraceLogLevel(LOG_WARNING | LOG_ERROR | LOG_FATAL);

        InitWindow(1080, 720, "Beats Baby");
        InitAudioDevice();
        SetTargetFPS(60);

        Scene currentScene = new LevelScene("path/to/music.mp3");

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