import static com.raylib.Colors.BLACK;
import static com.raylib.Colors.RAYWHITE;
import static com.raylib.Raylib.*;

import object.NoteKey;

public class Main {
    
    public static void main(String[] args) {
        float dt = 0.0f;

        InitWindow(1080, 720, "Beats Baby");
        InitAudioDevice();
        SetTargetFPS(60);

        NoteKey[] keys = {
            new NoteKey(KEY_UP),
            new NoteKey(KEY_LEFT),
            new NoteKey(KEY_RIGHT),
            new NoteKey(KEY_DOWN)
        };

        while (!WindowShouldClose()) {
            dt = GetFrameTime();

            for (NoteKey nk : keys) {
                nk.update(dt);
            }

            BeginDrawing();

            ClearBackground(RAYWHITE);

            // A relic of a simpler time
            // DrawText("Wyd? boss move boss move.", 100, 100, 20, BLACK);

            for (NoteKey nk : keys) {
                nk.render();
            }

            EndDrawing();
        }

        CloseAudioDevice();
        CloseWindow();
    }
}