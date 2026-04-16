package ui;

import core.Settings;

import static com.raylib.Colors.BLACK;
import static com.raylib.Raylib.*;

public class UITransition extends AbstractUIComponent {

    private UITransitionBox[][] boxes;
    private float timeToNextBoxSpawn = 0.05f;
    private float waitTimeAccumulator = 0.0f;
    private float volume = 1.0f;

    public boolean hasOpened = false;

    public UITransition() {
        this.boxes = new UITransitionBox[(int)Settings.UI_TRANSITION_DIMENSIONS_IN_BOXES.y()][(int)Settings.UI_TRANSITION_DIMENSIONS_IN_BOXES.x()];
        this.boxes[0][0] = new UITransitionBox(Vector2Zero(), this, waitTimeAccumulator);
    }

    @Override
    public void update(float dt) {
        // Volume control
        volume = Lerp(volume, hasOpened ? 1.0f : 0.0f, 0.1f);
        SetMasterVolume(volume);

        timeToNextBoxSpawn += dt;
        if (timeToNextBoxSpawn >= 0.05f) {
            // Update timer
            timeToNextBoxSpawn -= 0.05f;
            waitTimeAccumulator += 0.05f;

            for (int row = 0; row < boxes.length; row++) {
                // Break loop if row is empty, fill [row][0]
                if (boxes[row][0] == null) {
                    boxes[row][0] = new UITransitionBox(new Vector2().x(0).y(row), this, waitTimeAccumulator);
                    break;
                }

                for (int col = 0; col < boxes[row].length; col++) {
                    // Break row if col is empty, fill [row][col]
                    if (boxes[row][col] == null) {
                        boxes[row][col] = new UITransitionBox(new Vector2().x(col).y(row), this, waitTimeAccumulator);
                        break;
                    }
                }
            }
        }

        // Update boxes if not null
        for (int row = 0; row < boxes.length; row++) {
            for (int col = 0; col < boxes[row].length; col++) {
                UITransitionBox box = boxes[row][col];
                if (box != null) {
                    box.update(dt);
                    if (box.done) boxes[row][col] = null;
                }
            }
        }

        // Check to determine if we're transitioning in or out
        if (hasOpened) checkIfDone();
        else checkIfOpened();
    }

    @Override
    public void render() {
        for (UITransitionBox[] row : boxes)
            for (UITransitionBox box : row)
                if (box != null) box.render();
    }

    private void checkIfOpened() {
        for (UITransitionBox[] row : boxes) {
            for (UITransitionBox box : row) {
                if (box == null || !box.doneOpening) { // Box exists or isn't done, so we aren't open
                    return;
                }
            }
        }
        hasOpened = true;
    }

    private void checkIfDone() {
        for (UITransitionBox[] row : boxes) {
            for (UITransitionBox box : row) {
                if (box != null) { // Box exists, so we aren't done
                    return;
                }
            }
        }
        done = true;
    }

    private static class UITransitionBox extends AbstractUIComponent {

        private final UITransition parent;
        private final float waitingAfterFinish;

        private float scale = 0.0f;
        private float waitAccumulator = 0.0f;
        public boolean doneOpening = false;

        public UITransitionBox(Vector2 position, UITransition parent, float waitingAfterFinish) {
            this.parent = parent;
            this.position = Vector2Multiply(position, Settings.UI_TRANSITION_BOX_DIMENSIONS);
            this.waitingAfterFinish = waitingAfterFinish;
        }

        @Override
        public void update(float dt) {
            if (parent.hasOpened) { // Determine fading in or out
                waitAccumulator += dt;
                if (waitAccumulator >= waitingAfterFinish) // Only start lerping after enough time has passed
                    scale = Lerp(scale, 0.0f, 0.25f);
                if (scale <= 0.001f) done = true;
            } else {
                scale = Lerp(scale, 1.0f, 0.25f);

                if (scale >= 0.999f) doneOpening = true;
            }
        }

        @Override
        public void render() {
            DrawRectangleV(position, Vector2Scale(Settings.UI_TRANSITION_BOX_DIMENSIONS, scale), BLACK);
        }
    }
}
