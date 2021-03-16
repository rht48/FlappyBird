package launcher;

import processing.core.PApplet;

public class FlappyBird extends PApplet{

    Object o;
    int x = 500;

    public void settings() {
        size(500, 500);
    }

    public void setup() {
        o = new Object();
        frameRate = 5;
    }

    public void draw() {
        background(200);

        rect(x--, height - 50, 20, 50);

    }

    public static void main(String[] args) {
        PApplet.main("launcher.FlappyBird");
    }
}
