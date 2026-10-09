import javafx.scene.paint.Color;
import java.util.ArrayList;

public abstract class InteractiveObject {
    int x, y, z;
    Color color;
    int interactionRange;
    ArrayList<StaticObject> objects;
    boolean isActive;

    public InteractiveObject(int x, int y, int z, Color color) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.color = color;
        interactionRange = 20;
        objects = new ArrayList<>();
        isActive = true;
    }

    public void update() {}

    public boolean isHovered() {
        return false;
    }
    
}