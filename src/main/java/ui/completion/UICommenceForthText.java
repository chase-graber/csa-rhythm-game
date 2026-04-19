package ui.completion;

import static com.raylib.Colors.WHITE;
import static com.raylib.Raylib.*;

import core.Settings;
import ui.AbstractUIComponent;

// Blinking text at the bottom of CompletionScene
public class UICommenceForthText extends AbstractUIComponent {

    private final int fontSize;

    // Keep track of if it's showing or not
    private final float timing;
    private float accumulation = 0.0f;
    private boolean showing = true;

    public UICommenceForthText(float timing, int fontSize) {
        this.timing = timing;
        this.fontSize = fontSize;
    }

    @Override
    public void update(float dt) {
        accumulation += dt;

        // Swap showing and not showing, reset accumulator
        if (accumulation >= timing) {
            accumulation -= timing;
            showing = !showing;
        }
    }

    @Override
    public void render() {
        String text = "Press [SPACE] to return to main menu";
        float width = MeasureText(text, fontSize); // Ensure text is centered by screen x

        if (showing) DrawText(text, (int)(Settings.SCREEN_WIDTH - width) / 2, Settings.SCREEN_HEIGHT - Settings.COMPLETION_PADDING, fontSize, WHITE);
    }
}
