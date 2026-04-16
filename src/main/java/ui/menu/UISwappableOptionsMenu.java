package ui.menu;

import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

import core.Settings;
import core.TickableGameEntity;
import ui.AbstractUIComponent;

import java.util.function.Consumer;

public class UISwappableOptionsMenu<T> extends AbstractUIComponent {

    private final float width;

    private T[] values;
    private String[] displayNames;
    private int currentIndex;

    private final String label;
    private final Consumer<T> method;

    private UISwappableOptionsMenuArrow leftArrow;
    private UISwappableOptionsMenuArrow rightArrow;

    public UISwappableOptionsMenu(String label, float height, float width, T[] values, String[] displayNames, Consumer<T> method, int defaultIndex) {
        this.position = new Vector2().x((Settings.SCREEN_WIDTH - width) / 2).y(height);
        this.width = width;

        this.values = values;
        this.displayNames = displayNames;
        this.currentIndex = defaultIndex;

        this.label = label;
        this.method = method;

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

        if (leftArrow.pressed || rightArrow.pressed) {
            currentIndex += Boolean.compare(rightArrow.pressed, leftArrow.pressed);

            if (currentIndex < 0) currentIndex += values.length;
            else if (currentIndex >= values.length) currentIndex -= values.length;

            method.accept(values[currentIndex]);
        }
    }

    @Override
    public void render() {
        leftArrow.render();
        rightArrow.render();

        Vector2 textDimensions = MeasureTextEx(GetFontDefault(), displayNames[currentIndex], 30, 1);
        DrawTextEx(
                GetFontDefault(),
                displayNames[currentIndex],
                new Vector2()
                        .x(position.x() + (width - textDimensions.x()) / 2)
                        .y(position.y() + (Settings.OPTION_MENU_HEIGHT - textDimensions.y()) / 2),
                30,
                1,
                BLACK
                );

        float labelDimensions = MeasureText(label, 30);
        DrawTextEx(
                GetFontDefault(),
                label,
                new Vector2()
                        .x(position.x() + (width - labelDimensions) / 2)
                        .y(position.y() - Settings.OPTION_MENU_SIDE_PADDING - 15),
                30,
                1,
                BLACK
        );

        if (Settings.DEBUG) {
            DrawRectangleLinesEx(new Rectangle().x(position.x()).y(position.y()).width(width).height(Settings.OPTION_MENU_HEIGHT), 2, RED);
        }
    }

    private static class UISwappableOptionsMenuArrow implements TickableGameEntity {

        private Rectangle hitbox;
        private float size;
        private float targetSize;

        private float rotation;

        public boolean pressed = false;

        public UISwappableOptionsMenuArrow(Rectangle hitbox, float rotation) {
            this.hitbox = hitbox;
            this.size = Settings.OPTION_MENU_ARROW_SIZE;
            this.targetSize = Settings.OPTION_MENU_ARROW_SIZE;

            this.rotation = rotation;
        }

        @Override
        public void update(float dt) {
            Vector2 mp = GetMousePosition();

            if (CheckCollisionPointRec(mp, hitbox)) {
                targetSize = Settings.OPTION_MENU_ARROW_SIZE + 5;

                pressed = IsMouseButtonPressed(MOUSE_BUTTON_LEFT);
            } else targetSize = Settings.OPTION_MENU_ARROW_SIZE;

            size = Lerp(size, targetSize, 0.25f);
        }

        @Override
        public void render() {
            DrawPoly(
                new Vector2().x(hitbox.x() + hitbox.width() / 2).y(hitbox.y() + hitbox.height() / 2),
                3,
                size,
                rotation,
                BLACK
            );
            
            if (Settings.DEBUG) {
                DrawRectangleLinesEx(hitbox, 2, RED);
            }
        }
    }
}
