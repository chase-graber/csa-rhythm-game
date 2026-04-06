package ui;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

import util.AssetLoader;
import core.Settings;
import util.UtilMethods;

public class UIProgressBar extends AbstractUIComponent {

    private Music songToProgress;

    private final Rectangle bounds;
    private final float totalSongTime;
    private float currentSongTime;
    private boolean hasStarted = false;

    public UIProgressBar(Music songToProgress) {
        this.bounds = new Rectangle()
            .x(Settings.PROGRESS_BAR_POSITION.x())
            .y(Settings.PROGRESS_BAR_POSITION.y())
            .width(Settings.PROGRESS_BAR_DIMENSIONS.x())
            .height(Settings.PROGRESS_BAR_DIMENSIONS.y());

        this.songToProgress = songToProgress;
        this.totalSongTime = GetMusicTimeLength(songToProgress);
        this.currentSongTime = 0;
        this.texture = AssetLoader.getTexture("assets/textures/ui/border_with_bubble.png");
    }

    @Override
    public void update(float dt) {
        currentSongTime = GetMusicTimePlayed(songToProgress);
        if (!hasStarted && currentSongTime > 0) hasStarted = true;
    }

    @Override
    public void render() {
        float percentDone = currentSongTime / totalSongTime;
        DrawRectangleV(Settings.PROGRESS_BAR_POSITION,
                new Vector2().x((hasStarted && currentSongTime == 0 ? 1.0f : percentDone) * Settings.PROGRESS_BAR_DIMENSIONS.x())
                        .y(Settings.PROGRESS_BAR_DIMENSIONS.y()),
                GOLD);

        DrawTexturePro(texture, UtilMethods.getTextureRect(texture), bounds, Vector2Zero(), 0, WHITE);
    }

    public float getCurrentSongTime() {
        return currentSongTime;
    }
}
