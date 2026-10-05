import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javafx.geometry.Point3D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

final class IsoRenderer {
    private static final Color BACKGROUND = Color.WHITE;
    private static final double[] FACE_SHADES = {
        0.9, 0.7, 0.95, 0.78, 0.82, 0.72
    };

    void draw(GraphicsContext graphics, double width, double height, Level level, Player player, CameraView camera) {
        graphics.setFill(BACKGROUND);
        graphics.fillRect(0, 0, width, height);
        List<Face> faces = new ArrayList<>();
        for (Block block : level.getBlocks()) {
            addFaces(faces, block, camera);
        }
        for (Block block : level.getWheel().getBlocks()) {
            addFaces(faces, block, camera);
        }
        faces.sort(Comparator.comparingDouble(Face::depth).reversed());
        for (Face face : faces) {
            double[] xPoints = new double[4];
            double[] yPoints = new double[4];
            for (int i = 0; i < face.points.length; i++) {
                Point3D point = camera.project(
                    face.points[i].getX(), face.points[i].getY(), face.points[i].getZ(), width, height
                );
                xPoints[i] = point.getX();
                yPoints[i] = point.getY();
            }
            graphics.setFill(face.color);
            graphics.fillPolygon(xPoints, yPoints, 4);
            graphics.setStroke(face.color.deriveColor(0, 0.7, 0.65, 1));
            graphics.setLineWidth(0.45);
            graphics.strokePolygon(xPoints, yPoints, 4);
        }
        drawPlayer(graphics, width, height, camera, player);
    }

    private void addFaces(List<Face> faces, Block block, CameraView camera) {
        double x = block.getX();
        double y = block.getY();
        double z = block.getZ();
        double hx = block.getWidth() / 2;
        double hy = block.getHeight() / 2;
        double hz = block.getDepth() / 2;
        Point3D[][] vertices = {
            {new Point3D(x + hx, y - hy, z - hz), new Point3D(x + hx, y - hy, z + hz),
                new Point3D(x + hx, y + hy, z + hz), new Point3D(x + hx, y + hy, z - hz)},
            {new Point3D(x - hx, y - hy, z + hz), new Point3D(x - hx, y - hy, z - hz),
                new Point3D(x - hx, y + hy, z - hz), new Point3D(x - hx, y + hy, z + hz)},
            {new Point3D(x - hx, y + hy, z - hz), new Point3D(x + hx, y + hy, z - hz),
                new Point3D(x + hx, y + hy, z + hz), new Point3D(x - hx, y + hy, z + hz)},
            {new Point3D(x - hx, y - hy, z + hz), new Point3D(x + hx, y - hy, z + hz),
                new Point3D(x + hx, y - hy, z - hz), new Point3D(x - hx, y - hy, z - hz)},
            {new Point3D(x + hx, y - hy, z + hz), new Point3D(x - hx, y - hy, z + hz),
                new Point3D(x - hx, y + hy, z + hz), new Point3D(x + hx, y + hy, z + hz)},
            {new Point3D(x - hx, y - hy, z - hz), new Point3D(x + hx, y - hy, z - hz),
                new Point3D(x + hx, y + hy, z - hz), new Point3D(x - hx, y + hy, z - hz)}
        };

        for (int i = 0; i < vertices.length; i++) {
            double depth = 0;
            for (Point3D vertex : vertices[i]) {
                depth += vertex.dotProduct(camera.getForward()) / 4;
            }
            Color color = block.getColor().deriveColor(0, 1, FACE_SHADES[i], 1);
            faces.add(new Face(vertices[i], depth, color));
        }
    }

    private void drawPlayer(GraphicsContext graphics, double width, double height, CameraView camera, Player player) {
        Point3D point = camera.project(player.getX(), player.getY(), player.getZ(), width, height);
        double x = point.getX();
        double y = point.getY();
        graphics.setFill(Color.rgb(235, 235, 235));
        graphics.fillPolygon(
            new double[]{x, x - 7, x + 7},
            new double[]{y - 11, y + 7, y + 7},
            3
        );
        graphics.setStroke(Color.rgb(90, 90, 90));
        graphics.setLineWidth(1);
        graphics.strokePolygon(
            new double[]{x, x - 7, x + 7},
            new double[]{y - 11, y + 7, y + 7},
            3
        );
        graphics.setFill(Color.rgb(245, 245, 245));
        graphics.fillOval(x - 4, y - 18, 8, 8);
        graphics.setStroke(Color.rgb(90, 90, 90));
        graphics.strokeOval(x - 4, y - 18, 8, 8);
    }

    private static final class Face {
        private final Point3D[] points;
        private final double depth;
        private final Color color;

        Face(Point3D[] points, double depth, Color color) {
            this.points = points;
            this.depth = depth;
            this.color = color;
        }

        double depth() {
            return depth;
        }
    }
}
