import static com.raylib.Colors.BLACK;
import static com.raylib.Colors.RAYWHITE;
import static com.raylib.Raylib.*;

public class Main {
    public static void main(String args[]) {
        InitWindow(1080, 720, "Demo");
        InitAudioDevice();
        SetTargetFPS(60);

        while (!WindowShouldClose()) {
            BeginDrawing();

            ClearBackground(RAYWHITE);
            DrawText("Wyd? boss move boss move.", 100, 100, 20, BLACK);

            EndDrawing();
        }

        CloseAudioDevice();
        CloseWindow();
    }
}