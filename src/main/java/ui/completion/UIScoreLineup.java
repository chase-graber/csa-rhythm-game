package ui.completion;

import static com.raylib.Colors.WHITE;
import static com.raylib.Raylib.*;

import core.Settings;
import ui.AbstractUIComponent;
import util.AssetLoader;
import util.UtilMethods;

public class UIScoreLineup extends AbstractUIComponent {

    private final int[] totals;
    private final float[] percentages;

    // Middle textures
    private final Texture[] textures;
    private final Rectangle[] texRects;
    private float[] texRotations;
    private final float[] texRotationOffsets;

    public UIScoreLineup(int[] totals, float[] percentages) {
        this.totals = totals;
        this.percentages = percentages;
        this.textures = new Texture[]{
                AssetLoader.getTexture("assets/textures/ui/textPopup_0.png"),
                AssetLoader.getTexture("assets/textures/ui/textPopup_1.png"),
                AssetLoader.getTexture("assets/textures/ui/textPopup_2.png"),
                AssetLoader.getTexture("assets/textures/ui/textPopup_3.png")
        };
        this.texRects = new Rectangle[]{
                UtilMethods.getTextureRect(textures[0], new Vector2().x((float)Settings.SCREEN_WIDTH / 2).y(Settings.PADDING)),
                UtilMethods.getTextureRect(textures[1], new Vector2().x((float)Settings.SCREEN_WIDTH / 2).y(Settings.PADDING + Settings.SPACING)),
                UtilMethods.getTextureRect(textures[2], new Vector2().x((float)Settings.SCREEN_WIDTH / 2).y(Settings.PADDING + Settings.SPACING * 2)),
                UtilMethods.getTextureRect(textures[3], new Vector2().x((float)Settings.SCREEN_WIDTH / 2).y(Settings.PADDING + Settings.SPACING * 3)),
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
            texRotations[i] = (float)Math.sin(GetTime() + texRotationOffsets[i]) * 10;
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
    }
}
