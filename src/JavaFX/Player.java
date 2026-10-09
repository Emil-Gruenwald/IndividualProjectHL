import javafx.scene.shape.Sphere;
import javafx.scene.shape.Shape3D;

public class Player {
    private int x, y, z;
    private float rotationX, rotationY, rotationZ;
    Cone body, hat;
    Sphere head;

    public Player(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.rotationX = 0;
        this.rotationY = 0;
        this.rotationZ = 0;

        body = new Cone(x, y, z, 4, -8);
        head = new Sphere(3);
        head.setTranslateX(x);
        head.setTranslateY(y - 4);
        head.setTranslateZ(z);
        hat = new Cone(x, y - 4, z, 3, -5);
    }

    public Shape3D[] getShapes() {
        return new Shape3D[]{body.getShape(), head, hat.getShape()};
    }

    public void move(int dx, int dy, int dz) {
        x += dx;
        y += dy;
        z += dz;

        body = new Cone(x, y, z, 4, -8);
        head.setTranslateX(x);
        head.setTranslateY(y - 4);
        head.setTranslateZ(z);
        hat = new Cone(x, y - 4, z, 3, -5);
    }

    public void rotate(float dRotationX, float dRotationY, float dRotationZ) {
        rotationX += dRotationX;
        rotationY += dRotationY;
        rotationZ += dRotationZ;
    }
}