class Cube extends GridObject {
    Cube(int x, int y, int z, color col) {
        super(x, y, z, col);
    }

    Cube(int x, int y, int z, color col, ObjectExtension extension) {
        super(x, y, z, col);
        this.extension = extension;
    }

    @Override
    void display() {
        pushMatrix();
        translate(x, y, z);
        if (extension != null) {
            extension.display();
        }
        fill(col);
        box(gridSize);
        popMatrix();
        noFill();
    }
}