class SliderKnob extends ObjectExtension {

    public SliderKnob(int xMod, int yMod, int zMod, float rotX, float rotY, float rotZ, color col) {
        this.xMod = xMod;
        this.yMod = yMod;
        this.zMod = zMod;
        this.rotX = rotX;
        this.col = col;
        this.rotY = rotY;
        this.rotZ = rotZ;
    }

    @Override
    void display() {
        translate(xMod*gridSize/2, yMod*gridSize/2, zMod*gridSize/2);
        rotateX(rotX);
        rotateY(rotY);
        rotateZ(rotZ);
        fill(col);
        box(gridSize/3);
        translate(-xMod*gridSize, -yMod*gridSize, -zMod*gridSize);
        box(gridSize/3);
        rotateX(-rotX);
        rotateY(-rotY);
        rotateZ(-rotZ);
        translate(xMod*gridSize/2, yMod*gridSize/2, zMod*gridSize/2);
    }
}