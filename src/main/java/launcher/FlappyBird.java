package launcher;

import graphics.Renderer;
import processing.core.PApplet;

public class FlappyBird extends PApplet{

    private Renderer renderer;

    public void settings() {
        size(500, 500);
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

    public static void main(String[] args) {
        PApplet.main("launcher.FlappyBird");
    }
}
