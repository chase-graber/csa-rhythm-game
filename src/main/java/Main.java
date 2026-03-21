import static com.raylib.Colors.RAYWHITE;
import static com.raylib.Raylib.*;

import java.util.ArrayList;

import object.NoteKey;
import object.Note;

public class Main {
    
    public static void main(String[] args) {
        float dt = 0.0f;

        SetTraceLogLevel(LOG_WARNING | LOG_ERROR | LOG_FATAL);

        InitWindow(1080, 720, "Beats Baby");
        InitAudioDevice();
        SetTargetFPS(60);

        NoteKey[] keys = {
                new NoteKey(KEY_UP),
                new NoteKey(KEY_LEFT),
                new NoteKey(KEY_DOWN),
                new NoteKey(KEY_RIGHT)
        };

        ArrayList<Note> notes = new ArrayList<>();

        while (!WindowShouldClose()) {
            dt = GetFrameTime();

            int track = GetRandomValue(0, 3);
            if (IsKeyPressed(KEY_SPACE)) {
                notes.add(new Note(track, keys[track]));
                if (notes.size() == 1) notes.get(0).furthest = true;
            }

            for (NoteKey nk : keys) {
                nk.update(dt);
            }
            
            for (int i = notes.size() - 1; i >= 0; i--) {
                if (notes.get(i).done) {
                    notes.remove(i);
                    if (notes.size() > 0) notes.get(i).furthest = true;
                    continue;
                }
                notes.get(i).update(dt);
            }

            BeginDrawing();

            ClearBackground(RAYWHITE);

            // A relic of a simpler time
            // DrawText("Wyd? boss move boss move.", 100, 100, 20, BLACK);

            for (NoteKey nk : keys) {
                nk.render();
            }
            for (Note n : notes) {
                n.render();
            }

            EndDrawing();
        }

        CloseAudioDevice();
        CloseWindow();
    }
}