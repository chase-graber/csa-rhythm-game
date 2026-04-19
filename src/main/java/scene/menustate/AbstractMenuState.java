package scene.menustate;

import core.TickableGameEntity;
import ui.AbstractUIComponent;

import java.util.ArrayList;

// Basically just a collection of UI components that can be swapped for another collection of UI components
public abstract class AbstractMenuState implements TickableGameEntity {

    protected ArrayList<AbstractUIComponent> uiComponents;

    // Objects will be null in state machine since static variables are loaded first, init is needed to ensure that no rage is brought upon by the compiler
    public abstract void init();
}
