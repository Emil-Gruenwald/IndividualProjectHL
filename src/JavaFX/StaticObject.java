import javafx.scene.paint.Color;
import javafx.scene.shape.Shape3D;

public abstract class StaticObject {
    int x, y, z, size;
    Color color;

    public StaticObject(int x, int y, int z, int size, Color color) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.size = size;
        this.color = color;
    }

    public Shape3D[] getShapes() {
        return null;
    }
}