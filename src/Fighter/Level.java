import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;

public class Level {
    Platform[] platforms;
    Image backgroundImage;

    public Level(Platform[] platforms, Image backgroundImage) {
        this.platforms = platforms;
        this.backgroundImage = backgroundImage;
    }

    public void render(GraphicsContext gc) {
        // Render background
        if (backgroundImage != null) {
            gc.drawImage(backgroundImage, 0, 0);
        } else {
            gc.setFill(Color.LIGHTBLUE);
            gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        }

        // Render platforms
        for (Platform platform : platforms) {
            if (platform.sprite != null) {
                gc.drawImage(platform.sprite, platform.x, platform.y, platform.width, platform.height);
            } else {
                gc.setFill(Color.DARKGRAY);
                gc.fillRect(platform.x, platform.y, platform.width, platform.height);
            }
        }
    }
}