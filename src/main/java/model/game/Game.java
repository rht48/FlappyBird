package model.game;


import graphics.TexturedModel;
import launcher.FlappyBird;
import model.entity.Bird;
import model.entity.Entity;
import model.entity.Pipe;
import model.game.score.Score;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class Game {

    /* Textured Models for spawning entities */
    private final TexturedModel birdModel;
    private final TexturedModel pipeModel;

    /* Entities for the game */
    private Bird bird;
    private List<Pipe> pipes;

    /* Boolean for knowing if the game is finished */
    private boolean finished;

    private final Score score;

    public static final int TERRAIN_HEIGHT = 20;
    public static final int DIM_X = 500;
    public static final int DIM_Y = 500 - TERRAIN_HEIGHT;

    public Game(final TexturedModel birdModel, final TexturedModel pipeModel) {
        this.birdModel = birdModel;
        this.pipeModel = pipeModel;

        this.bird = new Bird(this.birdModel,
                new PVector(100, 100),
                0, 0, 0,
                0.1f, 0.1f, 0);
        this.pipes = new ArrayList<>();

        this.score = new Score();
        this.reset();
    }

    /**
     * Updates the game
     */
    public void update() {

        this.bird.update();

        // Add a new pipe on the right if necessary
        if(pipes.isEmpty()) {
            pipes.add(new Pipe(this.pipeModel, new PVector(DIM_X, 0), 0, 0, 0, 1f, 1f, 0));
        } else if(pipes.get(pipes.size() - 1).distanceTravelled() > 250) {
            pipes.add(new Pipe(this.pipeModel, new PVector(DIM_X, 0), 0, 0, 0, 1f, 1f, 0));
        }

        // Remove a pipe getting off the window
        final Pipe firstPipe = pipes.get(0);
        if(firstPipe.dismiss()) {
            pipes.remove(firstPipe);
        }

        // Update pipes
        pipes.forEach(pipe -> {
            pipe.update();
            if(pipe.hitsBird(bird)) {
                this.finished = true;
            } else if(pipe.birdHasPassed(bird)) {
                this.score.incrementScore(1);
            }
        });



        if(this.bird.getPosition().y + this.bird.getHeight() >= Game.DIM_Y) {
            this.finished = true;
        }
    }

    /**
     * Makes the bird jump
     */
    public void makeBirdJump() {
        this.bird.jump();
    }

    public void reset() {
        this.bird = new Bird(this.birdModel,
                new PVector(100, 100),
                0, 0, 0,
                0.1f, 0.1f, 0);
        this.pipes = new ArrayList<>();
        this.score.reset();
        this.finished = false;
    }

    public Entity getBird() {
        return this.bird;
    }

    public List<Pipe> getPipes() {
        return this.pipes;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public Score getScore() {
        return this.score;
    }
}
