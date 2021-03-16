package launcher;

import graphics.Renderer;
import graphics.TexturedModel;
import model.entity.Entity;
import processing.core.PApplet;
import processing.core.PVector;

public class FlappyBird extends PApplet {

    public static final int HEIGHT = 500;
    public static final int WIDTH = 500;

    private Renderer renderer;
    private Entity entity;

    public void settings() {
        size(WIDTH, HEIGHT);
    }

    public void setup() {
        renderer = new Renderer(this);

        // Example entity
        entity = new Entity(new TexturedModel(loadImage("assets/test-image.jpg")),
                new PVector(100, 100),
                0, 0, 0,
                0.5f, 0.5f, 0);
        frameRate = 5;
    }

    public void draw() {
        // Do not touch, this resets the frame
        background(0);

        // Render the entity
        this.renderer.render(entity);
    }


    public void mouseMoved() {

    }

    public void mouseClicked() {

    }

    public void keyPressed() {

    }

    public static void main(final String[] args) {
        PApplet.main("launcher.FlappyBird");
    }
}
