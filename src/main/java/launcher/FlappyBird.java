package launcher;

import graphics.Renderer;
import graphics.TexturedModel;
import graphics.color.Color;
import graphics.gui.Button;
import events.Command;
import graphics.gui.Label;
import graphics.gui.Panel;
import model.entity.Entity;
import model.game.Game;
import model.game.HumanGame;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.Collections;

public class FlappyBird extends PApplet {

    public static final int HEIGHT = 500;
    public static final int WIDTH = 800;

    private Renderer renderer;
    private Entity entity;
    private Game game;

    private Button button;
    private Panel endScreen;
    private Panel controlPanel;

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
        final TexturedModel birdModel = new TexturedModel(loadImage("assets/bird.png"));
        final TexturedModel pipeModel = new TexturedModel(loadImage("assets/pipe.png"));
        game = new HumanGame(birdModel, pipeModel);

        button = new Button(Game.DIM_X / 2f - 75, Game.DIM_Y / 2f - 25, 150, 50, "Rejouez !", new Color(245, 245, 80), 20, "ROG FONTS", new Command() {
            @Override
            public void execute() {
                game.reset();
            }
        });
        final Label l = new Label(Game.DIM_X / 2f, Game.DIM_Y / 4f + 60, () -> "Votre score: " + game.getScore().getScore(), 20, "ROG FONTS");
        endScreen = new Panel(Game.DIM_X / 2f - 150, Game.DIM_Y / 2f - 100,
                300, 200,
                Collections.singletonList(button),
                new Color(200, 100, 100),
                Collections.singletonList(l));

        controlPanel = new Panel(Game.DIM_X, 0, WIDTH, Game.DIM_Y + Game.TERRAIN_HEIGHT, new ArrayList<>(), new Color(242, 231, 191), new ArrayList<>());
    }

    public void draw() {
        if(!game.isFinished()) {
            game.update();
        }

        // Do not touch, this resets the frame
        background(255);



        // Render the entity
        this.renderer.render(game);

        this.renderer.render(controlPanel);
        
        if(game.isFinished()) {
            this.renderer.render(endScreen);
        }

        // game.update();


    }


    public void mouseMoved() {

    }

    public void mouseClicked() {
        if(game.isFinished()) {
            button.click(mouseX, mouseY);
        }
    }

    public void keyPressed() {
        if(key == ' ') {
            game.makeBirdsJump();
        }
        if(game.isFinished() && key == '\n') {
            game.reset();
        }
    }

    public static void main(final String[] args) {
        PApplet.main("launcher.FlappyBird");
    }
}
