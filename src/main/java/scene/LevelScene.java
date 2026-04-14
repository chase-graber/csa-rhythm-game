package scene;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

import java.util.ArrayList;

import core.Game;
import object.Note;
import object.NoteKey;
import ui.AbstractUIComponent;
import ui.level.UIProgressBar;
import core.Settings;
import util.AssetLoader;

public class LevelScene extends AbstractScene {

    private float elapsedTime = 0.0f;
    private boolean hasPlayedSong = false;
    private boolean transitioning = false;

    private NoteKey[] keys;
    private ArrayList<Note>[] tracks;

    // Scoring types
    public int[] numScoreTypes = { 0, 0, 0, 0 };
    private int totalNotes;

    public LevelScene(AssetLoader.LevelMetadata md) {
        this.music = md.song();
        this.music.looping(false);
        this.background = md.background();

        uiComponents.add(new UIProgressBar(md.song())); // UIProgressBar always at index 0

        updateKeyLayout();
    }

    // Addon to constructor, called in AssetLoader since notes need a parent scene (which can't exist if this is part of the constructor)
    public void setLevelTracks(ArrayList<Note>[] tracks) {
        this.tracks = tracks;
        for (ArrayList<Note> track : tracks) {
            this.totalNotes += track.size();
        }
    }

    @Override
    public void update(float dt) {
        // Update UI
        for (int i = uiComponents.size() - 1; i >= 0; i--) {
            AbstractUIComponent auic = uiComponents.get(i);
            if (auic.done) uiComponents.remove(i);

            auic.update(dt);
        }

        // Update music stream
        elapsedTime += dt;
        if (elapsedTime >= 2.9f && !IsMusicStreamPlaying(music) && !hasPlayedSong) {
            PlayMusicStream(music);
            hasPlayedSong = true;
        } else if (((UIProgressBar)uiComponents.get(0)).getCurrentSongTime() == 0 && hasPlayedSong && !transitioning) {
            transitioning = true;
            Game.transitionToNextScene(new CompletionScene(numScoreTypes, totalNotes));
        } else UpdateMusicStream(music);

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
                        try {
                            while (track.get(index).getPosition().x() + track.get(index).getRadius() < Settings.PADDING - track.get(index).getRadius()) {
                                index++;
                            }
                        } catch (IndexOutOfBoundsException e) {
                            index--;
                            TraceLog(LOG_DEBUG, "Attempted to move index out of bounds, length " + track.size() + ", index " + index);
                        } finally {
                            track.get(index).furthest = true;
                        }
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

        // Draw game objects
        for (int track = 0; track < tracks.length; track++) {
            DrawRectangle(0, (int)keys[track].getPosition().y() - keys[track].getRadius() + 10, Settings.SCREEN_WIDTH, 2 * keys[track].getRadius() - 20, Fade(BLACK, 0.25f));
            for (Note n : tracks[track]) {
                if (n.getPosition().x() - n.getRadius() < Settings.SCREEN_WIDTH) n.render();
            }
        }
        for (NoteKey nk : keys) {
            nk.render();
        }

        // Draw UI
        DrawRectangleGradientV(0, 0, Settings.SCREEN_WIDTH, 60, Fade(BLACK, 0.5f), BLANK);
        for (AbstractUIComponent auic : uiComponents) {
            auic.render();
        }
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
