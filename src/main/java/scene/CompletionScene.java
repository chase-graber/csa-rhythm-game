package scene;

import static com.raylib.Colors.WHITE;
import static com.raylib.Raylib.*;

import core.Game;
import ui.AbstractUIComponent;
import ui.completion.UICommenceForthText;
import ui.completion.UIScoreLineup;
import util.AssetLoader;

public class CompletionScene extends AbstractScene {

    public CompletionScene(int[] numHits, int totalNotes) {
        this.music = AssetLoader.getMusic("assets/sounds/completion.mp3");
        this.background = AssetLoader.getTexture("assets/textures/backgrounds/demo_bg.png");
        PlayMusicStream(this.music);

        float[] percents = { (float)numHits[0] / totalNotes,
                  (float)numHits[1] / totalNotes,
                  (float)numHits[2] / totalNotes,
                  (float)numHits[3] / totalNotes };

        addUIComponent(new UIScoreLineup(numHits, totalNotes, percents));
        addUIComponent(new UICommenceForthText(0.5f, 30));
    }

    @Override
    public void update(float dt) {
        UpdateMusicStream(music);

        for (AbstractUIComponent auic : uiComponents) {
            auic.update(dt);
        }

        if (IsKeyPressed(KEY_SPACE)) Game.getInstance().transitionToNextScene(AssetLoader.loadLevelScene("spooktune.txt"));
    }

    @Override
    public void render() {
        DrawTextureV(background, Vector2Zero(), WHITE);

        for (AbstractUIComponent auic : uiComponents) {
            auic.render();
        }
    }
}
