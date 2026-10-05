import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;

final class Wheel {
    private static final double X = 100;
    private static final double Y = 0;
    private static final double Z = 100;

    private boolean dragging;
    private double dragStartX;
    private double dragStartRotation;
    private double rotation;

    List<Block> getBlocks() {
        List<Block> blocks = new ArrayList<>();
        Color baseColor = Color.rgb(100, 100, 100);
        Color wheelColor = Color.rgb(100, 100, 0);
        addRotatedBox(blocks, X, Y, Z, Level.GRID_SIZE, Level.GRID_SIZE, Level.GRID_SIZE, baseColor);
        addRotatedBox(blocks, X, Y + 20, Z, Level.GRID_SIZE, Level.GRID_SIZE, Level.GRID_SIZE, baseColor);
        addRotatedBox(blocks, X, Y + 40, Z, Level.GRID_SIZE, Level.GRID_SIZE, Level.GRID_SIZE, baseColor);
        double thickness = Level.GRID_SIZE / 3;
        double length = Level.GRID_SIZE * 10;
        double radius = Level.GRID_SIZE;
        addRotatedBox(blocks, X + length / 2, Y, Z, length, thickness, thickness, wheelColor);
        addRotatedBox(blocks, X + length, Y, Z, thickness, thickness, radius * 2, wheelColor);
        addRotatedBox(blocks, X + length, Y, Z, thickness, radius * 2, thickness, wheelColor);
        return blocks;
    }

    boolean press(double mouseX, double mouseY, Point3D screenPoint) {
        if (Math.hypot(mouseX - screenPoint.getX(), mouseY - screenPoint.getY()) >= 50) {
            return false;
        }
        dragging = true;
        dragStartX = mouseX;
        dragStartRotation = rotation;
        return true;
    }

    void drag(double mouseX) {
        if (dragging) {
            rotation = clamp(dragStartRotation + (mouseX - dragStartX) * 0.01, -200, 200);
        }
    }

    void release() {
        dragging = false;
    }

    void update() {
        if (!dragging) {
            double snap = Math.rint(rotation / (Math.PI / 2)) * Math.PI / 2;
            rotation += (snap - rotation) * 0.2;
            if (Math.abs(snap - rotation) < 0.01) {
                rotation = snap;
            }
        }
    }

    private void addRotatedBox(
        List<Block> blocks,
        double x,
        double y,
        double z,
        double width,
        double height,
        double depth,
        Color color
    ) {
        double relativeY = y - Y;
        double relativeZ = z - Z;
        double rotatedY = relativeY * Math.cos(rotation) - relativeZ * Math.sin(rotation);
        double rotatedZ = relativeY * Math.sin(rotation) + relativeZ * Math.cos(rotation);
        blocks.add(new Block(
            x,
            Y + rotatedY,
            Z + rotatedZ,
            width,
            height,
            depth,
            color
        ));
    }

    private double clamp(double value, double minimum, double maximum) {
        return Math.max(minimum, Math.min(maximum, value));
    }
}
