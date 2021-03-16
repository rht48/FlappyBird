package graphics;

import model.entity.Entity;
import processing.core.PApplet;

public class Renderer {

    private PApplet a;

    public Renderer(final PApplet applet) {
        this.a = applet;
    }

    public void render(final Entity entity) {
        // Push the matrix
        a.pushMatrix();

        // Translate to current position
        a.translate(entity.getPosition().x, entity.getPosition().y);

        // Scale the image down
        a.scale(entity.getScaleX(), entity.getScaleY());

        // Rotate the image
        a.rotate(entity.getRotZ());

        // Place the image on the screen
        a.image(entity.getModel().getImage(), 0, 0);

        // Pop the matrix back to where it started
        a.popMatrix();
    }
}
