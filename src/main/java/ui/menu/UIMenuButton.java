package ui.menu;

import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

import core.Settings;
import ui.AbstractUIComponent;
import util.UtilMethods;

import java.util.function.Consumer;

/*
* I am so freaking smart
*/
public class UIMenuButton<T> extends AbstractUIComponent {

    private final T input;
    private final Consumer<T> method;

    private float scale = 1.0f;
    private Rectangle boundingBox;

    private final String text;

    public UIMenuButton(float height, String text, Consumer<T> method, T input) {
        this.position = new Vector2().x(Settings.MENU_BUTTON_X).y(height);
        this.boundingBox = new Rectangle()
                .x(this.position.x()).y(this.position.y())
                .width(Settings.MENU_BUTTON_DIMENSIONS.x()).height(Settings.MENU_BUTTON_DIMENSIONS.y());

        this.text = text;
        this.input = input;
        this.method = method;
    }

    @Override
    public void update(float dt) {
        boolean withinBounds = CheckCollisionPointRec(GetMousePosition(), boundingBox);
        float targetScale = withinBounds ? 1.05f : 1.0f;

        scale = Lerp(scale, targetScale, 0.25f);

        if (IsMouseButtonPressed(MOUSE_BUTTON_LEFT) && withinBounds) {
            System.out.println(input);
            method.accept(input);
        }
    }

    @Override
    public void render() {
        // TODO: fix rect positioning
        Rectangle drawRect = UtilMethods.scaleRectangleAroundCenter(boundingBox, scale);
        DrawRectangleRounded(drawRect, 1, 1, BLACK);
        DrawRectangleRoundedLinesEx(drawRect, 1, 1, 3, WHITE);

        // TODO: draw text
//        Vector2 textWidth = MeasureTextEx(GetFontDefault(), text, (int)(30 * scale), 0);
//        DrawText();

        if (Settings.DEBUG) {
            DrawRectangleLinesEx(boundingBox, 2, RED);
        }
    }
}
