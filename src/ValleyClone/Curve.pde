class Curve extends GridObject {

  float rotX, rotY, rotZ;

  Curve(int x, int y, int z, float rotX, float rotY, float rotZ, color col) {
    super(x, y, z, col);
    this.rotX = rotX;
    this.rotY = rotY;
    this.rotZ = rotZ;
  }

  @Override
    void display() {
    pushMatrix();
    translate(x, y, z);
    rotateX(rotX);
    rotateY(rotY);
    rotateZ(rotZ);
    fill(col);

    translate(0, 0, gridSize/4);
    box(gridSize, gridSize, gridSize/2);
    translate(0, 0, -gridSize/4);

    drawCurvedFace();

    translate(0, gridSize/4, -gridSize/4);
    box(gridSize, gridSize/2, gridSize/2);

    popMatrix();
    noFill();
  }

  void drawCurvedFace() {
    // Curved surface connecting:
    // - Start: y=-gridSize/2, z=0
    // - End: y=0, z=-gridSize/2

    float segments = 20;
    float xHalf = gridSize / 2;

    // Draw the main curved surface
    beginShape(QUAD_STRIP);
    for (int i = 0; i <= segments; i++) {
      float t = i / segments; // Parameter from 0 to 1

      // Interpolate y position from gridSize/2 to 0
      float yPos = lerp(gridSize/2, 0, t);

      // Interpolate z position from 0 to -gridSize/2
      float zPos = lerp(0, -gridSize/2, t);

      // Create a smooth curve bulge in y direction away from the corner
      float yBulge = sin(t * PI) * gridSize/8;

      vertex(xHalf, -(yPos + yBulge), zPos);
      vertex(-xHalf, -(yPos + yBulge), zPos);
      
    }
    endShape();

    beginShape();
    vertex(xHalf, 0, 0);
    for (int i = 0; i <= segments; i++) {
      float t = i / segments; // Parameter from 0 to 1

      // Interpolate y position from gridSize/2 to 0
      float yPos = lerp(gridSize/2, 0, t);

      // Interpolate z position from 0 to -gridSize/2
      float zPos = lerp(0, -gridSize/2, t);

      // Create a smooth curve bulge in y direction away from the corner
      float yBulge = sin(t * PI) * gridSize/8;

      vertex(xHalf, -(yPos + yBulge), zPos);
      
    }
    vertex (xHalf, 0, 0);
    endShape();

    beginShape();
    vertex(-xHalf, 0, 0);
    for (int i = 0; i <= segments; i++) {
      float t = i / segments; // Parameter from 0 to 1

      // Interpolate y position from gridSize/2 to 0
      float yPos = lerp(gridSize/2, 0, t);

      // Interpolate z position from 0 to -gridSize/2
      float zPos = lerp(0, -gridSize/2, t);

      // Create a smooth curve bulge in y direction away from the corner
      float yBulge = sin(t * PI) * gridSize/8;

      vertex(-xHalf, -(yPos + yBulge), zPos);
      
    }
    vertex (-xHalf, 0, 0);
    endShape();
  }

}