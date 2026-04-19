package ui.menu;

import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

import core.Settings;
import ui.AbstractUIComponent;

import java.util.function.Consumer;

// Slider that has a range between a min and max float (like a volume slider)
public class UISlider extends AbstractUIComponent {

    // Label so that player knows what the slider is for
    private final String label;

    // Bounds and display width of the slider
    private final float min, max;
    private final float width;

    // Current value the slider holds
    private float value;

    // X position of slider handle
    private float xPos;

    // Hitbox for slider handle
    private Rectangle bounds;

    // Method to be executed whenever the slider is updated
    private final Consumer<Float> method;

    // Multiple constructors so that I don't have to define default values
    public UISlider(String label, float height, float min, float max, float defaultValue, float width, Consumer<Float> method) {
        this(label, new Vector2().x((Settings.SCREEN_WIDTH - width) / 2).y(height), min, max, defaultValue, width, method);
    }

    // Main constructor
    public UISlider(String label, Vector2 position, float min, float max, float defaultValue, float width, Consumer<Float> method) {
        // General init
        this.position = position;

        this.label = label;

        this.min = min;
        this.max = max;
        this.width = width;

        this.value = Clamp(defaultValue, min, max);
        this.xPos = position.x() + defaultValue * width / (max - min);

        this.bounds = new Rectangle()
                .x(xPos - Settings.SLIDER_RADIUS).y(position.y() - Settings.SLIDER_RADIUS + Settings.SLIDER_TRACK_HEIGHT / 2)
                .width(Settings.SLIDER_RADIUS * 2).height(Settings.SLIDER_RADIUS * 2);

        this.method = method;
    }

    @Override
    public void update(float dt) {
        Vector2 md = GetMouseDelta();

        // Handle is grabbed
        if (IsMouseButtonDown(MOUSE_BUTTON_LEFT) &&
                (CheckCollisionPointRec(GetMousePosition(), bounds) || CheckCollisionPointRec(Vector2Subtract(GetMousePosition(), GetMouseDelta()), bounds))) {
            // Change visual position and clamp it inside the slider
            xPos += md.x();
            xPos = Clamp(xPos, position.x(), position.x() + width);
            bounds.x(Clamp(bounds.x() + md.x(), position.x() - Settings.SLIDER_RADIUS, position.x() + width - Settings.SLIDER_RADIUS));

            // Recalculate value based on xPos
            value = (xPos - position.x()) * (max - min) / width;

            // Update setting with value
            method.accept(value);
        }
    }

    @Override
    public void render() {
        // Draw slider label
        int textWidth = MeasureText(label, 20);
        DrawText(label, (int)(position.x() - textWidth - Settings.SLIDER_PADDING), (int)(position.y() - Settings.SLIDER_RADIUS / 2), 20, WHITE);

        // Draw slider back rectangle with proper width
        DrawRectangleV(position, new Vector2().x(width).y(Settings.SLIDER_TRACK_HEIGHT), GRAY);
        DrawCircle((int)xPos, (int)(position.y() + Settings.SLIDER_TRACK_HEIGHT / 2), Settings.SLIDER_RADIUS, WHITE);

        // Draw slider value to the right of the slider
        DrawTextEx(
                GetFontDefault(),
                Float.toString(Math.round(value * 10) / 10.0f),
                Vector2Add(position, new Vector2().x(width + Settings.SLIDER_PADDING).y(-Settings.SLIDER_RADIUS / 2)),
                20,
                1,
                WHITE);

        // Debug drawing
        if (Settings.DEBUG) {
            DrawRectangleLinesEx(bounds, 2, RED);
        }
    }
}
