class Node {
    GridObject base;
    int xMod, yMod, zMod;
    boolean active;
    ArrayList<Node> neighbors = new ArrayList<Node>();

    public Node(int xMod, int yMod, int zMod, GridObject base) {
        this.xMod = xMod;
        this.yMod = yMod;
        this.zMod = zMod;
        this.base = base;
        active = true;
    }

    void addNeighbor(Node neighbor) {
        neighbors.add(neighbor);
        neighbor.neighbors.add(this);
    }

    float[] getPosition() {
        return new float[]{base.x + xMod, base.y + yMod, base.z + zMod};
    }

    boolean mousePressed() {
        if (isHovered()) {
            return true;
        }
        return false;
    }

    boolean isHovered() {
        return dist(mouseX, mouseY, screenX(base.x, base.y, base.z), screenY(base.x, base.y, base.z)) < gridSize/2;
    }
}