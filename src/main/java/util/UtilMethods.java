package util;

import static com.raylib.Raylib.*;

public class UtilMethods {

    public static Rectangle getTextureRect(Texture tex) {
        return new Rectangle()
            .x(0).y(0)
            .width(tex.width()).height(tex.height());
    }

    public static Rectangle getTextureRect(Texture tex, Vector2 offset) {
        return new Rectangle()
            .x(offset.x()).y(offset.y())
            .width(tex.width()).height(tex.height());
    }

    public static Rectangle getTextureRect(Texture tex, Vector2 offset, float scale) {
        return new Rectangle()
            .x(offset.x() * scale).y(offset.y() * scale)
            .width(tex.width() * scale).height(tex.height() * scale);
    }

    public static Vector2 getTextureMiddle(Texture tex) {
        return new Vector2().x(tex.width() / 2).y(tex.height() / 2);
    }
    
    private UtilMethods() { } // No making objects of this class
}
