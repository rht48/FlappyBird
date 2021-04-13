package launcher;

import graphics.Renderer;
import graphics.TexturedModel;
import graphics.color.Color;
import graphics.gui.Button;
import graphics.gui.Label;
import graphics.gui.Panel;
import model.entity.Entity;
import model.game.Game;
import model.game.IAGame;
import model.ia.IAPlayer;
import model.ia.qlearn.QLearnIA;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FlappyBird extends PApplet {

    public static final int HEIGHT = 500;
    public static final int WIDTH = 800;

    private Renderer renderer;
    private Entity entity;
    private Game game;

    private Button button;
    private Panel endScreen;
    private Panel controlPanel;
    private final List<IAPlayer> players = new ArrayList<>();

    private int amount = 1;

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

        // game = new HumanGame(birdModel, pipeModel);
        game = new IAGame(birdModel, pipeModel);

//        for(int i = 0; i < 10; i++) {
//            players.add(new RandomIA((IAGame) game));
//        }

        players.add(new QLearnIA((IAGame) game));

        button = new Button(Game.DIM_X / 2f - 75, Game.DIM_Y / 2f - 25, 150, 50, "Rejouez !", new Color(245, 245, 80), 10, "ROG FONTS", () -> game.reset());
        final Label l = new Label(Game.DIM_X / 2f, Game.DIM_Y / 4f + 60, () -> "Votre score: " + game.getScore().getScore(), 20, "ROG FONTS");
        endScreen = new Panel(Game.DIM_X / 2f - 150, Game.DIM_Y / 2f - 100,
                300, 200,
                Collections.singletonList(button),
                new Color(200, 100, 100),
                Collections.singletonList(l));

        final Button speedDown = new Button(Game.DIM_X, 0, 50, 20, "SpeedDown", new Color(100, 100, 100), 10, "Arial", () -> amount = 1);
        final Button speedUp = new Button(Game.DIM_X + 50, 0, 50, 20, "SpeedUp", new Color(242, 50, 50), 10, "Arial", () -> amount = 1000000);
        controlPanel = new Panel(Game.DIM_X, 0, WIDTH, Game.DIM_Y + Game.TERRAIN_HEIGHT, Arrays.asList(speedDown, speedUp), new Color(242, 231, 191), new ArrayList<>());
        game.reset();
    }

    public void draw() {
        if(!game.isFinished()) {
            for(int i = 0; i < amount; i++) {
                game.update();
                if (game instanceof IAGame) {
                    players.forEach(IAPlayer::update);
                }
            }
        }

        // Do not touch, this resets the frame
        background(255);



        // Render the entity
        this.renderer.render(game);

        this.renderer.render(controlPanel);



        if(!(game instanceof IAGame) && game.isFinished()) {
            this.renderer.render(endScreen);
        }

    }


    public void mouseMoved() {

    }

    public void mouseClicked() {
        if(!(game instanceof IAGame) && game.isFinished()) {
            button.click(mouseX, mouseY);
        }
        controlPanel.getButtons().forEach(b -> b.click(mouseX, mouseY));
    }

    public void keyPressed() {
        if(!(game instanceof IAGame) && key == ' ') {
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
