package model.game;


import graphics.TexturedModel;
import launcher.FlappyBird;
import model.entity.Bird;
import model.entity.Entity;
import model.entity.Pipe;
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

    private int score;

    public Game(final TexturedModel birdModel, final TexturedModel pipeModel) {
        this.birdModel = birdModel;
        this.pipeModel = pipeModel;

        this.bird = new Bird(this.birdModel,
                new PVector(100, 100),
                0, 0, 0,
                0.1f, 0.1f, 0);
        this.pipes = new ArrayList<>();

        this.score = 0;
        this.reset();
    }

    /**
     * Updates the game
     */
    public void update() {

        this.bird.update();

        // Add a new pipe on the right if necessary
        if(pipes.isEmpty()) {
            pipes.add(new Pipe(this.pipeModel, new PVector(FlappyBird.WIDTH, FlappyBird.HEIGHT), 0, 0, 0, 0.1f, 0.1f, 0));
        } else if(pipes.get(pipes.size() - 1).distanceTravelled() > 150) {
            pipes.add(new Pipe(this.pipeModel, new PVector(FlappyBird.WIDTH, FlappyBird.HEIGHT), 0, 0, 0, 0.1f, 0.1f, 0));
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
                // end game
                System.out.println("The bird hit a pipe. Score : " + score);
            } else if(pipe.birdHasPassed(bird)) {
                score++;
            }
        });



        if(this.bird.getPosition().y >= FlappyBird.HEIGHT - 100) {
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
}
