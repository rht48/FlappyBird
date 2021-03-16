package launcher;

import graphics.Renderer;
import graphics.TexturedModel;
import model.entity.Bird;
import model.entity.Entity;
import model.game.Game;
import processing.core.PApplet;
import processing.core.PVector;

public class FlappyBird extends PApplet {

    public static final int HEIGHT = 500;
    public static final int WIDTH = 500;

    private Renderer renderer;
    private Entity entity;
    private Game game;

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
        TexturedModel birdModel = new TexturedModel(loadImage("assets/bird.png"));
        TexturedModel pipeModel = new TexturedModel(loadImage("assets/pipe.png"));
        game = new Game(birdModel, pipeModel);
    }

    public void draw() {
        // Do not touch, this resets the frame
        background(30, 90, 90);

        // Render the entity
        Entity bird = game.getBird();
        this.renderer.render(bird);
        if(!game.isFinished()) {
            game.update();
        }
    }


    public void mouseMoved() {

    }

    public void mouseClicked() {

    }

    public void keyPressed() {
        if(key == ' ') {
            game.makeBirdJump();
        }
        if(game.isFinished() && key == '\n') {
            game.reset();
        }
    }

    public static void main(final String[] args) {
        PApplet.main("launcher.FlappyBird");
    }
}
