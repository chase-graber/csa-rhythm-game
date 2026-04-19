package ui;

import core.TickableGameEntity;

import static com.raylib.Raylib.*;

// Any UI element
public abstract class AbstractUIComponent implements TickableGameEntity {

    protected Vector2 position;
    protected Texture texture;

    // Used by some classes to determine whether to delete component
    public boolean done = false;
}
