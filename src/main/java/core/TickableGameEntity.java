package core;

// Basically everything implements this whether directly or indirectly since almost everything needs an update and render
public interface TickableGameEntity {

    void update(float dt);
    void render();
}
