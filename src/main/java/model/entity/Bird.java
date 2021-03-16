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

        if (position.y >= FlappyBird.HEIGHT - scaleY / 2) {
            position.y = FlappyBird.HEIGHT - scaleY / 2;
            this.velocity = 0;
        }

        if (position.y <= scaleY / 2) {
            position.y = scaleY / 2;
            this.velocity = 0;
        }
    }
}
