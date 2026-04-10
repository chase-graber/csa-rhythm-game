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

    public static Vector2 getTextureMiddle(Texture tex) {
        return new Vector2().x(tex.width() / 2.0f).y(tex.height() / 2.0f);
    }

    public static Rectangle scaleRectangleAroundCenter(Rectangle rec, float scale) {
        float newWidth = rec.width() * scale;
        float newHeight = rec.height() * scale;
        float newX = rec.x() - newWidth / 2;
        float newY = rec.y() - newHeight / 2;

        return new Rectangle().x(newX).y(newY).width(newWidth).height(newHeight);
    }
    
    private UtilMethods() { } // No making objects of this class
}
