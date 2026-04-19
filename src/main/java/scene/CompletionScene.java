package scene;

import static com.raylib.Colors.DARKBLUE;
import static com.raylib.Raylib.*;

import core.Game;
import core.Settings;
import ui.AbstractUIComponent;
import ui.completion.UICommenceForthText;
import ui.completion.UIScoreLineup;
import util.AssetLoader;

// Plays after a level is completed, basically stats viewer
public class CompletionScene extends AbstractScene {

    // For scrolling background
    private float bgOffset;

    public CompletionScene(int[] numHits, int totalNotes) {
        // General scene init
        this.metadata = new SceneMetadata(AssetLoader.getMusic("assets/sounds/completion.mp3"),
                        AssetLoader.getTexture("assets/textures/backgrounds/menu_bg.png"));
        PlayMusicStream(this.metadata.music());

        // Percentage of each type of note that was hit
        float[] percents = { (float)numHits[0] / totalNotes,
                  (float)numHits[1] / totalNotes,
                  (float)numHits[2] / totalNotes,
                  (float)numHits[3] / totalNotes };

        this.bgOffset = 0.0f;

        // Add UI
        uiComponents.add(new UIScoreLineup(numHits, totalNotes, percents));
        uiComponents.add(new UICommenceForthText(0.5f, 30));
    }

    @Override
    public void update(float dt) {
        UpdateMusicStream(metadata.music());

        for (AbstractUIComponent auic : uiComponents) {
            auic.update(dt);
        }

        // Return to main menu
        if (IsKeyPressed(KEY_SPACE)) Game.transitionToNextScene(Game.mainMenu);

        // Scroll background, loop back to start at end
        bgOffset += Settings.BACKGROUND_SCROLL_SPEED * dt;
        if (bgOffset <= -2 * Settings.SCREEN_WIDTH) bgOffset += 2 * Settings.SCREEN_WIDTH;
    }

    @Override
    public void render() {
        // Draw two backgrounds for seamless looping
        DrawTexture(metadata.background(), (int)bgOffset, 0, DARKBLUE);
        if (bgOffset <= -Settings.SCREEN_WIDTH) DrawTexture(metadata.background(), (int)bgOffset + 2 * Settings.SCREEN_WIDTH, 0, DARKBLUE);

        for (AbstractUIComponent auic : uiComponents) {
            auic.render();
        }
    }
}
