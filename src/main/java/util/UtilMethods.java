package util;

import static com.raylib.Raylib.*;

// General utilities, mostly positioning math that's redundant to repeat
public class UtilMethods {

    // Get rectangle with width and height the same as the texture
    public static Rectangle getTextureRect(Texture tex) {
        return new Rectangle()
            .x(0).y(0)
            .width(tex.width()).height(tex.height());
    }

    // Get rectangle with width and height the same as the texture + an offset for positioning
    public static Rectangle getTextureRect(Texture tex, Vector2 offset) {
        return new Rectangle()
            .x(offset.x()).y(offset.y())
            .width(tex.width()).height(tex.height());
    }

    // Get center pixel of texture
    public static Vector2 getTextureMiddle(Texture tex) {
        return new Vector2().x(tex.width() / 2.0f).y(tex.height() / 2.0f);
    }

    // Get center position of rectangle
    public static Vector2 getRectangleMiddle(Rectangle rec) {
        return new Vector2().x(rec.x() + rec.width() / 2).y(rec.y() + rec.height() / 2);
    }

    // Scale a rectangle around it's center position
    public static Rectangle scaleRectangleAroundCenter(Rectangle rec, float scale) {
        float w = rec.width() * scale;
        float h = rec.height() * scale;
        float x = rec.x() + (rec.width() - w) / 2;
        float y = rec.y() + (rec.height() - h) / 2;

        return new Rectangle().x(x).y(y).width(w).height(h);
    }
    
    private UtilMethods() { } // No making objects of this class
}
