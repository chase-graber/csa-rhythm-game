package scene;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

import java.util.ArrayList;

import com.raylib.Raylib.Vector2;

import core.Game;
import object.Note;
import object.NoteKey;
import ui.AbstractUIComponent;
import ui.level.UIProgressBar;
import core.Settings;
import ui.menu.UIMenuButton;
import ui.menu.UISlider;

// Main gameplay
public class LevelScene extends AbstractScene {

    // General/misc
    private float elapsedTime = 0.0f;
    private boolean hasPlayedSong = false;
    private boolean transitioning = false;

    // 4 tracks, each with one note key and an arbitrary number of notes
    private NoteKey[] keys;
    private ArrayList<Note>[] tracks;

    // Scoring types
    public int[] numScoreTypes = { 0, 0, 0, 0 };
    private int totalNotes;

    // Pausing
    private boolean paused = false;
    private final UIMenuButton<AbstractScene> returnToMainMenuButton = new UIMenuButton<>(350,
            new Vector2().x(400).y(100),
            "RETURN TO MAIN MENU",
            Game::transitionToNextScene,
            Game.mainMenu);
    private final UISlider settingsVolSlider = new UISlider("Volume", 550, 0, 100, Settings.volume * 100, 200, Settings::updateVolume);

    public LevelScene() {
        // Ensure that note keys are mapped to the current key layout
        updateKeyLayout();
    }

    // Addons to constructor, called in AssetLoader since notes need a parent scene (which can't exist if this is part of the constructor)
    public void applyMetadata(SceneMetadata md) {
        this.metadata = md;
        this.metadata.music().looping(false);

        uiComponents.add(new UIProgressBar(md.music())); // UIProgressBar always at index 0
    }

    public void setLevelTracks(ArrayList<Note>[] tracks) {
        this.tracks = tracks;
        for (ArrayList<Note> track : tracks) {
            this.totalNotes += track.size();
        }
    }

    @Override
    public void update(float dt) {
        // Check pause toggle
        if (IsKeyPressed(KEY_P)) paused = !paused;

        // Update level
        if (!paused) {
            // Update UI
            for (int i = uiComponents.size() - 1; i >= 0; i--) {
                AbstractUIComponent auic = uiComponents.get(i);
                if (auic.done) uiComponents.remove(i);

                auic.update(dt);
            }

            elapsedTime += dt;
            if (elapsedTime >= 2.9f && !IsMusicStreamPlaying(metadata.music()) && !hasPlayedSong) { // Level is starting
                // Update music stream
                PlayMusicStream(metadata.music());
                hasPlayedSong = true;
            } else if (((UIProgressBar) uiComponents.get(0)).getCurrentSongTime() == 0 && hasPlayedSong && !transitioning) { // Level is over
                // Begin scene transition
                transitioning = true;
                Game.transitionToNextScene(new CompletionScene(numScoreTypes, totalNotes));
            } else UpdateMusicStream(metadata.music()); // Level is still playing

            // Update keys (detect presses)
            for (NoteKey nk : keys) {
                nk.update(dt);
            }

            // Move the notes
            for (ArrayList<Note> track : tracks) {
                for (int i = track.size() - 1; i >= 0; i--) {
                    Note n = track.get(i);

                    if (n.done) { // Note has either been hit or is offscreen
                        track.remove(i);

                        if (!track.isEmpty()) { // Don't bother doing anything if it was the last note
                            int index = 0;

                            // Decides which note is the furthest based on position and index, lowk forgot what any of this means its so convoluted but at least it works
                            try {
                                while (track.get(index).getPosition().x() + track.get(index).getRadius() < Settings.PADDING - track.get(index).getRadius()) {
                                    index++;
                                }
                            } catch (IndexOutOfBoundsException e) {
                                index--;
                                TraceLog(LOG_ERROR, "Attempted to move index out of bounds, length " + track.size() + ", index " + index);
                            } finally {
                                track.get(index).furthest = true;
                            }
                        }
                        continue;
                    } else if (n.getPosition().x() + n.getRadius() < Settings.PADDING - n.getRadius()) { // Don't let notes that passed the note key be the furthest
                        n.furthest = false;
                        if (i < track.size() - 1) track.get(i + 1).furthest = true;
                    }

                    n.update(dt); // Update note position
                }
            }
        } else { // Update pause menu UI
            returnToMainMenuButton.update(dt);
            settingsVolSlider.update(dt);
        }
    }

    @Override
    public void render() {
        // Draw background
        DrawTextureV(metadata.background(), Vector2Zero(), WHITE);

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

        // Draw pause menu
        if (paused) {
            DrawRectangleV(Vector2Zero(), new Vector2().x(Settings.SCREEN_WIDTH).y(Settings.SCREEN_HEIGHT), Fade(BLACK, 0.25f));

            returnToMainMenuButton.render();
            settingsVolSlider.render();

            float textWidth = MeasureText("PAUSED", 50);
            DrawText("PAUSED", (int)(Settings.SCREEN_WIDTH - textWidth) / 2, 50, 50, WHITE);
            textWidth = MeasureText("PRESS P TO UNPAUSE", 30);
            DrawText("PRESS P TO UNPAUSE", (int)(Settings.SCREEN_WIDTH - textWidth) / 2, 120, 30, WHITE);
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
