package model.entity;

import graphics.TexturedModel;
import model.game.HumanGame;
import processing.core.PImage;
import processing.core.PVector;

public class Pipe extends Entity {

    private static final int spacing = 150;
    private final int top;
    private final int bottom;
    private double speed;
    private boolean birdHasPassed;
    private boolean highlight;
    private final float width;
    private final float height;

    public Pipe(final TexturedModel model, final PVector position,
                final float rotX, final float rotY, final float rotZ,
                final float scaleX, final float scaleY, final float scaleZ) {
        super(model, position, rotX, rotY, rotZ, scaleX, scaleY, scaleZ);
        top = (int) (Math.random() * (float) (HumanGame.DIM_Y - spacing) / 2 + ((float) HumanGame.DIM_Y / 6));
        bottom = top + spacing;
        speed = 3;
        highlight = false;
        birdHasPassed = false;
        final PImage image = model.getImage();
        final int imageWidth = image.width;
        final int imageHeight = image.height;
        height = (float) imageHeight * (float) scaleX;
        width = (float) imageWidth * (float) scaleY;
    }

    /**
     * Getter for attribute top
     * @return the top pipe ordinate
     */
    public float getTop() {
        return top;
    }

    /**
     * Getter for attribute bottom
     * @return the down pipe ordinate
     */
    public float getBottom() {
        return bottom;
    }

    /**
     * Getter for attribute width
     * @return the pipe width
     */
    public float getWidth() {
        return width;
    }


    /**
     * Has the bird hit the pipe ?
     * @param bird
     * @return whether the bird has hit or not.
     */
    public boolean hitsBird(final Bird bird) {
        // Si il est entre les deux tuyaux
        /*
        if(!((bird.getPosition().y + bird.getHeight() < top) && (bird.getPosition().y > bottom))) {
            if ((bird.getPosition().x + bird.getWidth() > position.x) && (bird.getPosition().x < position.x + width)) {
                highlight = true;
                birdHasPassed = true;
                return true;
            }
        }*/
        if(bird.getPosition().x + bird.getWidth() > position.x && bird.getPosition().x < position.x + width) {
            if(bird.getPosition().y < top || bird.getPosition().y + bird.height > bottom) {
                return true;
            }
        }
        /*
        if((bird.getPosition().y + bird.getHeight() < top) && (bird.getPosition().y > bottom)) {
            if((bird.getPosition().x + bird.getWidth() > position.x) || (bird.getPosition().x < position.x + width)) {
                highlight = true;
                birdHasPassed = true;
                return true;
            }
        }
         */
        highlight = false;
        return false;
    }

    /**
     * Has the bird successfully passed the pipe ?
     * @param bird
     * @return whether the bird has passed the pipe or not.
     */
    public boolean birdHasPassed(final Bird bird) {
        if(bird.getPosition().x > position.x + width && !birdHasPassed) {
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
        return position.x < -width;
    }

    /**
     *
     * @return the distance travelled by the pipe since it was created
     */
    public float distanceTravelled() {
        return HumanGame.DIM_X - position.x;
    }
}
