package ui.menu;

import static com.raylib.Colors.RED;
import static com.raylib.Colors.WHITE;
import static com.raylib.Raylib.*;

import core.Settings;
import ui.AbstractUIComponent;

import java.util.function.Consumer;

public class UISwappableOptionsMenu<T> extends AbstractUIComponent {

    private float width;

    private T[] values;
    private String[] displayNames;
    private int currentIndex;

    private final Consumer<T> method;

    private Rectangle leftArrow;
    private Rectangle rightArrow;

    public UISwappableOptionsMenu(float height, float width, T[] values, String[] displayNames, Consumer<T> method, int defaultIndex) {
        this.position = new Vector2().x((Settings.SCREEN_WIDTH - width) / 2).y(height);
        this.width = width;

        this.values = values;
        this.displayNames = displayNames;
        this.currentIndex = defaultIndex;

        this.method = method;

        this.leftArrow = new Rectangle()
                .x(position.x() - Settings.OPTION_MENU_SIDE_PADDING)
                .y(position.y() + Settings.OPTION_MENU_HEIGHT / 2)
                .width(Settings.OPTION_MENU_ARROW_SIZE * 2)
                .height(Settings.OPTION_MENU_ARROW_SIZE * 2);
        this.rightArrow = new Rectangle()
                .x(position.x() + width + Settings.OPTION_MENU_SIDE_PADDING)
                .y(position.y() + Settings.OPTION_MENU_HEIGHT / 2)
                .width(Settings.OPTION_MENU_ARROW_SIZE * 2)
                .height(Settings.OPTION_MENU_ARROW_SIZE * 2);
    }

    @Override
    public void update(float dt) {
        Vector2 mp = GetMousePosition();

        if (IsMouseButtonDown(MOUSE_BUTTON_LEFT)) {
            if (CheckCollisionPointRec(mp, leftArrow)) {
                currentIndex--;
            } else if (CheckCollisionPointRec(mp, rightArrow)) {
                currentIndex++;
            }
        }
    }

    @Override
    public void render() {
        DrawPoly(
                new Vector2().x((leftArrow.x() + leftArrow.width()) / 2).y((leftArrow.y() + leftArrow.height()) / 2),
                3,
                Settings.OPTION_MENU_ARROW_SIZE,
                180,
                WHITE
        );
        DrawPoly(
                new Vector2().x((rightArrow.x() + rightArrow.width()) / 2).y((rightArrow.y() + rightArrow.height()) / 2),
                3,
                Settings.OPTION_MENU_ARROW_SIZE,
                0,
                WHITE
        );

        if (Settings.DEBUG) {
            DrawRectangleLinesEx(leftArrow, 2, RED);
            DrawRectangleLinesEx(rightArrow, 2, RED);
        }
    }
}
