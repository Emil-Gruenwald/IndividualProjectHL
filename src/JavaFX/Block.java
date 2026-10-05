import javafx.scene.paint.Color;

final class Block {
    private final double x;
    private final double y;
    private final double z;
    private final double width;
    private final double height;
    private final double depth;
    private final Color color;

    Block(double x, double y, double z, double width, double height, double depth, Color color) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.color = color;
    }

    double getX() {
        return x;
    }

    double getY() {
        return y;
    }

    double getZ() {
        return z;
    }

    double getWidth() {
        return width;
    }

    double getHeight() {
        return height;
    }

    double getDepth() {
        return depth;
    }

    Color getColor() {
        return color;
    }
}
