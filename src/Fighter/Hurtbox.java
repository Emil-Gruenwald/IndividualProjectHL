public class Hurtbox {
    double x, y, width, height;

    public Hurtbox(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isCollidingWith(Hurtbox other) {
        return this.x < other.x + other.width &&
               this.x + this.width > other.x &&
               this.y < other.y + other.height &&
               this.y + this.height > other.y;
    }

    public boolean isCollidingWith(Hitbox hitbox) {
        return this.x < hitbox.x + hitbox.width &&
               this.x + this.width > hitbox.x &&
               this.y < hitbox.y + hitbox.height &&
               this.y + this.height > hitbox.y && hitbox.frame >= hitbox.firstFrame && hitbox.frame <= hitbox.lastFrame;
    }
}
