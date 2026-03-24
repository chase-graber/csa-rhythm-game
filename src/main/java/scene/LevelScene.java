package scene;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.BLACK;

import java.util.ArrayList;

import com.raylib.Raylib.Music;

import object.Note;
import object.NoteKey;
import util.AssetLoader;
import util.Settings;

public class LevelScene implements Scene {

    private double startTime;
    private Music song;

    private NoteKey[] keys = {
        new NoteKey(KEY_UP),
        new NoteKey(KEY_LEFT),
        new NoteKey(KEY_DOWN),
        new NoteKey(KEY_RIGHT)
    };
    private ArrayList<Note>[] tracks = new ArrayList[]{
        new ArrayList<>(),
        new ArrayList<>(),
        new ArrayList<>(),
        new ArrayList<>()
    };

    public LevelScene(String songpath) {
        this.startTime = GetTime();
        this.song = AssetLoader.getMusic(songpath);
    }

    public void addNoteToTrack(int track) {
        tracks[track].add(new Note(track, this));
        if (tracks[track].size() == 1) tracks[track].get(0).furthest = true;
    }

    @Override
    public void update(float dt) {
        if (IsKeyPressed(KEY_SPACE)) addNoteToTrack(GetRandomValue(0, 3));

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
        for (int track = 0; track < tracks.length; track++) {
            DrawRectangle(0, (int)keys[track].getPosition().y() - keys[track].getRadius(), GetScreenWidth(), 2 * keys[track].getRadius(), Fade(BLACK, 0.25f));
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
