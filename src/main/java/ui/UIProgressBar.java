package ui;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

import util.AssetLoader;
import util.Settings;

public class UIProgressBar {

    private Music songToProgress;

    private final Rectangle bounds;
    private final float totalSongTime;
    private float currentSongTime;

    private final Texture borderTexture;

    public UIProgressBar(Music songToProgress) {
        this.bounds = new Rectangle()
            .x(Settings.PROGRESS_BAR_POSITION.x())
            .y(Settings.PROGRESS_BAR_POSITION.y())
            .width(Settings.PROGRESS_BAR_DIMENSIONS.x())
            .height(Settings.PROGRESS_BAR_DIMENSIONS.y());

        this.songToProgress = songToProgress;
        this.totalSongTime = GetMusicTimeLength(songToProgress);
        this.currentSongTime = 0;
        this.borderTexture = AssetLoader.getTexture("assets/textures/border_with_bubble.png");
    }

    public void update() {
        currentSongTime = GetMusicTimePlayed(songToProgress);
    }

    public void render() {
        float percentDone = currentSongTime / totalSongTime;
        DrawRectangleV(Settings.PROGRESS_BAR_POSITION, new Vector2().x(percentDone * Settings.PROGRESS_BAR_DIMENSIONS.x()).y(Settings.PROGRESS_BAR_DIMENSIONS.y()), GOLD);

        DrawTexturePro(borderTexture, new Rectangle().width(borderTexture.width()).height(borderTexture.height()), bounds, Vector2Zero(), 0, WHITE);
    }
    
}
