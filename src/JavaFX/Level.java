import javafx.scene.shape.*;
import javafx.scene.Group;
import javafx.scene.ParallelCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Level {
    private final ArrayList<Shape3D> objects = new ArrayList<>();
    private int[][][] level;
    private Scene scene;


    public Level(int[][][] level, int tileSize) {
        Group root = new Group();
        this.level = level;

        constructLevel(tileSize);

        for (Shape3D o : objects) {
            root.getChildren().add(o);
        }

        scene = new Scene(root, 400, 600);
        scene.setFill(Color.BLACK);

        ParallelCamera camera = new ParallelCamera();
        camera.setTranslateX(300);
        camera.setTranslateY(0);
        camera.setTranslateZ(300);
        scene.setCamera(camera);
    }

    public Scene getScene() {
        return scene;
    }

    private void constructLevel(int tileSize) {
        for (int i = 0; i < level.length; i++) {
            for (int j = 0; j < level[i].length; j++) {
                for (int k = 0; k < level[i][j].length; k++) {
                    switch (level[i][j][k]) {
                        case 1:
                            Box box = new Box(tileSize, tileSize, tileSize);
                            box.setTranslateX(i * tileSize);
                            box.setTranslateY(j * tileSize);
                            box.setTranslateZ(k * tileSize);
                            objects.add(box);
                    }
                }
            }
        }
    }
}
