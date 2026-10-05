import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.geometry.Point3D;

final class PathNode {
    private final double x;
    private final double y;
    private final double z;
    private final List<PathNode> neighbors = new ArrayList<>();

    PathNode(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
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

    Point3D getPosition() {
        return new Point3D(x, y, z);
    }

    void connect(PathNode other) {
        neighbors.add(other);
        other.neighbors.add(this);
    }

    List<PathNode> findPathTo(PathNode goal) {
        List<PathNode> queue = new ArrayList<>();
        List<PathNode> visited = new ArrayList<>();
        List<PathNode> parentNodes = new ArrayList<>();
        List<PathNode> parents = new ArrayList<>();
        queue.add(this);
        visited.add(this);
        parentNodes.add(this);
        parents.add(null);

        for (int next = 0; next < queue.size(); next++) {
            PathNode current = queue.get(next);
            if (current == goal) {
                List<PathNode> result = new ArrayList<>();
                for (PathNode node = goal; node != null; ) {
                    result.add(node);
                    int index = parentNodes.indexOf(node);
                    node = index < 0 ? null : parents.get(index);
                }
                Collections.reverse(result);
                return result;
            }
            for (PathNode neighbor : current.neighbors) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                    parentNodes.add(neighbor);
                    parents.add(current);
                }
            }
        }
        return Collections.emptyList();
    }
}
