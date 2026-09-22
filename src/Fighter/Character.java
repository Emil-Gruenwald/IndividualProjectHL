import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;

import java.util.Set;
import java.util.ArrayList;

import javafx.scene.input.KeyCode;

public class Character {
    public float x, y, sx, sy, groundControl, airControl, gravity;
    public int jumpsRemaining, width, height, jumpCount, jumpDelay, maxFallSpeed, imageOffsetX, imageOffsetY, imageWidth;
    public boolean isOnGround, isOnLedge, fastFalling;
    public Image sprite;
    public ImageView spriteRender;
    public Character(int x, int y, int width, int height, Image sprite) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        sx = 0;
        sy = 0;
        groundControl = 2f;
        airControl = .5f;
        gravity = 0.5f;
        jumpsRemaining = 2;
        jumpCount = 2;
        jumpDelay = 0;
        maxFallSpeed = 10;
        isOnGround = false;
        isOnLedge = false;
        fastFalling = false;
        this.sprite = sprite;
    }

    public void move(Set<KeyCode> keysPressed, ArrayList<Input> inputs, Level currentLevel) {
        float previousY = y;

        if (!isOnGround && !isOnLedge) {
            for (Platform platform : currentLevel.platforms) {
                if (isStandingOn(platform)) {
                    isOnGround = true;
                    jumpsRemaining = jumpCount;
                    break;
                }
            }
        }

        boolean wasGrounded = isOnGround || isOnLedge;

        if (!isOnLedge && !isOnGround) {
            sy += gravity;
        }
        if (sy > maxFallSpeed) {
            sy = maxFallSpeed;
        }
        boolean jumpPressed = inputs.stream().anyMatch(input -> input.keyCode == KeyCode.W);

        if (!wasGrounded && !isOnGround && !isOnLedge && jumpsRemaining == jumpCount) {
            jumpsRemaining--; // Decrease jumpsRemaining if the player is in the air and hasn't jumped yet
        }

        if (jumpPressed && jumpsRemaining > 0) {
            sy = -10; // Jump
            jumpsRemaining--;
            isOnGround = false; // Character is now in the air
            isOnLedge = false; // If the player was on a ledge, they should leave it when jumping
            inputs.removeIf(input -> input.keyCode == KeyCode.W);
        }
        if (keysPressed.contains(KeyCode.S)) {
            sy = maxFallSpeed * 1.5f; // Fast fall
            isOnLedge = false; // If the player is on a ledge, they should drop down
            fastFalling = true;
        }

        y += sy;
        isOnGround = false; // Reset ground state before checking collisions

        for (Platform platform : currentLevel.platforms) {
            if (isCollidingWith(platform)) {
                if (platform.isSolid) {
                    if (sy >= 0 && previousY + height <= platform.y) {
                        y = platform.y - height;
                    } else if (sy < 0 && previousY >= platform.y + platform.height) {
                        y = platform.y + platform.height;
                    }

                    if (sy >= 0 && y + height <= platform.y) {
                        isOnGround = true;
                        jumpsRemaining = jumpCount; // Reset jumps when landing
                    }
                    sy = 0; // Stop vertical movement
                } else {
                    // Only land on not solid platforms if falling onto them
                    if (sy > 0 && y + height <= platform.y + sy && !keysPressed.contains(KeyCode.S)) {
                        // while (isCollidingWith(platform)) {
                        // y -= Math.abs(sy) / sy;
                        // }
                        // isOnGround = true;
                        // jumpsRemaining = jumpCount; // Reset jumps when landing
                        // sy = 0; // Stop vertical movement
                        if (sy >= 0 && previousY + height <= platform.y) {
                            y = platform.y - height;
                        } else if (sy < 0 && previousY >= platform.y + platform.height) {
                            y = platform.y + platform.height;
                        }

                        isOnGround = true;
                        jumpsRemaining = jumpCount; // Reset jumps when landing

                        sy = 0; // Stop vertical movement
                    }
                }
            }
        }

        float previousX = x;
        if (keysPressed.contains(KeyCode.A) && !isOnLedge) {
            if (isOnGround) {
                sx -= groundControl; // Move left on ground
            } else {
                sx -= airControl; // Move left in air
            }
        }
        if (keysPressed.contains(KeyCode.D) && !isOnLedge) {
            if (isOnGround) {
                sx += groundControl; // Move right on ground
            } else {
                sx += airControl; // Move right in air
            }
        }

        if (isOnGround) {
            sx *= 0.8; // Friction on ground
        } else {
            sx *= 0.95; // Air resistance
        }
        x += sx;

        for (Platform platform : currentLevel.platforms) {
            if (isCollidingWith(platform)) {
                if (platform.isSolid) {
                    if (sx > 0 && previousX + width <= platform.x) {
                        x = platform.x - width;
                    } else if (sx < 0 && previousX >= platform.x + platform.width) {
                        x = platform.x + platform.width;
                    }
                    sx = 0; // Stop horizontal movement
                    if (platform.hasLedge && Math.abs(y - platform.y) < 5 && !isOnLedge
                            && !keysPressed.contains(KeyCode.S)) {
                        isOnLedge = true;
                        y = platform.y;
                        jumpsRemaining = jumpCount; // Reset jumps when landing on ledge
                        sy = 0; // Stop vertical movement
                    }
                }
            }
        }

    }

    public void setUpRender() {
        spriteRender = new ImageView(sprite);
        spriteRender.setFitWidth(imageWidth);
        spriteRender.setPreserveRatio(true);
        
    }

    public void render(GraphicsContext gc) {
        if (spriteRender != null) {
            spriteRender.setLayoutX(x + imageOffsetX);
            spriteRender.setLayoutY(y + imageOffsetY);
            gc.setFill(Color.RED);
            gc.fillRect(x, y, 50, 100);
        }
    }

    private boolean isCollidingWith(Platform platform) {
        return x < platform.x + platform.width &&
                x + width > platform.x &&
                y < platform.y + platform.height &&
                y + height > platform.y;
    }

    private boolean isStandingOn(Platform platform) {
        return x < platform.x + platform.width &&
                x + width > platform.x &&
                Math.abs(y + height - platform.y) < 0.001f;
    }
}
