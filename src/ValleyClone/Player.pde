class Player {
    float x, y, z;
    float rotX, rotY, rotZ;
    Node currentNode;
    Node targetNode;
    ArrayList<Node> path = new ArrayList<Node>();
    int currentPathIndex = 0;
    float startX, startY, startZ;
    // movementProgress removed; we use fixed per-frame step instead
    float dirX = 0, dirY = 0, dirZ = 0;
    float segmentStep = 0; // distance to move each frame along current segment

    Player(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.startX = x;
        this.startY = y;
        this.startZ = z;
        rotX = 0;
        rotY = 0;
        rotZ = 0;
    }
    
    ArrayList<Node> findPath(Node start, Node goal) {
        ArrayList<Node> resultPath = new ArrayList<Node>();
        if (start == goal) {
            resultPath.add(goal);
            return resultPath;
        }
        
        ArrayList<Node> queue = new ArrayList<Node>();
        ArrayList<Node> visited = new ArrayList<Node>();
        ArrayList<Node> parent = new ArrayList<Node>();
        ArrayList<Node> parentTracker = new ArrayList<Node>();
        
        queue.add(start);
        visited.add(start);
        parent.add(null);
        parentTracker.add(start);
        
        while (queue.size() > 0) {
            Node current = queue.get(0);
            queue.remove(0);
            
            if (current == goal) {
                // Reconstruct path
                Node node = goal;
                while (node != null) {
                    resultPath.add(0, node);
                    int idx = parentTracker.indexOf(node);
                    node = parent.get(idx);
                }
                return resultPath;
            }
            
            for (Node neighbor : current.neighbors) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                    parent.add(current);
                    parentTracker.add(neighbor);
                }
            }
        }
        
        // No path found, return empty
        return resultPath;
    }
    
    void moveToNode(Node targetNode) {
        this.targetNode = targetNode;
        this.path = findPath(currentNode, targetNode);

        // If path includes the starting node at index 0, skip it so we move
        // to a neighbor first (otherwise the first target is the current node)
        if (path.size() > 0 && currentNode != null && path.get(0) == currentNode) {
            this.currentPathIndex = 1;
        } else {
            this.currentPathIndex = 0;
        }

        if (currentPathIndex < path.size()) {
            moveToNextNodeInPath();
        }
    }
    
    void moveToNextNodeInPath() {
        if (currentPathIndex < path.size()) {
            Node nextNode = path.get(currentPathIndex);
            this.startX = x;
            this.startY = y;
            this.startZ = z;
            float[] targetPos = nextNode.getPosition();
            float segLen = dist(startX, startY, startZ, targetPos[0], targetPos[1], targetPos[2]);
            if (segLen == 0) {
                dirX = dirY = dirZ = 0;
                segmentStep = 0;
            } else {
                dirX = (targetPos[0] - startX) / segLen;
                dirY = (targetPos[1] - startY) / segLen;
                dirZ = (targetPos[2] - startZ) / segLen;
                segmentStep = segLen / 15.0; // move over ~15 frames (~4x faster than before)
            }
        }
    }
    
    void update() {
        if (currentPathIndex < path.size()) {
            float[] targetPos = path.get(currentPathIndex).getPosition();
            float remaining = dist(x, y, z, targetPos[0], targetPos[1], targetPos[2]);

            if (segmentStep == 0 || remaining <= segmentStep) {
                // Snap to target and advance
                x = targetPos[0];
                y = targetPos[1];
                z = targetPos[2];
                currentNode = path.get(currentPathIndex);
                currentPathIndex++;

                if (currentPathIndex < path.size()) {
                    moveToNextNodeInPath();
                } else {
                    targetNode = null;
                    path.clear();
                }
            } else {
                // Move fixed step toward target
                x += dirX * segmentStep;
                y += dirY * segmentStep;
                z += dirZ * segmentStep;
            }
        } else {
            // No path
        }
    }

    void display() {
        pushMatrix();
        translate(x, y, z);
        rotateX(rotX);
        rotateY(rotY);
        rotateZ(rotZ);
        fill(255);
        // draw cone as body
        translate(0, -gridSize/4, 0);
        int segments = 20;
        float radius = gridSize/4;
        float height = gridSize/2;
        beginShape(TRIANGLE_FAN);
        vertex(0, 0, 0); // Cone tip
        for (int i = 0; i <= segments; i++) {
            float angle = TWO_PI * i / segments;
            vertex(cos(angle) * radius, height, sin(angle) * radius);
        }
        endShape();
        // draw sphere as head
        translate(0, -gridSize/4, 0);
        sphere(gridSize/4);

        // draw another cone at an angle as a hat
        // translate(0, -gridSize/4, 0);
        // rotateX(PI/4);
        // beginShape(TRIANGLE_FAN);
        // vertex(0, 0, 0); // Cone tip
        // for (int i = 0; i <= segments; i++) {
        //     float angle = TWO_PI * i / segments;
        //     vertex(cos(angle) * radius, height/2, sin(angle) * radius);
        // }
        endShape();

        popMatrix();
    }
}