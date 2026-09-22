class Planes extends GridObject {
    boolean[] visibleSides = new boolean[6]; // +X, -X, +Y, -Y, +Z, -Z

    public Planes(int x, int y, int z, color col, boolean[] visibleSides) {
        super(x, y, z, col);
        this.visibleSides = visibleSides;
    }

    @Override
    void display() {
        pushMatrix();
        translate(x, y, z);
        fill(col);
        if (visibleSides[0]) {
            translate(gridSize/2, 0, 0);
            box(0, gridSize, gridSize);
            translate(-gridSize/2, 0, 0);
        }
        if (visibleSides[1]) {
            translate(-gridSize/2, 0, 0);
            box(0, gridSize, gridSize);
            translate(gridSize/2, 0, 0);
        }
        if (visibleSides[2]) {
            translate(0, gridSize/2, 0);
            box(gridSize, 0, gridSize);
            translate(0, -gridSize/2, 0);
        }
        if (visibleSides[3]) {
            translate(0, -gridSize/2, 0);
            box(gridSize, 0, gridSize);
            translate(0, gridSize/2, 0);
        }
        if (visibleSides[4]) {
            translate(0, 0, gridSize/2);
            box(gridSize, gridSize, 0);
            translate(0, 0, -gridSize/2);
        }
        if (visibleSides[5]) {
            translate(0, 0, -gridSize/2);
            box(gridSize, gridSize, 0);
            translate(0, 0, gridSize/2);
        }
        popMatrix();
        noFill();
    }

}