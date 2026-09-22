class Stair extends GridObject {

    float rotX, rotY, rotZ;

    Stair(int x, int y, int z, float rotX, float rotY, float rotZ, color col) {
        super(x, y, z, col);
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
    }

    @Override
    void display() {
        int segments = 5;
        float stepSize = (float) gridSize / (float) segments;

        pushMatrix();
        translate(x, y, z);
        rotateX(rotX);
        rotateY(rotY);
        rotateZ(rotZ);
        fill(col);
        translate(0, gridSize/2, -gridSize/2);
        translate(0, -stepSize/2, stepSize/2);
        for (int i = 0; i < segments; i++) {
            box(gridSize, stepSize * (i + 1), stepSize);
            translate(0, -stepSize/2, stepSize);
        }

        popMatrix();
        noFill();
    }
}