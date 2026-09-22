int mapX = 10, mapY = 10, mapZ = 10;
//GridObject[][][] map = new GridObject[mapX][mapY][mapZ];
ArrayList<GridObject> map = new ArrayList<GridObject>();
ArrayList<InteractiveObject> interactiveObjects = new ArrayList<InteractiveObject>();
ArrayList<Node> pathfindingNodes = new ArrayList<Node>();
int gridSize = 20;
int level;

float cameraDistance = 1000;
float cameraPhi = PI / 4;   // 45 degrees around the horizontal circle
float cameraTheta = acos(1.0 / sqrt(3)); // equal-axis isometric elevation (~35.26°)
float camX, camY, camZ;

Node selectedNode = null;

Player player = new Player(0, 0, 0);

void setup() {
  size(400, 600, P3D);
  frameRate(60);
  ortho();
  noFill();
  noStroke();
  background(255);

  // for (int i = 0; i < 10; i++) {
  //   for (int j = 0; j < 10; j++) {
  //     for (int k = 0; k < 10; k++) {
  //       map[i][j][k] = null;
  //       // boolean atBoundaryX = (i == 0 || i == 9);
  //       // boolean atBoundaryY = (j == 0 || j == 9);
  //       // boolean atBoundaryZ = (k == 0 || k == 9);
  //       // if ((atBoundaryX && atBoundaryY) || (atBoundaryX && atBoundaryZ) || (atBoundaryY && atBoundaryZ)) {
  //       //     map[i][j][k] = new Cube(i, j, k, color(200));
  //       // }
  //     }
  //   }
  // }

  // map[0][0][0] = new Cube(0, 0, 0, color(255, 0, 0), new Button(0, -1, 0, 0, 0, 0, color(255), 'P'));
  // map[9][9][8] = new Stair(9, 9, 8, 0, PI, 0, color(0, 255, 0));
  // map[9][9][9] = new Curve(9, 9, 9, 0, PI, 0, color(0, 0, 255));
  // map[9][9][7] = new Cube(9, 9, 7, color(255, 255, 0));

  // // Add a wheel in the middle of the map for testing
  // ArrayList<GridObject> wheelBase = new ArrayList<GridObject>();
  // for (int i = 0; i < 2; i++) {
  //   wheelBase.add(new Cube(5, 5, 5 + i, color(150)));
  // }
  // wheelBase.get(1).extension = new WheelPart(0, 0, 0, 0, 0, 0, gridSize*2, gridSize/2, 'X', color(255, 0, 255));
  // Wheel wheelX = new Wheel(5, 5, 6, color(255, 0, 255), 0, 0, 0, wheelBase, 'X', 200);
  // interactiveObjects.add(wheelX);

  // // Add a slider at the top of the map for testing
  // ArrayList<GridObject> sliderBase = new ArrayList<GridObject>();
  // sliderBase.add(new Cube(5, 0, 5, color(150), new SliderKnob(0, 0, 1, 0, 0, 0, color(0, 255, 255))));
  
  // Slider sliderY = new Slider(5, 0, 5, color(0, 255, 255), 0, 0, 0, sliderBase, 'X', 200);
  // interactiveObjects.add(sliderY);

  map.add(new Cube(0, 0, 20, color(100)));
  map.add(new Cube(0, 0, 40, color(100)));
  map.add(new Cube(0, 0, 60, color(100)));
  map.add(new Cube(0, 0, 80, color(100)));
  map.add(new Cube(0, 0, 100, color(100)));
  map.add(new Cube(20, 0, 100, color(100)));
  map.add(new Cube(40, 0, 100, color(100)));
  map.add(new Cube(60, 0, 100, color(100)));
  map.add(new Cube(80, 0, 100, color(100)));
  // map.add(new Cube(100, 60, 100, color(100)));
  // map.add(new Cube(100, 80, 100, color(100)));
  // map.add(new Cube(100, 100, 100, color(100)));
  map.add(new Planes(100, 0, 160, color(100), new boolean[]{true, false, false, true, false, false}));
  map.add(new Cube(0, 100, 80, color(100)));
  map.add(new Cube(0, 100, 100, color(100)));
  map.add(new Cube(20, 100, 100, color(100)));
  map.add(new Cube(40, 100, 100, color(100)));
  map.add(new Cube(60, 100, 100, color(100)));
  map.add(new Cube(80, 100, 100, color(100)));

  for (int i = 0; i < map.size(); i ++) {
    pathfindingNodes.add(new Node(0, -gridSize, 0, map.get(i)));
  }

  ArrayList<GridObject> wheelBase = new ArrayList<GridObject>();
  wheelBase.add(new Cube(100, 0, 100, color(100), new WheelPart(0, 0, 0, 0, 0, 0, gridSize*2, gridSize, 'X', color(100, 100, 0))));
  wheelBase.add(new Cube(100, 20, 100, color(100)));
  wheelBase.add(new Cube(100, 40, 100, color(100)));
  
  Wheel wheelX = new Wheel(100, 0, 100, color(255, 0, 255), 0, 0, 0, wheelBase, 'X', 200);
  interactiveObjects.add(wheelX);

  pathfindingNodes.add(9, new Node(0, -3*gridSize, 2*gridSize, interactiveObjects.get(0).gridObjects.get(2)));
  pathfindingNodes.add(9, new Node(0, -2*gridSize, gridSize, interactiveObjects.get(0).gridObjects.get(1)));
  pathfindingNodes.add(9, new Node(0, -gridSize, 0, interactiveObjects.get(0).gridObjects.get(0)));

  for (int i = 0; i < pathfindingNodes.size()-1; i++) {
    pathfindingNodes.get(i).addNeighbor(pathfindingNodes.get(i+1));
  }
  

  selectedNode = pathfindingNodes.get(pathfindingNodes.size()-1);
  player.x = (int)selectedNode.getPosition()[0];
  player.y = (int)selectedNode.getPosition()[1];
  player.z = (int)selectedNode.getPosition()[2];
  player.currentNode = selectedNode;
}

void draw() {
  background(255);
  
  camX = cameraDistance * cos(cameraPhi) * sin(cameraTheta);
  camZ = cameraDistance * sin(cameraPhi) * sin(cameraTheta);
  // In Processing 3D, positive Y points downward, so negate Y to place the camera above the level.
  camY = -cameraDistance * cos(cameraTheta);

  // Only start a new path when not already moving
  if (player.targetNode == null && player.currentNode != selectedNode) {
    player.moveToNode(selectedNode);
  }

  player.update();

  camera(camX, camY, camZ, 0, 0, 0, 0, 1, 0);
  lights();
  //pointLight(255, 255, 255, -100, 0, 100);
  //pointLight(255, 255, 255, 100, 0, -100);
  pointLight(100, 100, 100, 1000, 0, 0);
  for (GridObject obj : map) {
    obj.display();
  }

  for (InteractiveObject obj : interactiveObjects) {
    obj.update();
    obj.display();
  }

  for (Node node : pathfindingNodes) {
    float[] pos = node.getPosition();
    // Draw a small sphere at the node's position
    pushMatrix();
    translate(pos[0], pos[1], pos[2]);
    fill(255, 0, 0);
    if (node == selectedNode) {
      fill(0, 255, 0);
    }
    noStroke();
    //sphere(5);
    popMatrix();
  }

  player.display();
}

void keyPressed() {
  if (keyCode == LEFT) {
    // camX = camX * cos(PI / 16) - camZ * sin(PI / 16);
    // camZ = camX * sin(PI / 16) + camZ * cos(PI / 16);
    cameraPhi += PI / 4;
  } else if (keyCode == RIGHT) {
    // camX = camX * cos(-PI / 16) - camZ * sin(-PI / 16);
    // camZ = camX * sin(-PI / 16) + camZ * cos(-PI / 16);
    cameraPhi -= PI / 4;
  } else if (keyCode == UP) {
    //camY -= 100;
    cameraTheta += PI / 16;
  } else if (keyCode == DOWN) {
    //camY += 100;
    cameraTheta -= PI / 16;
  }

  if (cameraPhi < 0) cameraPhi += TWO_PI;
  if (cameraPhi >= TWO_PI) cameraPhi -= TWO_PI;
  if (cameraTheta < 0) cameraTheta += TWO_PI;
  if (cameraTheta >= TWO_PI) cameraTheta -= TWO_PI;
}

void mousePressed() {
  for (Node node : pathfindingNodes) {
    if (node.mousePressed()) {
      selectedNode = node;
      break;
    }
  }
  for (InteractiveObject obj : interactiveObjects) {
    if (obj.mousePressed()) {
      break;
    }
  }
}

void mouseReleased() {
  for (InteractiveObject obj : interactiveObjects) {
    obj.mouseReleased();
  }
}
