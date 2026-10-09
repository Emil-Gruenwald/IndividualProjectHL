import javafx.scene.paint.Color;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Box;

public class Planes extends StaticObject {
    boolean[] planes = new boolean[6]; // 0: +X, 1: -X, 2: +Y, 3: -Y, 4: +Z, 5: -Z
    Box[] planeShapes = new Box[6];

    public Planes(int x, int y, int z, int size, Color color, boolean[] planes) {
        super(x, y, z, size, color);
        this.planes = planes;

        for (int i = 0; i < 6; i++) {
            if (planes[i]) {
                Box plane = new Box(size, size, size);
                plane.setTranslateX(x);
                plane.setTranslateY(y);
                plane.setTranslateZ(z);

                switch (i) {
                    case 0: // +X
                        plane.setTranslateX(x + size / 2.0);
                        break;
                    case 1: // -X
                        plane.setTranslateX(x - size / 2.0);
                        break;
                    case 2: // +Y
                        plane.setTranslateY(y + size / 2.0);
                        break;
                    case 3: // -Y
                        plane.setTranslateY(y - size / 2.0);
                        break;
                    case 4: // +Z
                        plane.setTranslateZ(z + size / 2.0);
                        break;
                    case 5: // -Z
                        plane.setTranslateZ(z - size / 2.0);
                        break;
                }

                planeShapes[i] = plane;
            }
        }
    }

    @Override
    public Shape3D[] getShapes() {
        return planeShapes;
    }
}