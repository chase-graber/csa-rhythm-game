package ui;

import core.Settings;

import static com.raylib.Colors.BLACK;
import static com.raylib.Raylib.*;

// Plays when changing scenes
public class UITransition extends AbstractUIComponent {

    // Boxes and counters to whenever the next box comes in
    private UITransitionBox[][] boxes;
    private float timeToNextBoxSpawn = 0.05f;
    private float waitTimeAccumulator = 0.0f;

    // Volume control
    private float volume = 1.0f;

    // Scene changes when this becomes true
    public boolean hasOpened = false;

    public UITransition() {
        this.boxes = new UITransitionBox[(int)Settings.UI_TRANSITION_DIMENSIONS_IN_BOXES.y()][(int)Settings.UI_TRANSITION_DIMENSIONS_IN_BOXES.x()];
        this.boxes[0][0] = new UITransitionBox(Vector2Zero(), this, waitTimeAccumulator); // Make first box
    }

    @Override
    public void update(float dt) {
        // Volume control
        volume = Lerp(volume, hasOpened ? 1.0f : 0.0f, 0.1f);
        SetMasterVolume(volume);

        // Accumulator
        timeToNextBoxSpawn += dt;
        if (timeToNextBoxSpawn >= 0.05f) {
            // Update timer
            timeToNextBoxSpawn -= 0.05f;
            // Increase the amount of time the boxes that are being added this iteration will wait to disappear once everything has opened
            waitTimeAccumulator += 0.05f;

            /*
            * Fills array with boxes in a sweep from top left to bottom right, as demonstrated below
            *
            * # # # # # #
            * # * . . . #
            * # . . . . #
            * # . . . . #
            * # . . . . #
            * # # # # # #
            *
            * # # # # # #
            * # * * . . #
            * # * . . . #
            * # . . . . #
            * # . . . . #
            * # # # # # #
            *
            * # # # # # #
            * # * * * . #
            * # * * . . #
            * # * . . . #
            * # . . . . #
            * # # # # # #
            *
            * etc.
            */
            for (int row = 0; row < boxes.length; row++) {
                // Break loop if row is empty, fill [row][0]
                if (boxes[row][0] == null) {
                    boxes[row][0] = new UITransitionBox(new Vector2().x(0).y(row), this, waitTimeAccumulator);
                    break;
                }

                for (int col = 1; col < boxes[row].length; col++) {
                    // Move to next row if col is empty, fill [row][col]
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
        // Draw all existing boxes
        for (UITransitionBox[] row : boxes)
            for (UITransitionBox box : row)
                if (box != null) box.render();
    }

    // Decide when all boxes have fully opened, change hasOpened once they have
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

    // Decide when all boxes have disparaged, change done once they have
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

    // Each box with its own scale
    private static class UITransitionBox extends AbstractUIComponent {

        // Reference to the parent transition to find out when everything has opened
        private final UITransition parent;

        // How long to wait once everything has opened
        private final float waitingAfterFinish;

        // Visual stuff and accumulators
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
            if (parent.hasOpened) { // Fading in if true, fading out if false
                waitAccumulator += dt;
                if (waitAccumulator >= waitingAfterFinish) // Only start lerping after enough time has passed
                    scale = Lerp(scale, 0.0f, 0.25f);
                if (scale <= 0.001f) done = true; // Done when within bounds since lerping takes forever to actually reach zero
            } else {
                scale = Lerp(scale, 1.0f, 0.25f);

                if (scale >= 0.999f) doneOpening = true; // Done when within bounds since lerping takes forever to actually reach zero
            }
        }

        @Override
        public void render() {
            DrawRectangleV(position, Vector2Scale(Settings.UI_TRANSITION_BOX_DIMENSIONS, scale), BLACK);
        }
    }
}
