import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

public class Main extends Application {
    // Game State Trackers
    private final Set<KeyCode> keysPressed = new HashSet<>();
    private AnimationTimer gameLoop;
    public boolean running = true;
    Canvas canvas = new Canvas(1200, 700);

    Level[] stages = new Level[3];
    int currentStage = 0;

    Character player = new Red();

    ArrayList<Input> inputs = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {

        // Setup Canvas and Graphics Engine
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Layout Container
        StackPane root = new StackPane(canvas);
        root.setAlignment(Pos.TOP_LEFT);
        if (player.spriteRender != null) {
            root.getChildren().add(player.spriteRender);
        }
        Scene scene = new Scene(root);

        // Capture Keyboard Inputs
        scene.setOnKeyPressed(e -> {
            if (keysPressed.add(e.getCode())) {
                inputs.add(new Input(e.getCode(), 0));
            }
        });
        scene.setOnKeyReleased(e -> keysPressed.remove(e.getCode()));

        primaryStage.setTitle("Temp");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();

        stages[0] = new Level(new Platform[]{
                new Platform(0, 400, 800, 50, true, true, null),
                new Platform(200, 300, 100, 20, true, false, null),
                new Platform(400, 200, 100, 20, false, false, null)
        }, null);

        // The Game Loop (runs roughly 60 times per second)
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                if (running) {
                    updateGameLogic();
                    renderGraphics(gc);
                } else {
                    renderGameOver(gc);
                }
            }
        };
        gameLoop.start();
    }

    @Override
    public void stop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
        keysPressed.clear();
    }

    private void updateGameLogic() {
        player.move(keysPressed, inputs, stages[currentStage]);
        inputs.removeIf(Input::update);
    }

    private void renderGraphics(GraphicsContext gc) {
        // Clear the canvas
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        stages[currentStage].render(gc);

        player.render(gc);

        // Text in the top left corner for debugging
        gc.setFill(Color.BLACK);
        gc.setFont(new Font(20));
    }

    private void renderGameOver(GraphicsContext gc) {
    
    }

    public static void main(String[] eloquence) {
        launch(eloquence);
    }
}
