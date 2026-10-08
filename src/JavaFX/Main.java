import javafx.application.Application;
import javafx.stage.Stage;
//import javafx.animation.AnimationTimer;

public class Main extends Application {
    //private AnimationTimer mainLoop;
    private Level[] levels;

    @Override
    public void start(Stage primaryStage) throws Exception {
        int[][][] tempLevel = new int[10][10][10];
        for (int i = 0; i < tempLevel.length; i++) {
            for (int j = 0; j < tempLevel[i].length; j++) {
                for (int k = 0; k < tempLevel[i][j].length; k++) {
                    if (i == 0) {
                        tempLevel[i][j][k] = 1;
                    }
                }
            }
        }

        levels = new Level[1];
        levels[0] = new Level(tempLevel, 10);

        primaryStage.setTitle("Test Application");
        primaryStage.setScene(levels[0].getScene());
        primaryStage.show();
    }

    public static void main(String args[]) {
        launch(args);
    }
}