package scene.menustate;

import core.TickableGameEntity;
import ui.AbstractUIComponent;

import java.util.ArrayList;

public abstract class AbstractMenuState implements TickableGameEntity {

    protected ArrayList<AbstractUIComponent> uiComponents = new ArrayList<>();

    public void addUIComponent(AbstractUIComponent auic) {
        uiComponents.add(auic);
    }
}
