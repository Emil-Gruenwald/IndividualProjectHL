import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Main extends Application {
    private final Canvas canvas = new Canvas(400, 600);
    private final CameraView camera = new CameraView();
    private final Level level = new Level();
    private final Player player = new Player();
    private final IsoRenderer renderer = new IsoRenderer();
    private PathNode selectedNode;

    @Override
    public void start(Stage stage) {
        level.build();
        selectedNode = level.getInitialNode();
        player.setPosition(selectedNode);

        canvas.setFocusTraversable(true);
        canvas.setOnMousePressed(this::handleMousePressed);
        canvas.setOnMouseDragged(event -> level.getWheel().drag(event.getX()));
        canvas.setOnMouseReleased(event -> level.getWheel().release());
        canvas.setOnKeyPressed(event -> {
            if (camera.handleKey(event.getCode())) {
                draw();
                event.consume();
            }
        });

        stage.setTitle("ValleyClone - JavaFX");
        stage.setScene(new Scene(new javafx.scene.Group(canvas), 400, 600));
        stage.setResizable(false);
        stage.show();
        canvas.requestFocus();
        draw();

        new AnimationTimer() {
            private long previousFrame;

            @Override
            public void handle(long now) {
                double elapsed = previousFrame == 0 ? 1.0 / 60 : (now - previousFrame) / 1_000_000_000.0;
                previousFrame = now;
                if (player.getCurrentNode() != selectedNode) {
                    player.moveTo(selectedNode);
                }
                player.update(Math.min(elapsed, 0.05));
                level.getWheel().update();
                draw();
            }
        }.start();
    }

    private void handleMousePressed(MouseEvent event) {
        canvas.requestFocus();
        Point3D wheelPoint = camera.project(100, 0, 100, canvas.getWidth(), canvas.getHeight());
        if (level.getWheel().press(event.getX(), event.getY(), wheelPoint)) {
            event.consume();
            return;
        }

        PathNode closest = null;
        double closestDistance = Level.GRID_SIZE / 2;
        for (PathNode node : level.getPathNodes()) {
            Point3D screenPoint = camera.project(node.getX(), node.getY(), node.getZ(), canvas.getWidth(), canvas.getHeight());
            double distance = Math.hypot(event.getX() - screenPoint.getX(), event.getY() - screenPoint.getY());
            if (distance < closestDistance) {
                closest = node;
                closestDistance = distance;
            }
        }
        if (closest != null) {
            selectedNode = closest;
        }
    }

    private void draw() {
        renderer.draw(canvas.getGraphicsContext2D(), canvas.getWidth(), canvas.getHeight(), level, player, camera);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
