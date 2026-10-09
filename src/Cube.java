import javafx.scene.paint.Color;
import javafx.scene.shape.Box;

public class Cube extends StaticObject {
    Box box;

    public Cube(int x, int y, int z, int size, Color color) {
        super(x, y, z, size, color);
        box = new Box(size, size, size);
        box.setTranslateX(x * size);
        box.setTranslateY(y * size);
        box.setTranslateZ(z * size);
        box.setMaterial(new javafx.scene.paint.PhongMaterial(color));
    }

    @Override
    public Box[] getShapes() {
        return new Box[]{box};
    }
}