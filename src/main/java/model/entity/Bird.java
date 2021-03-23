package model.entity;

import graphics.TexturedModel;
import launcher.FlappyBird;
import processing.core.PVector;

public class Bird extends Entity {

    private double gravity;
    private double lift;
    private double velocity;


    public Bird(final TexturedModel model, final PVector position,
                final float rotX, final float rotY, final float rotZ,
                final float scaleX, final float scaleY, final float scaleZ) {
        super(model, position, rotX, rotY, rotZ, scaleX, scaleY, scaleZ);
        gravity = 0.6;
        lift = -10;
        velocity = 0;

    }

    /**
     * Make the bird flap once and perform a great jump.
     */
    public void jump() {
        velocity = lift;
    }

    public void update() {
        velocity += gravity;
        position.y += velocity;

        // Cas en bas
        if (position.y >= FlappyBird.HEIGHT - height) {
            position.y = FlappyBird.HEIGHT - height;
            this.velocity = 0;
        }

        // Cas en haut
        if (position.y <= 0) {
            position.y = 0;
            this.velocity = 0;
        }
    }

    public float getHeight() {
        return this.height;
    }

    public float getWidth() {
        return this.width;
    }

}
