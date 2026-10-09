import javafx.scene.shape.TriangleMesh;
import javafx.scene.shape.MeshView;

public class Cone {
    private double radius;
    private double height;
    private int x, y, z;

    public Cone(int x, int y, int z, double radius, double height) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.radius = radius;
        this.height = height;
    }

    public MeshView getShape() {
        TriangleMesh shape = new TriangleMesh();

        float[] points = new float[3 * 2 + 3 * 20]; // 2 points for the tip and base center, 20 points for the base circle
        for (int i = 0; i < 20; i++) {
            double angle = 2 * Math.PI * i / 20;
            points[3 * (i + 2)] = (float) (radius * Math.cos(angle)) + x; // x
            points[3 * (i + 2) + 1] = (float) y; // y
            points[3 * (i + 2) + 2] = (float) (radius * Math.sin(angle)) + z; // z
        }
        points[0] = (float) x; // tip x
        points[1] = (float) (height + y); // tip y
        points[2] = (float) z; // tip z
        points[3] = (float) x; // base center x
        points[4] = (float) y; // base center y
        points[5] = (float) z; // base center z

        int[] faces = new int[6 * 20 + 6 * 20]; // 20 faces for the sides, 20 faces for the base
        for (int i = 0; i < 20; i++) {
            int faceOffset = 6 * i;
            faces[faceOffset] = 0; // tip index
            faces[faceOffset + 1] = 0; // tip texture index
            faces[faceOffset + 2] = i + 2; // base vertex
            faces[faceOffset + 3] = 0; // base vertex texture index
            faces[faceOffset + 4] = (i + 1) % 20 + 2; // next base vertex
            faces[faceOffset + 5] = 0; // next base vertex texture index
        }
        for (int i = 0; i < 20; i++) {
            int faceOffset = 6 * (20 + i);
            faces[faceOffset] = 1; // base center index
            faces[faceOffset + 1] = 0; // base center texture index
            faces[faceOffset + 2] = i + 2; // base vertex
            faces[faceOffset + 3] = 0; // base vertex texture index
            faces[faceOffset + 4] = (i + 1) % 20 + 2; // next base vertex
            faces[faceOffset + 5] = 0; // next base vertex texture index
        }

        shape.getPoints().setAll(points);
        shape.getTexCoords().setAll(0, 0);
        shape.getFaces().setAll(faces);

        return new MeshView(shape);
    }
}