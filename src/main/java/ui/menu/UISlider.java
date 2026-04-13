package ui.menu;

import static com.raylib.Raylib.*;

import core.Settings;
import ui.AbstractUIComponent;

// TODO: finish when you get motivation
public class UISlider extends AbstractUIComponent {

    private final float min, max;
    private final float width;

    private float value;
    private float xPos;

    private Rectangle bounds;

    public UISlider(Vector2 position, float min, float max, float width, float defaultValue) {
        this.position = position;

        this.min = min;
        this.max = max;
        this.width = width;

        this.value = Clamp(defaultValue, min, max);
        this.xPos = position.x() + (defaultValue * width / (max - min));

        this.bounds = new Rectangle()
                .x(xPos - Settings.SLIDER_RADIUS).y(position.y() - Settings.SLIDER_RADIUS + Settings.SLIDER_TRACK_HEIGHT / 2)
                .width(Settings.SLIDER_RADIUS * 2).height(Settings.SLIDER_RADIUS * 2);
    }

    @Override
    public void update(float dt) {
        Vector2 md = GetMouseDelta();

        if (IsMouseButtonDown(MOUSE_BUTTON_LEFT) && CheckCollisionPointRec(GetMousePosition(), bounds)) {
            xPos -= md.x();
            xPos = Clamp(xPos, position.x(), position.x() + width);
            bounds.x(bounds.x() - md.x());

            // Recalculate value
            value = xPos * (max - min) / width;
        }
    }

    @Override
    public void render() {

    }
}
