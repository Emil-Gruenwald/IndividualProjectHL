import javafx.scene.shape.*;
import javafx.scene.Group;
import javafx.scene.ParallelCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.geometry.Point3D;
import javafx.scene.transform.Affine;

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

        scene = new Scene(root, 400, 600, true);
        scene.setFill(Color.BLACK);

        ParallelCamera camera = new ParallelCamera();
        Point3D target = new Point3D(
                (level.length - 1) * tileSize / 2.0,
                (level[0].length - 1) * tileSize / 2.0,
                (level[0][0].length - 1) * tileSize / 2.0
        );
        Point3D forward = new Point3D(-1, 1, 1).normalize();
        Point3D worldUp = new Point3D(0, -1, 0);
        Point3D cameraRight = forward.crossProduct(worldUp).normalize();
        Point3D cameraDown = forward.crossProduct(cameraRight).normalize();
        double cameraDistance = Math.max(level.length, Math.max(level[0].length, level[0][0].length)) * tileSize * 2;

        PointLight light = new PointLight(Color.WHITE);
        light.setTranslateX(target.getX() + cameraDistance);
        light.setTranslateY(target.getY() - cameraDistance);
        light.setTranslateZ(target.getZ() - cameraDistance);
        root.getChildren().add(light);

        Affine cameraRotation = new Affine(
                cameraRight.getX(), cameraDown.getX(), forward.getX(), 0,
                cameraRight.getY(), cameraDown.getY(), forward.getY(), 0,
                cameraRight.getZ(), cameraDown.getZ(), forward.getZ(), 0
        );
        camera.getTransforms().add(cameraRotation);
        Runnable centerCamera = () -> {
            Point3D viewportCenter = cameraRotation.transform(
                    new Point3D(scene.getWidth() / 2, scene.getHeight() / 2, 0)
            );
            camera.setTranslateX(target.getX() - forward.getX() * cameraDistance - viewportCenter.getX());
            camera.setTranslateY(target.getY() - forward.getY() * cameraDistance - viewportCenter.getY());
            camera.setTranslateZ(target.getZ() - forward.getZ() * cameraDistance - viewportCenter.getZ());
        };
        centerCamera.run();
        scene.widthProperty().addListener((observable, oldWidth, newWidth) -> centerCamera.run());
        scene.heightProperty().addListener((observable, oldHeight, newHeight) -> centerCamera.run());
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
