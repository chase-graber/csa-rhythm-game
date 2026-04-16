package scene;

import static com.raylib.Colors.DARKBLUE;
import static com.raylib.Raylib.*;

import core.Game;
import core.Settings;
import ui.AbstractUIComponent;
import ui.completion.UICommenceForthText;
import ui.completion.UIScoreLineup;
import util.AssetLoader;

public class CompletionScene extends AbstractScene {

    private float bgOffset;

    public CompletionScene(int[] numHits, int totalNotes) {
        this.music = AssetLoader.getMusic("assets/sounds/completion.mp3");
        this.background = AssetLoader.getTexture("assets/textures/backgrounds/menu_bg.png");
        PlayMusicStream(this.music);

        float[] percents = { (float)numHits[0] / totalNotes,
                  (float)numHits[1] / totalNotes,
                  (float)numHits[2] / totalNotes,
                  (float)numHits[3] / totalNotes };

        this.bgOffset = 0.0f;

        uiComponents.add(new UIScoreLineup(numHits, totalNotes, percents));
        uiComponents.add(new UICommenceForthText(0.5f, 30));
    }

    @Override
    public void update(float dt) {
        UpdateMusicStream(music);

        for (AbstractUIComponent auic : uiComponents) {
            auic.update(dt);
        }

        if (IsKeyPressed(KEY_SPACE)) Game.transitionToNextScene(Game.mainMenu);

        bgOffset += Settings.BACKGROUND_SCROLL_SPEED * dt;
        if (bgOffset <= -2 * Settings.SCREEN_WIDTH) bgOffset += 2 * Settings.SCREEN_WIDTH;
    }

    @Override
    public void render() {
        DrawTexture(background, (int)bgOffset, 0, DARKBLUE);
        DrawTexture(background, (int)bgOffset + 2 * Settings.SCREEN_WIDTH, 0, DARKBLUE);

        for (AbstractUIComponent auic : uiComponents) {
            auic.render();
        }
    }
}
