class Wheel extends InteractiveObject {
  char axis; // 'X', 'Y', or 'Z'
  float rotationLimit;
  float currentRotation;
  float dragStartMouse;
  float dragStartRotation;

  public Wheel(int x, int y, int z, color col, float rotX, float rotY, float rotZ, ArrayList<GridObject> gridObjects, char axis, float rotationLimit) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.col = col;
    this.rotX = rotX;
    this.rotY = rotY;
    this.rotZ = rotZ;
    this.gridObjects = gridObjects;
    this.axis = axis;
    this.rotationLimit = rotationLimit;

    interactionRadius = 50;
    currentRotation = 0;
    dragStartMouse = 0;
    dragStartRotation = 0;
  }

  @Override
  boolean mousePressed() {
    super.mousePressed();
    if (isActive) {
      dragStartMouse = mouseX;
      dragStartRotation = currentRotation;
    }
    return isActive;
  }

  @Override
  void update() {
    if (isActive) {
      float delta = mouseX - dragStartMouse;
      currentRotation = dragStartRotation + delta * 0.01; // scale mouse movement to rotation
      currentRotation = constrain(currentRotation, -rotationLimit, rotationLimit);
    } else {
      currentRotation -= (currentRotation - round(currentRotation/(PI/2)) * (PI/2)) * 0.2; // snap back to nearest 90 degrees
      if (abs(currentRotation - round(currentRotation/(PI/2)) * (PI/2)) < 0.01) {
        currentRotation = round(currentRotation/(PI/2)) * (PI/2);
      }
    }
  }

  @Override
  void display() {
    pushMatrix();
    translate(x, y, z);
    
    if (axis == 'X') {
      rotateX(currentRotation);
    } else if (axis == 'Y') {
      rotateY(currentRotation);
    } else if (axis == 'Z') {
      rotateZ(currentRotation);
    }
    
    translate(-x, -y, -z);
    super.display();
    popMatrix();
  }

  @Override
  boolean isHovered() {
    return dist(
      mouseX,
      mouseY,
      screenX(x, y, z),
      screenY(x, y, z)
    ) < interactionRadius;
  }
}