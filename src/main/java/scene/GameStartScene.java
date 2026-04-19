package scene;

import static com.raylib.Colors.WHITE;
import static com.raylib.Raylib.*;

import core.Game;
import core.Settings;
import core.TickableGameEntity;

// Plays on game boot and never again
public class GameStartScene extends AbstractScene {

    // Text to be displayed in order
    private final String[] displayText = {
            "A GAME MADE WITH RAYLIB",
            "BART PRODUCTIONS PRESENTS:"
    };
    private int currentIndex;

    private TextComponent currentText;
    private boolean transitioning = false;

    public GameStartScene() {
        this.currentText = new TextComponent(this.displayText[0]);
        this.currentIndex = 0;
    }

    @Override
    public void update(float dt) {
        currentText.update(dt);

        // Swap text when done
        if (currentText.done) {
            currentIndex++;

            // Change text or change scene depending on which index is current
            if (currentIndex < displayText.length) currentText = new TextComponent(displayText[currentIndex]);
            else if (!transitioning) { // Don't start a new transition every frame
                Game.transitionToNextScene(Game.mainMenu);
                transitioning = true;
            }
        }
    }

    @Override
    public void render() {
        currentText.render();
    }

    // Text that appears and fades
    private static class TextComponent implements TickableGameEntity {

        // Text and opacity
        private final String text;
        private float alpha;

        // Timing stuff
        private static final float totalTimeOpaque = 2.0f;
        private float timeAccumulator;

        // Basically a state machine
        private boolean fadingIn, fadingOut = false;
        public boolean done = false;

        public TextComponent(String text) {
            this.text = text;
            this.alpha = 0.0f;
            this.fadingIn = true;
        }

        @Override
        public void update(float dt) {
            if (fadingIn) { // Increase opacity
                alpha += dt * 2;
                if (alpha >= 1.0f) {
                    alpha = 1.0f;
                    fadingIn = false;
                }
            } else if (fadingOut) { // Decrease opacity
                alpha -= dt * 2;
                if (alpha <= 0.0f) {
                    fadingOut = false;
                    done = true;
                }
            } else { // Hold for the extent of the total time to be opaque
                timeAccumulator += dt;
                if (timeAccumulator >= totalTimeOpaque) {
                    fadingOut = true;
                }
            }
        }

        @Override
        public void render() {
            // Draw in the center of the screen
            Vector2 textDimensions = MeasureTextEx(GetFontDefault(), text, 30, 2);
            DrawTextEx(GetFontDefault(), text, new Vector2().x((Settings.SCREEN_WIDTH - textDimensions.x()) / 2)
                        .y((Settings.SCREEN_HEIGHT - textDimensions.y()) / 2), 30, 2, Fade(WHITE, alpha));
        }
    }
}
