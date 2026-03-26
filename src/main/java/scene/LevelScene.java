package scene;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

import java.util.ArrayList;

import com.raylib.Raylib.Music;

import object.Note;
import object.NoteKey;
import util.Settings;

public class LevelScene implements Scene {

    private Music song;
    private Texture background;
    private float startTime; // Might need this might not
    private float elapsedTime = 0.0f;

    private NoteKey[] keys;
    private ArrayList<Note>[] tracks;

    public LevelScene(Music song, Texture background, Settings.KeyLayouts layout) {
        this.song = song;
        this.background = background;
        this.startTime = (float)GetTime();

        switch(layout) {
            case ARROW ->
                keys = new NoteKey[]{
                    new NoteKey(KEY_UP),
                    new NoteKey(KEY_LEFT),
                    new NoteKey(KEY_DOWN),
                    new NoteKey(KEY_RIGHT)
                };
            case DFJK ->
                keys = new NoteKey[]{
                    new NoteKey(KEY_D),
                    new NoteKey(KEY_F),
                    new NoteKey(KEY_J),
                    new NoteKey(KEY_K)
                };
        }
    }

    public void setLevelTracks(ArrayList<Note>[] tracks) {
        this.tracks = tracks;
    }

    @Override
    public void update(float dt) {
        elapsedTime += dt;
        if (elapsedTime >= 3.0f && !IsMusicStreamPlaying(song)) PlayMusicStream(song);
        else UpdateMusicStream(song);

        // Update keys (detect presses)
        for (NoteKey nk : keys) {
            nk.update(dt);
        }

        // Move the notes
        for (ArrayList<Note> track : tracks) {
            for (int i = track.size() - 1; i >= 0; i--) {
                Note n = track.get(i);
                if (n.done) {
                    track.remove(i);
                    if (i < track.size()) track.get(i).furthest = true;
                    continue;
                } else if (n.getPosition().x() + n.getRadius() < Settings.PADDING - n.getRadius()) {
                      n.furthest = false;
                      if (i < track.size() - 1) track.get(i+1).furthest = true;
                }
                n.update(dt);
            }
        }
    }

    @Override
    public void render() {
        // Draw background
        DrawTextureV(background, Vector2Zero(), WHITE);

        for (int track = 0; track < tracks.length; track++) {
            DrawRectangle(0, (int)keys[track].getPosition().y() - keys[track].getRadius() + 10, GetScreenWidth(), 2 * keys[track].getRadius() - 20, Fade(BLACK, 0.25f));
            for (Note n : tracks[track]) {
                n.render();
            }
        }
        for (NoteKey nk : keys) {
            nk.render();
        }
    }

    public NoteKey getTrackKey(int track) {
        return keys[track];
    }
}
