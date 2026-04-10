package ui;

import core.TickableGameEntity;

import static com.raylib.Raylib.*;

public abstract class AbstractUIComponent implements TickableGameEntity {

    protected Vector2 position;
    protected Texture texture;

    public boolean done = false;
}
