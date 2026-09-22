class Button extends ObjectExtension {
    char buttonType; // 'P' for push, 'T' for toggle
    boolean isPressed;

    public Button(int xMod, int yMod, int zMod, float rotX, float rotY, float rotZ, color col, char buttonType) {
        this.xMod = xMod;
        this.yMod = yMod;
        this.zMod = zMod;
        this.rotX = rotX;
        this.col = col;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.buttonType = buttonType;
        isPressed = false;
    }

    @Override
    void display() {
        translate(xMod*gridSize/2, yMod*gridSize/2, zMod*gridSize/2);
        rotateX(rotX);
        rotateY(rotY);
        rotateZ(rotZ);
        fill(col);
        box(gridSize/3);
        rotateX(-rotX);
        rotateY(-rotY);
        rotateZ(-rotZ);
        translate(-xMod*gridSize/2, -yMod*gridSize/2, -zMod*gridSize/2);
    }
}