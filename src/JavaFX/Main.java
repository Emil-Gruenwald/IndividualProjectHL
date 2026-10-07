import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.ParallelCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.animation.AnimationTimer;

public class Main extends Application {
    private AnimationTimer mainLoop;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, 400, 600);
        scene.setFill(Color.BLACK);

        ParallelCamera camera = new ParallelCamera();
        scene.setCamera(camera);

        Box box = new Box();
        box.setWidth(100);
        box.setHeight(100);
        box.setDepth(100);
        box.setTranslateX(150);
        box.setTranslateY(150);
       
        Rotate rx = new Rotate(0, Rotate.X_AXIS);
        Rotate ry = new Rotate(0, Rotate.Y_AXIS);
        Rotate rz = new Rotate(0, Rotate.Z_AXIS);

        box.getTransforms().addAll(rx, ry, rz);


        rx.setAngle(30);
        ry.setAngle(45);
        rz.setAngle(15);

        root.getChildren().add(box);

        primaryStage.setTitle("Test Application");
        primaryStage.setScene(scene);
        primaryStage.show();

        mainLoop = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                rx.setAngle(rx.getAngle() + 5);
                ry.setAngle(ry.getAngle() + 5);
                rz.setAngle(rz.getAngle() + 5);
            }
        };
        mainLoop.start();
    }

    public static void main(String args[]) {
        launch(args);
    }
}