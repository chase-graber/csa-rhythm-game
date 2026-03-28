package scene;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

import java.util.ArrayList;

import object.Note;
import object.NoteKey;
import ui.UIProgressBar;
import util.Settings;

public class LevelScene implements Scene {

    private Music song;
    private Texture background;
    private float startTime; // Might need this might not
    private float elapsedTime = 0.0f;
    private boolean hasPlayedSong = false;

    private NoteKey[] keys;
    private ArrayList<Note>[] tracks;

    // Scoring types
    public int numPerfect, numGood, numPoor, numMiss;

    // UI elements
    private UIProgressBar progressBar;

    public LevelScene(Music song, Texture background) {
        this.song = song;
        this.song.looping(false);
        this.background = background;
        this.startTime = (float)GetTime();
        this.progressBar = new UIProgressBar(song);
        updateKeyLayout();
    }

    // Addon to constructor, called in AssetLoader since notes need a parent scene (which can't exist if this is part of the constructor)
    public void setLevelTracks(ArrayList<Note>[] tracks) {
        this.tracks = tracks;
    }

    @Override
    public void update(float dt) {
        // Update UI
        progressBar.update();

        elapsedTime += dt;
        if (elapsedTime >= 2.9f && !IsMusicStreamPlaying(song) && !hasPlayedSong) {
            PlayMusicStream(song);
            hasPlayedSong = true;
        } else UpdateMusicStream(song);

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

                    if (!track.isEmpty()) {
                        int index = 0;
                        while (track.get(index).getPosition().x() + track.get(index).getRadius() < Settings.PADDING - track.get(index).getRadius()) {
                            index++;
                        }
                        track.get(index).furthest = true;
                    }
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

        // Draw UI
        DrawRectangleGradientV(0, 0, GetScreenWidth(), 60, BLACK, BLANK);
        progressBar.render();
    }

    public NoteKey getTrackKey(int track) {
        return keys[track];
    }

    // Use for settings menu
    public void updateKeyLayout() {
        keys = new NoteKey[]{
            new NoteKey(Settings.currentKeyLayout.getTrackKey(0)),
            new NoteKey(Settings.currentKeyLayout.getTrackKey(1)),
            new NoteKey(Settings.currentKeyLayout.getTrackKey(2)),
            new NoteKey(Settings.currentKeyLayout.getTrackKey(3))
        };
    }
}
