package graphics;

import graphics.gui.Button;
import graphics.gui.Label;
import graphics.gui.Panel;
import model.entity.Entity;
import model.entity.Pipe;
import model.game.Game;
import model.game.score.Score;
import processing.core.PApplet;
import processing.core.PFont;


public class Renderer {

    private PApplet a;
    private final PFont scoreFont;

    public Renderer(final PApplet applet) {
        this.a = applet;
        scoreFont = a.createFont("ROG FONTS", 48);
    }

    public void render(final Game game) {
        a.noStroke();
        a.fill(0, 255, 255);
        a.rect(0, 0, Game.DIM_X, Game.DIM_Y);

        game.getBirds().forEach(this::render);
        game.getPipes().forEach(this::render);
        render(game.getScore());

        a.fill(200, 173, 127);
        a.rect(0, Game.DIM_Y, Game.DIM_X, Game.DIM_Y + Game.TERRAIN_HEIGHT);
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

        a.textFont(scoreFont);
        final float textWidth = a.textWidth(sc);
        final float textHeight = -a.textAscent() + a.textDescent();

        a.translate(Game.DIM_X / 2f - textWidth / 2, -textHeight);

        a.fill(245, 245, 80);
        a.text(sc, 0, 0);

        a.popMatrix();
    }

    public void render(final Button button) {
        a.pushMatrix();

        a.translate(button.getX(), button.getY());
        a.fill(button.getColor().getRed(), button.getColor().getGreen(), button.getColor().getBlue());
        a.rect(0, 0, button.getLenX(), button.getLenY());

        a.fill(0, 0, 0);
        a.textFont(a.createFont(button.getFont(), button.getTextSize()));

        final String text = button.getText();
        final float textWidth = a.textWidth(text);
        final float textHeight = -a.textAscent() + a.textDescent();
        final float lenX = button.getLenX();
        final float lenY = button.getLenY();

        a.text(text, (lenX - textWidth) / 2, (lenY - textHeight) / 2);

        a.popMatrix();
    }

    public void render(final Label label) {
        a.pushMatrix();
        a.translate(label.getX(), label.getY());
        a.textFont(a.createFont(label.getFont(), label.getTextSize()));
        final String text = label.getText();
        final float textWidth = a.textWidth(text);
        final float textHeight = -a.textAscent() + a.textDescent();
        a.fill(0);
        a.text(text, (-textWidth) / 2, (-textHeight) / 2);
        a.popMatrix();
    }

    public void render(final Panel panel) {
        a.pushMatrix();

        a.translate(panel.getX(), panel.getY());
        a.fill(panel.getColor().getRed(), panel.getColor().getGreen(), panel.getColor().getBlue());
        a.rect(0, 0, panel.getLenX(), panel.getLenY());

        a.popMatrix();
        panel.getLabels().forEach(this::render);
        panel.getButtons().forEach(this::render);

    }
}
