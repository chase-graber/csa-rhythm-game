package ui.menu;

import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

import core.Settings;
import ui.AbstractUIComponent;

import java.util.function.Consumer;

public class UISwappableOptionsMenu<T> extends AbstractUIComponent {

    private final float width;

    private T[] values;
    private String[] displayNames;
    private int currentIndex;

    private final String label;
    private final Consumer<T> method;

    private Rectangle leftArrow;
    private float leftArrowSize;
    private float leftArrowSizeTarget;
    private Rectangle rightArrow;
    private float rightArrowSize;
    private float rightArrowSizeTarget;

    public UISwappableOptionsMenu(String label, float height, float width, T[] values, String[] displayNames, Consumer<T> method, int defaultIndex) {
        this.position = new Vector2().x((Settings.SCREEN_WIDTH - width) / 2).y(height);
        this.width = width;

        this.values = values;
        this.displayNames = displayNames;
        this.currentIndex = defaultIndex;

        this.label = label;
        this.method = method;

        this.leftArrow = new Rectangle()
                .x(position.x() - Settings.OPTION_MENU_ARROW_SIZE * 2 - Settings.OPTION_MENU_SIDE_PADDING)
                .y(position.y() - Settings.OPTION_MENU_ARROW_SIZE + Settings.OPTION_MENU_HEIGHT / 2)
                .width(Settings.OPTION_MENU_ARROW_SIZE * 2)
                .height(Settings.OPTION_MENU_ARROW_SIZE * 2);
        this.leftArrowSize = Settings.OPTION_MENU_ARROW_SIZE;
        this.leftArrowSizeTarget = Settings.OPTION_MENU_ARROW_SIZE;
        this.rightArrow = new Rectangle()
                .x(position.x() + width + Settings.OPTION_MENU_SIDE_PADDING)
                .y(position.y() - Settings.OPTION_MENU_ARROW_SIZE + Settings.OPTION_MENU_HEIGHT / 2)
                .width(Settings.OPTION_MENU_ARROW_SIZE * 2)
                .height(Settings.OPTION_MENU_ARROW_SIZE * 2);
        this.rightArrowSize = Settings.OPTION_MENU_ARROW_SIZE;
        this.rightArrowSizeTarget = Settings.OPTION_MENU_ARROW_SIZE;
    }

    @Override
    public void update(float dt) {
        Vector2 mp = GetMousePosition();

        if (IsMouseButtonPressed(MOUSE_BUTTON_LEFT)) {
            if (CheckCollisionPointRec(mp, leftArrow)) {
                currentIndex--;
                if (currentIndex < 0) currentIndex += values.length;
            } else if (CheckCollisionPointRec(mp, rightArrow)) {
                currentIndex++;
                if (currentIndex >= values.length) currentIndex -= values.length;
            }
            method.accept(values[currentIndex]);
        }

        if (CheckCollisionPointRec(mp, leftArrow)) {
            leftArrowSizeTarget = Settings.OPTION_MENU_ARROW_SIZE + 5;
        } else leftArrowSizeTarget = Settings.OPTION_MENU_ARROW_SIZE;
        if (CheckCollisionPointRec(mp, rightArrow)) {
            rightArrowSizeTarget = Settings.OPTION_MENU_ARROW_SIZE + 5;
        } else rightArrowSizeTarget = Settings.OPTION_MENU_ARROW_SIZE;

        leftArrowSize = Lerp(leftArrowSize, leftArrowSizeTarget, 0.25f);
        rightArrowSize = Lerp(rightArrowSize, rightArrowSizeTarget, 0.25f);
    }

    @Override
    public void render() {
        DrawPoly(
                new Vector2().x(leftArrow.x() + leftArrow.width() / 2).y(leftArrow.y() + leftArrow.height() / 2),
                3,
                leftArrowSize,
                180,
                BLACK
        );
        DrawPoly(
                new Vector2().x(rightArrow.x() + rightArrow.width() / 2).y(rightArrow.y() + rightArrow.height() / 2),
                3,
                rightArrowSize,
                0,
                BLACK
        );

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
            DrawRectangleLinesEx(leftArrow, 2, RED);
            DrawRectangleLinesEx(rightArrow, 2, RED);

            DrawRectangleLinesEx(new Rectangle().x(position.x()).y(position.y()).width(width).height(Settings.OPTION_MENU_HEIGHT), 2, RED);
        }
    }
}
