package ui;

import core.Settings;

import static com.raylib.Colors.BLACK;
import static com.raylib.Raylib.*;

public class UITransition extends AbstractUIComponent {

    private final boolean fadingIn;
    private float alpha;
    private float volume;

    public UITransition(boolean fadingIn) {
        this.fadingIn = fadingIn;
        this.alpha = this.fadingIn ? 0.0f : 1.0f;
        this.volume = this.fadingIn ? 1.0f : 0.0f;
    }

    @Override
    public void update(float dt) {
        alpha += fadingIn ? dt : -dt;
        volume -= fadingIn ? dt : -dt;
        SetMasterVolume(volume);
        if (alpha < 0.0f || alpha > 1.0f) done = true;
    }

    @Override
    public void render() {
        DrawRectangleV(Vector2Zero(), new Vector2().x(Settings.SCREEN_WIDTH).y(Settings.SCREEN_HEIGHT), Fade(BLACK, alpha));
    }

    // Old version, might work on later
//    private UITransitionBox[][] boxes;
//    private Color color;
//    private float timeToNextBoxSpawn = 0.1f;
//
//    public boolean hasOpened = false;
//
//    public UITransition(Color color) {
//        this.color = color;
//        this.boxes = new UITransitionBox[(int)Settings.UI_TRANSITION_DIMENSIONS_IN_BOXES.x()][(int)Settings.UI_TRANSITION_DIMENSIONS_IN_BOXES.y()];
//        this.boxes[0][0] = new UITransitionBox(Vector2Zero(), color);
//    }
//
//    @Override
//    public void update(float dt) {
//        // Create new boxes when the time is right
//        timeToNextBoxSpawn -= dt;
//        if (!hasOpened && timeToNextBoxSpawn <= 0.0f) {
//            timeToNextBoxSpawn += 0.1f;
//            for (int row = 0; row < boxes.length; row++) {
//                if (boxes[row][0] == null) {
//                    boxes[row][0] = new UITransitionBox(new Vector2().x(row).y(0), color);
//                    break;
//                }
//                for (int col = 0; col < boxes[row].length; col++) {
//                    if (boxes[row][col] == null) {
//                        boxes[row][col] = new UITransitionBox(new Vector2().x(row).y(col), color);
//                        break;
//                    } else if (boxes[row][col] != null) { // Update box if not null
//                        boxes[row][col].update(dt);
//                    } else if (row == Settings.UI_TRANSITION_DIMENSIONS_IN_BOXES.x() && col == Settings.UI_TRANSITION_DIMENSIONS_IN_BOXES.y()) {
//                        hasOpened = true;
//                    }
//                }
//            }
//        } else {
//            if (boxes[0][0].done) return; // Don't even bother updating if it's done
//            for (UITransitionBox[] row : boxes)
//                for (UITransitionBox box : row)
//                    if (box != null) box.fade(dt);
//        }
//    }
//
//    @Override
//    public void render() {
//        for (UITransitionBox[] row : boxes)
//            for (UITransitionBox box : row)
//                if (box != null) box.render();
//    }
//
//    private boolean areAllBoxesDone() {
//        for (UITransitionBox[] row : boxes) {
//            for (UITransitionBox box : row) {
//                if (!box.done) return false;
//            }
//        }
//        return true;
//    }
//
//    private static class UITransitionBox extends AbstractUIComponent {
//
//        private final Color color;
//
//        private float scale = 0.0f;
//        private float alpha = 1.0f;
//
//        public UITransitionBox(Vector2 position, Color color) {
//            this.position = Vector2Multiply(position, Settings.UI_TRANSITION_BOX_DIMENSIONS);
//            this.color = color;
//        }
//
//        @Override
//        public void update(float dt) {
//            scale = Lerp(scale, 1.0f, 0.5f);
//        }
//
//        @Override
//        public void render() {
//            DrawRectangleV(position, Vector2Scale(Settings.UI_TRANSITION_BOX_DIMENSIONS, scale), Fade(color, alpha));
//        }
//
//        public void fade(float dt) {
//            alpha -= dt * 2;
//            if (alpha <= 0.0f) done = true;
//        }
//    }
}
