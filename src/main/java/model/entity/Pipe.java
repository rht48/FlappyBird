package model.entity;

import graphics.TexturedModel;
import launcher.FlappyBird;
import processing.core.PVector;

public class Pipe extends Entity{

    private int spacing;
    private int top;
    private int bottom;
    private double speed;
    private boolean birdHasPassed;
    private boolean highlight;

    public Pipe(TexturedModel model, PVector position,
                float rotX, float rotY, float rotZ,
                float scaleX, float scaleY, float scaleZ) {
        super(model, position, rotX, rotY, rotZ, scaleX, scaleY, scaleZ);
        spacing = 125;
        top = (int) (Math.random() * FlappyBird.HEIGHT * 5 / 6 + (FlappyBird.HEIGHT / 6));
        bottom = top + spacing;
        speed = 3;
        highlight = false;
        birdHasPassed = false;
    }

    /**
     * Has the bird hit the pipe ?
     * @param bird
     * @return whether the bird has hit or not.
     */
    public boolean hitsBird(final Bird bird) {
        float halfBirdHeight = bird.scaleY / 2;
        float halfBirdWidth = bird.scaleX /2;
        if((bird.getPosition().y - halfBirdHeight < top) || (bird.getPosition().y + halfBirdHeight > bottom)) {
            if ((bird.getPosition().x + halfBirdWidth > position.x) && (bird.getPosition().x - halfBirdWidth < position.x + scaleX)) {
                highlight = true;
                birdHasPassed = true;
                return true;
            }
        }
        highlight = false;
        return false;
    }

    /**
     * Has the bird successfully passed the pipe ?
     * @param bird
     * @return whether the bird has passed the pipe or not.
     */
    public boolean birdHasPassed(final Bird bird) {
        if(bird.getPosition().x > position.x && !birdHasPassed) {
            birdHasPassed = true;
            return true;
        }
        return false;
    }

    /**
     * Increase the pipe moving speed.
     * @param amount of speed to add.
     */
    public void speedUp(final double amount) {
        speed += amount;
    }

    public void update() {
        position.x -= speed;
    }

    /**
     * Has the pipe left the screen ?
     * @return whether the pipe should be dismissed or not.
     */
    public boolean dismiss() {
        return position.x < -scaleX;
    }
}
