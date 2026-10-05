import java.util.Collections;
import java.util.List;

final class Player {
    private PathNode currentNode;
    private List<PathNode> path = Collections.emptyList();
    private int pathIndex;
    private double x;
    private double y;
    private double z;

    PathNode getCurrentNode() {
        return currentNode;
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

    void setPosition(PathNode position) {
        x = position.getX();
        y = position.getY();
        z = position.getZ();
        currentNode = position;
    }

    void moveTo(PathNode target) {
        path = currentNode == null ? Collections.emptyList() : currentNode.findPathTo(target);
        pathIndex = path.size() > 1 ? 1 : path.size();
        if (path.size() == 1) {
            currentNode = target;
        }
    }

    void update(double elapsed) {
        double remainingDistance = elapsed * 80;
        while (pathIndex < path.size()) {
            PathNode target = path.get(pathIndex);
            double dx = target.getX() - x;
            double dy = target.getY() - y;
            double dz = target.getZ() - z;
            double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
            if (distance > remainingDistance && distance != 0) {
                x += dx / distance * remainingDistance;
                y += dy / distance * remainingDistance;
                z += dz / distance * remainingDistance;
                break;
            }
            x = target.getX();
            y = target.getY();
            z = target.getZ();
            currentNode = target;
            pathIndex++;
            remainingDistance -= distance;
        }
        if (pathIndex >= path.size()) {
            path = Collections.emptyList();
            pathIndex = 0;
        }
    }
}
