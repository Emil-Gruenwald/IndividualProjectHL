public class Hitbox {
    double x, y, width, height;
    int frame, firstFrame, lastFrame;

    public Hitbox(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isCollidingWith(Hitbox other) {
        return this.x < other.x + other.width &&
               this.x + this.width > other.x &&
               this.y < other.y + other.height &&
               this.y + this.height > other.y && this.frame >= this.firstFrame && this.frame <= this.lastFrame
               && other.frame >= other.firstFrame && other.frame <= other.lastFrame;
    }
    
    public boolean isCollidingWith(Hurtbox hurtbox) {
        return this.x < hurtbox.x + hurtbox.width &&
               this.x + this.width > hurtbox.x &&
               this.y < hurtbox.y + hurtbox.height &&
               this.y + this.height > hurtbox.y && this.frame >= this.firstFrame && this.frame <= this.lastFrame;
    }
}
