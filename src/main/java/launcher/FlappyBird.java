package launcher;

import graphics.Renderer;
import processing.core.PApplet;

public class FlappyBird extends PApplet {

    public static final int HEIGHT = 500;
    public static final int WIDTH = 500;

    private Renderer renderer;

    public void settings() {
        size(WIDTH, HEIGHT);
    }

    public void setup() {
        renderer = new Renderer(this);
        frameRate = 5;
    }

    public void draw() {
        this.renderer.render(new Object());
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
