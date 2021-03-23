package graphics;

import model.entity.Entity;
import model.entity.Pipe;
import processing.core.PApplet;

public class PipeRenderer {

    private PApplet a;

    public PipeRenderer(final PApplet applet) {
        this.a = applet;
    }

    public void render(final Pipe pipe) {
        // Push the matrix
        a.pushMatrix();
        // Translate to current position
        a.translate(pipe.getPosition().x, pipe.getBottom());
        // Scale the image down
        a.scale(pipe.getScaleX(), pipe.getScaleY());
        // Rotate the image
        a.rotate(pipe.getRotZ());
        // Place the image on the screen
        a.image(pipe.getModel().getImage(), 0, 0);
        // Pop the matrix back to where it started
        a.popMatrix();

        // Push the matrix
        a.pushMatrix();
        // Translate to current position
        a.translate(pipe.getPosition().x + pipe.getWidth(), pipe.getTop());
        // Scale the image down
        a.scale(pipe.getScaleX(), pipe.getScaleY());
        // Rotate the image
        a.rotate((float) (pipe.getRotZ() + Math.PI));
        // Place the image on the screen
        a.image(pipe.getModel().getImage(), 0, 0);
        // Pop the matrix back to where it started
        a.popMatrix();

    }
}
