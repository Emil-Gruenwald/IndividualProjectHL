class WheelPart extends ObjectExtension {
    int length, radius;
    char axis; // 'X', 'Y', or 'Z'

    public WheelPart(int xMod, int yMod, int zMod, float rotX, float rotY, float rotZ, int length, int radius, char axis, color col) {
        this.xMod = xMod;
        this.yMod = yMod;
        this.zMod = zMod;
        this.rotX = rotX;
        this.col = col;
        this.axis = axis;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.length = length;
        this.radius = radius;
    }

    @Override
    void display() {
        translate(xMod*gridSize/2, yMod*gridSize/2, zMod*gridSize/2);
        rotateX(rotX);
        rotateY(rotY);
        rotateZ(rotZ);
        fill(col);
        if (axis == 'X') {
            translate(length/2, 0, 0);
            box(length, gridSize/3, gridSize/3);
            translate(length/2, 0, 0);
            box(gridSize/3, gridSize/3, radius*2);
            box(gridSize/3, radius*2, gridSize/3);
            translate(-length, 0, 0);
        } else if (axis == 'Y') {
            translate(0, length/2, 0);
            box(gridSize/3, length, gridSize/3);
            translate(0, length/2, 0);
            box(gridSize/3, gridSize/3, radius*2);
            box(radius*2, gridSize/3, gridSize/3);
            translate(0, -length, 0);
        } else if (axis == 'Z') {
            translate(0, 0, length/2);
            box(gridSize/3, gridSize/3, length);
            translate(0, 0, length/2);
            box(gridSize/3, radius*2, gridSize/3);
            box(radius*2, gridSize/3, gridSize/3);
            translate(0, 0, -length);
        }
        rotate(-rotX);
        rotateY(-rotY);
        rotateZ(-rotZ);
        translate(-xMod*gridSize/2, -yMod*gridSize/2, -zMod*gridSize/2);
        
    }
}