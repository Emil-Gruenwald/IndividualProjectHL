abstract class GridObject {
    int x, y, z;
    int cameraLayer; //higher cameraLayer means it will be drawn on top of lower cameraLayer objects

    ObjectExtension extension;

    color col;

    GridObject(int x, int y, int z, color col) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.col = col;
        this.cameraLayer = 0;
        extension = null;
    }

    void display() {}
}