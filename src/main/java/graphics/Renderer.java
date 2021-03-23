package graphics;

import graphics.gui.Button;
import model.entity.Entity;
import model.entity.Pipe;
import model.game.Game;
import model.game.score.Score;
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

    public void render(final Score score) {
        a.pushMatrix();
        final String sc = "" + score.getScore();

        final float textWidth = a.textWidth(sc);
        final float textHeight = a.textAscent() + a.textDescent();

        a.translate(Game.DIM_X / 2f - textWidth / 2, textHeight);
        a.textFont(a.createFont("ROG FONTS", 48));
        a.fill(245, 245, 80);
        a.text(sc, 0, 0);

        a.popMatrix();
    }

    public void render(final Button button) {
        a.pushMatrix();

        a.translate(button.getX(), button.getY());
        a.textFont(a.createFont("ROG FONTS", 48));
        a.fill(245, 245, 80);
        a.rect(0, 0, button.getLenX(), button.getLenY());

        a.popMatrix();
    }
}
