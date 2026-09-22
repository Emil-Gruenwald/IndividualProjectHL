abstract class InteractiveObject {
  int x, y, z;
  color col;
  int interactionRadius;
  float rotX, rotY, rotZ;
  ArrayList<GridObject> gridObjects;
  boolean isActive;

  void display() {
    for (GridObject obj : gridObjects) {
      obj.display();
    }
  }

  boolean isHovered() {
    //return dist(mouseX, mouseY, screenX(x * gridSize, y * gridSize, z * gridSize), screenY(x * gridSize, y * gridSize, z * gridSize)) < interactionRadius;
    return dist(
      mouseX,
      mouseY,
      screenX(x, y, z),
      screenY(x, y, z)
      ) < interactionRadius;
  }

  boolean mousePressed() {
    if (isHovered()) {
      isActive = true;
      return true;
    }
    return false;
  }

  void mouseReleased() {
    isActive = false;
  }

  void update() {
    // To be overridden by subclasses for specific behavior
  }
}
