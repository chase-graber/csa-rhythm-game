package scene;

import static com.raylib.Colors.GREEN;
import static com.raylib.Raylib.*;

import util.AssetLoader;

public class CompletionScene extends AbstractScene{

    private final int[] numHits;
    private final float[] percents;

    public CompletionScene(int[] numHits, int totalNotes) {
        this.music = AssetLoader.getMusic("completion.mp3");
        PlayMusicStream(this.music);

        this.numHits = numHits;
        this.percents = new float[]
                { (float)numHits[0] / totalNotes,
                  (float)numHits[1] / totalNotes,
                  (float)numHits[2] / totalNotes,
                  (float)numHits[3] / totalNotes };
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render() {
        ClearBackground(GREEN);
    }
}
