import javafx.scene.input.KeyCode;

public class Input {
    public KeyCode keyCode;
    int frameCount;

    public Input(KeyCode keyCode, int frameCount) {
        this.keyCode = keyCode;
        this.frameCount = frameCount;
    }

    public boolean update() {
        frameCount++;
        return frameCount <= 10;
    }
}
