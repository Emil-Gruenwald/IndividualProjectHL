import javafx.scene.image.Image;

public class Red extends Character {
    public Red() {
        super(100, 100, 50, 100, new Image("file:sprites/red/R1.png"));
        int canvasWidth = 1500;
        int canvasHeight = 1500;
        imageOffsetX = width/2-canvasWidth/2;
        imageOffsetY = height/2-canvasHeight/2;
        imageWidth = canvasWidth * 5;
    }


}
