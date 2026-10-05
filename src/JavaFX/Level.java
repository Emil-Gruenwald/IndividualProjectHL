import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;

final class Level {
    static final double GRID_SIZE = 20;

    private final List<Block> blocks = new ArrayList<>();
    private final List<PathNode> pathNodes = new ArrayList<>();
    private final Wheel wheel = new Wheel();
    private PathNode initialNode;

    void build() {
        Color gray = Color.rgb(100, 100, 100);
        Block[] levelBlocks = {
            new Block(0, 0, 20, GRID_SIZE, GRID_SIZE, GRID_SIZE, gray),
            new Block(0, 0, 40, GRID_SIZE, GRID_SIZE, GRID_SIZE, gray),
            new Block(0, 0, 60, GRID_SIZE, GRID_SIZE, GRID_SIZE, gray),
            new Block(0, 0, 80, GRID_SIZE, GRID_SIZE, GRID_SIZE, gray),
            new Block(0, 0, 100, GRID_SIZE, GRID_SIZE, GRID_SIZE, gray),
            new Block(20, 0, 100, GRID_SIZE, GRID_SIZE, GRID_SIZE, gray),
            new Block(40, 0, 100, GRID_SIZE, GRID_SIZE, GRID_SIZE, gray),
            new Block(60, 0, 100, GRID_SIZE, GRID_SIZE, GRID_SIZE, gray),
            new Block(80, 0, 100, GRID_SIZE, GRID_SIZE, GRID_SIZE, gray),
            new Block(100, 0, 160, GRID_SIZE, GRID_SIZE, GRID_SIZE, gray),
            new Block(0, 100, 80, GRID_SIZE, GRID_SIZE, GRID_SIZE, gray),
            new Block(0, 100, 100, GRID_SIZE, GRID_SIZE, GRID_SIZE, gray),
            new Block(20, 100, 100, GRID_SIZE, GRID_SIZE, GRID_SIZE, gray),
            new Block(40, 100, 100, GRID_SIZE, GRID_SIZE, GRID_SIZE, gray),
            new Block(60, 100, 100, GRID_SIZE, GRID_SIZE, GRID_SIZE, gray),
            new Block(80, 100, 100, GRID_SIZE, GRID_SIZE, GRID_SIZE, gray)
        };
        for (int i = 0; i < levelBlocks.length; i++) {
            Block block = levelBlocks[i];
            if (i == 9) {
                blocks.add(new Block(110, 0, 160, 0.6, GRID_SIZE, GRID_SIZE, gray));
                blocks.add(new Block(100, -10, 160, GRID_SIZE, 0.6, GRID_SIZE, gray));
            } else {
                blocks.add(block);
            }
            pathNodes.add(new PathNode(block.getX(), block.getY() - GRID_SIZE, block.getZ()));
        }

        pathNodes.add(9, new PathNode(100, -3 * GRID_SIZE, 140));
        pathNodes.add(9, new PathNode(100, -2 * GRID_SIZE, 120));
        pathNodes.add(9, new PathNode(100, -GRID_SIZE, 100));
        for (int i = 0; i < pathNodes.size() - 1; i++) {
            pathNodes.get(i).connect(pathNodes.get(i + 1));
        }
        initialNode = pathNodes.get(pathNodes.size() - 1);
    }

    List<Block> getBlocks() {
        return blocks;
    }

    List<PathNode> getPathNodes() {
        return pathNodes;
    }

    PathNode getInitialNode() {
        return initialNode;
    }

    Wheel getWheel() {
        return wheel;
    }
}
