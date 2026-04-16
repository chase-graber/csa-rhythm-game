package ui.completion;

import static com.raylib.Colors.WHITE;
import static com.raylib.Colors.BLACK;
import static com.raylib.Raylib.*;

import core.Settings;
import ui.AbstractUIComponent;
import util.AssetLoader;
import util.UtilMethods;

public class UIScoreLineup extends AbstractUIComponent {

    private final int[] numHits;
    private final int totalNotes;
    private final float[] percentages;

    // Middle textures
    private final Texture[] textures;
    private final Rectangle[] texRects;
    private float[] texRotations;
    private final float[] texRotationOffsets;

    public UIScoreLineup(int[] numHits, int totalNotes, float[] percentages) {
        this.numHits = numHits;
        this.totalNotes = totalNotes;
        this.percentages = percentages;
        this.textures = new Texture[]{
                AssetLoader.getTexture("assets/textures/ui/textPopup_0.png"),
                AssetLoader.getTexture("assets/textures/ui/textPopup_1.png"),
                AssetLoader.getTexture("assets/textures/ui/textPopup_2.png"),
                AssetLoader.getTexture("assets/textures/ui/textPopup_3.png")
        };
        this.texRects = new Rectangle[]{
                UtilMethods.getTextureRect(textures[0], new Vector2().x((float)Settings.SCREEN_WIDTH / 2).y(Settings.COMPLETION_PADDING)),
                UtilMethods.getTextureRect(textures[1], new Vector2().x((float)Settings.SCREEN_WIDTH / 2).y(Settings.COMPLETION_PADDING + Settings.SPACING)),
                UtilMethods.getTextureRect(textures[2], new Vector2().x((float)Settings.SCREEN_WIDTH / 2).y(Settings.COMPLETION_PADDING + Settings.SPACING * 2)),
                UtilMethods.getTextureRect(textures[3], new Vector2().x((float)Settings.SCREEN_WIDTH / 2).y(Settings.COMPLETION_PADDING + Settings.SPACING * 3)),
        };
        this.texRotations = new float[]{ 0, 0, 0, 0 };
        this.texRotationOffsets = new float[]{
                (float) (Math.random() * PI),
                (float) (Math.random() * PI),
                (float) (Math.random() * PI),
                (float) (Math.random() * PI)
        };
    }

    @Override
    public void update(float dt) {
        for (int i = 0; i < texRotations.length; i++) {
            texRotations[i] = (float)Math.sin(GetTime() + texRotationOffsets[i]) * 5;
        }
    }

    @Override
    public void render() {
        for (int i = 0; i < textures.length; i++) {
            DrawTexturePro(textures[i],
                    UtilMethods.getTextureRect(textures[i]),
                    texRects[i],
                    UtilMethods.getTextureMiddle(textures[i]),
                    texRotations[i],
                    WHITE
            );
        }

        for (int i = 0; i < numHits.length; i++) {
            String totalsText = numHits[i] + "/" + totalNotes;
            Vector2 totalsSize = MeasureTextEx(GetFontDefault(), totalsText, 20, 0);
            String percentageText = Math.round(percentages[i] * 1000) / 10.0f + "%";
            Vector2 percentageSize = MeasureTextEx(GetFontDefault(), percentageText, 20, 0);

            DrawText(totalsText,
                    (int)((texRects[i].x() - 2.5 * Settings.SPACING) - (totalsSize.x() / 2)),
                    (int)(texRects[i].y() - (totalsSize.y() / 2)),
                    50, BLACK);
            DrawText(percentageText,
                    (int)((texRects[i].x() + 2 * Settings.SPACING) - (percentageSize.x() / 2)),
                    (int)(texRects[i].y() - (percentageSize.y() / 2)),
                    50, BLACK);
        }
    }
}
