import javafx.geometry.Point3D;
import javafx.scene.input.KeyCode;

final class CameraView {
    private double phi = Math.PI / 4;
    private double theta = Math.acos(1 / Math.sqrt(3));
    private Point3D right = new Point3D(1, 0, 0);
    private Point3D down = new Point3D(0, 1, 0);
    private Point3D forward = new Point3D(0, 0, 1);

    boolean handleKey(KeyCode key) {
        switch (key) {
            case LEFT -> phi += Math.PI / 4;
            case RIGHT -> phi -= Math.PI / 4;
            case UP -> theta = clamp(theta + Math.PI / 16, 0.05, Math.PI - 0.05);
            case DOWN -> theta = clamp(theta - Math.PI / 16, 0.05, Math.PI - 0.05);
            default -> {
                return false;
            }
        }
        phi = (phi % (2 * Math.PI) + 2 * Math.PI) % (2 * Math.PI);
        updateBasis();
        return true;
    }

    Point3D project(double x, double y, double z, double width, double height) {
        updateBasis();
        Point3D position = new Point3D(x, y, z);
        return new Point3D(
            width / 2 + position.dotProduct(right),
            height / 2 + position.dotProduct(down),
            position.dotProduct(forward)
        );
    }

    Point3D getRight() {
        updateBasis();
        return right;
    }

    Point3D getDown() {
        updateBasis();
        return down;
    }

    Point3D getForward() {
        updateBasis();
        return forward;
    }

    private void updateBasis() {
        double cameraX = Math.cos(phi) * Math.sin(theta);
        double cameraY = -Math.cos(theta);
        double cameraZ = Math.sin(phi) * Math.sin(theta);
        Point3D cameraPosition = new Point3D(cameraX, cameraY, cameraZ);
        forward = cameraPosition.multiply(-1).normalize();
        right = forward.crossProduct(new Point3D(0, -1, 0)).normalize();
        if (right.magnitude() < 1e-9) {
            right = new Point3D(1, 0, 0);
        }
        down = forward.crossProduct(right).normalize();
    }

    private double clamp(double value, double minimum, double maximum) {
        return Math.max(minimum, Math.min(maximum, value));
    }
}
