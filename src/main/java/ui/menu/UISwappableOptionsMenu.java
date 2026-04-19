package ui.menu;

import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

import core.Settings;
import core.TickableGameEntity;
import ui.AbstractUIComponent;

import java.util.function.Consumer;

// Set of options that can be cycled through with the arrows
public class UISwappableOptionsMenu<T> extends AbstractUIComponent {

    // Display stuff
    private final float width;

    // Arrows to switch between values
    private final UISwappableOptionsMenuArrow leftArrow;
    private final UISwappableOptionsMenuArrow rightArrow;

    // Values to swap between and what is displayed for each value
    private T[] values;
    private String[] displayNames;
    private int currentIndex;

    // Label so the player knows what the menu is for
    private final String label;

    // Method to execute on interaction with an arrow
    private final Consumer<T> method;

    public UISwappableOptionsMenu(String label, float height, float width, T[] values, String[] displayNames, Consumer<T> method, int defaultIndex) {
        // General init
        this.position = new Vector2().x((Settings.SCREEN_WIDTH - width) / 2).y(height);
        this.width = width;

        this.values = values;
        this.displayNames = displayNames;
        this.currentIndex = defaultIndex;

        this.label = label;
        this.method = method;

        // Give arrows proper positioning and rotation
        this.leftArrow = new UISwappableOptionsMenuArrow(new Rectangle()
                .x(position.x() - Settings.OPTION_MENU_ARROW_SIZE * 2 - Settings.OPTION_MENU_SIDE_PADDING)
                .y(position.y() - Settings.OPTION_MENU_ARROW_SIZE + Settings.OPTION_MENU_HEIGHT / 2)
                .width(Settings.OPTION_MENU_ARROW_SIZE * 2)
                .height(Settings.OPTION_MENU_ARROW_SIZE * 2),
                180);
        this.rightArrow = new UISwappableOptionsMenuArrow(new Rectangle()
                .x(position.x() + width + Settings.OPTION_MENU_SIDE_PADDING)
                .y(position.y() - Settings.OPTION_MENU_ARROW_SIZE + Settings.OPTION_MENU_HEIGHT / 2)
                .width(Settings.OPTION_MENU_ARROW_SIZE * 2)
                .height(Settings.OPTION_MENU_ARROW_SIZE * 2),
                0);
    }

    @Override
    public void update(float dt) {
        leftArrow.update(dt);
        rightArrow.update(dt);

        // If either arrow is pressed
        if (leftArrow.pressed || rightArrow.pressed) {
            currentIndex += Boolean.compare(rightArrow.pressed, leftArrow.pressed); // -1 if left, 1 if right

            // Loop index around if out of bounds
            if (currentIndex < 0) currentIndex += values.length;
            else if (currentIndex >= values.length) currentIndex -= values.length;

            // Execute method with the value that was switched to
            method.accept(values[currentIndex]);
        }
    }

    @Override
    public void render() {
        leftArrow.render();
        rightArrow.render();

        // Draw display name for the current value
        Vector2 textDimensions = MeasureTextEx(GetFontDefault(), displayNames[currentIndex], 30, 1);
        DrawTextEx(
                GetFontDefault(),
                displayNames[currentIndex],
                new Vector2()
                        .x(position.x() + (width - textDimensions.x()) / 2)
                        .y(position.y() + (Settings.OPTION_MENU_HEIGHT - textDimensions.y()) / 2),
                30,
                1,
                WHITE
                );

        // Draw menu label
        float labelDimensions = MeasureText(label, 30);
        DrawTextEx(
                GetFontDefault(),
                label,
                new Vector2()
                        .x(position.x() + (width - labelDimensions) / 2)
                        .y(position.y() - Settings.OPTION_MENU_SIDE_PADDING - 15),
                30,
                1,
                WHITE
        );

        // Debug drawing
        if (Settings.DEBUG) {
            DrawRectangleLinesEx(new Rectangle().x(position.x()).y(position.y()).width(width).height(Settings.OPTION_MENU_HEIGHT), 2, RED);
        }
    }

    // Self-contained arrow
    private static class UISwappableOptionsMenuArrow implements TickableGameEntity {

        // Hitbox
        private Rectangle hitbox;

        // Arrow rotation in degrees
        private final float rotation;

        // Size to change on hover
        private float size;
        private float targetSize;

        // Detect interaction
        public boolean pressed = false;

        public UISwappableOptionsMenuArrow(Rectangle hitbox, float rotation) {
            // General init
            this.hitbox = hitbox;
            this.size = Settings.OPTION_MENU_ARROW_SIZE;
            this.targetSize = Settings.OPTION_MENU_ARROW_SIZE;

            this.rotation = rotation;
        }

        @Override
        public void update(float dt) {
            Vector2 mp = GetMousePosition();

            // Check if mouse intersects hitbox, change target size accordingly
            if (CheckCollisionPointRec(mp, hitbox)) {
                targetSize = Settings.OPTION_MENU_ARROW_SIZE + 5;

                pressed = IsMouseButtonPressed(MOUSE_BUTTON_LEFT); // Arrow is pressed, menu will swap value
            } else targetSize = Settings.OPTION_MENU_ARROW_SIZE;

            // Lerp size towards target size
            size = Lerp(size, targetSize, 0.25f);
        }

        @Override
        public void render() {
            // Draw rotated triangle
            DrawPoly(
                new Vector2().x(hitbox.x() + hitbox.width() / 2).y(hitbox.y() + hitbox.height() / 2),
                3,
                size,
                rotation,
                WHITE
            );

            // Debug drawing
            if (Settings.DEBUG) {
                DrawRectangleLinesEx(hitbox, 2, RED);
            }
        }
    }
}
