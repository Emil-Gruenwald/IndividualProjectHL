import javafx.scene.image.Image;

public class Platform {
    int x, y, width, height;
    boolean isSolid, hasLedge;
    Image sprite;

    public Platform(int x, int y, int width, int height, boolean isSolid, boolean hasLedge, Image sprite) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isSolid = isSolid;
        this.hasLedge = hasLedge;
        this.sprite = sprite;
    }
}
