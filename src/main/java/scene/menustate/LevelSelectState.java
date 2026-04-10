package scene.menustate;

import static com.raylib.Colors.WHITE;
import static com.raylib.Raylib.DrawText;

public class LevelSelectState extends AbstractMenuState {

    private static LevelSelectState levelSelectState;

    private LevelSelectState() {
        // Init code
    }

    public static LevelSelectState getInstance() {
        if (levelSelectState == null)
            levelSelectState = new LevelSelectState();
        return levelSelectState;
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render() {
        DrawText("Level select state", 10, 10, 20, WHITE);
    }
}
