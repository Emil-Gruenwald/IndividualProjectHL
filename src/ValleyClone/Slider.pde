class Slider extends InteractiveObject {
  char sliderType; // 'X', 'Y', or 'Z'
  int sliderLength;
  float currentDisplacement;
  float dragStartMouse;
  float dragStartDisplacement;

  public Slider(int x, int y, int z, color col, float rotX, float rotY, float rotZ, ArrayList<GridObject> gridObjects, char sliderType, int sliderLength) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.col = col;
    this.rotX = rotX;
    this.rotY = rotY;
    this.rotZ = rotZ;
    this.gridObjects = gridObjects;
    this.sliderType = sliderType;
    this.sliderLength = sliderLength;

    interactionRadius = 50;
    currentDisplacement = 0;
    dragStartMouse = 0;
    dragStartDisplacement = 0;
  }

  @Override
  boolean mousePressed() {
    super.mousePressed();
    if (isActive) {
      if (sliderType == 'X') {
        dragStartMouse = mouseX;
      } else {
        dragStartMouse = mouseY;
      }
      dragStartDisplacement = currentDisplacement;
    }
    return isActive;
  }

  @Override
  void update() {
    if (isActive) {
      float delta = 0;
      if (sliderType == 'X') {
        delta = mouseX - dragStartMouse;
      } else {
        delta = mouseY - dragStartMouse;
      }
      currentDisplacement = dragStartDisplacement + delta;
      currentDisplacement = constrain(currentDisplacement, -sliderLength, sliderLength);
    } else {
      currentDisplacement -= (currentDisplacement - round(currentDisplacement/gridSize) * gridSize) * 0.2; 
      if (abs(currentDisplacement - round(currentDisplacement/gridSize) * gridSize) < 0.1) {
        currentDisplacement = round(currentDisplacement/gridSize) * gridSize;
      }
    }
  }

  @Override
  void display() {
    pushMatrix();
    if (sliderType == 'X') {
      translate(currentDisplacement, 0, 0);
    } else if (sliderType == 'Y') {
      translate(0, currentDisplacement, 0);
    } else if (sliderType == 'Z') {
      translate(0, 0, currentDisplacement);
    }
    super.display();
    popMatrix();
  }

  @Override
  boolean isHovered() {
    float worldX = (x - (mapX - 1) / 2.0) * gridSize;
    float worldY = (y - (mapY - 1) / 2.0) * gridSize;
    float worldZ = (z - (mapZ - 1) / 2.0) * gridSize;
    if (sliderType == 'X') {
      worldX += currentDisplacement;
    } else if (sliderType == 'Y') {
      worldY += currentDisplacement;
    } else if (sliderType == 'Z') {
      worldZ += currentDisplacement;
    }
    return dist(
      mouseX,
      mouseY,
      screenX(worldX, worldY, worldZ),
      screenY(worldX, worldY, worldZ)
    ) < interactionRadius;
  }
}
