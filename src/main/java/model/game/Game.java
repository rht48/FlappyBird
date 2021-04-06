package model.game;

import graphics.TexturedModel;
import model.entity.Bird;
import model.entity.Pipe;
import model.game.score.Score;

import java.util.ArrayList;
import java.util.List;

public abstract class Game {

    /* Textured Models for spawning entities */
    protected final TexturedModel birdModel;
    protected final TexturedModel pipeModel;

    /* Entities for the game */
    protected List<Pipe> pipes;

    /* Boolean for knowing if the game is finished */
    protected boolean finished;

    protected final Score score;

    public static final int TERRAIN_HEIGHT = 20;
    public static final int DIM_X = 500;
    public static final int DIM_Y = 500 - TERRAIN_HEIGHT;


    public Game(final TexturedModel birdModel, final TexturedModel pipeModel) {
        this.birdModel = birdModel;
        this.pipeModel = pipeModel;

        this.pipes = new ArrayList<>();

        this.score = new Score();
    }

    public abstract List<Bird> getBirds();

    public abstract void reset();

    public abstract void update();

    public abstract void makeBirdsJump();

    public List<Pipe> getPipes() {
        return this.pipes;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public Score getScore() {
        return this.score;
    }

    public TexturedModel getBirdModel() {
        return birdModel;
    }

    public TexturedModel getPipeModel() {
        return pipeModel;
    }
}
