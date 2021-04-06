package model.game;


import graphics.TexturedModel;
import model.entity.Bird;
import model.entity.Pipe;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HumanGame extends Game {



    /* Entities for the game */
    private Bird bird;


    public HumanGame(final TexturedModel birdModel, final TexturedModel pipeModel) {
        super(birdModel, pipeModel);

        this.bird = new Bird(this.birdModel,
                new PVector(100, 100));
        this.reset();
    }

    @Override
    public List<Bird> getBirds() {
        return Arrays.asList(this.bird);
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



        if(this.bird.getPosition().y + this.bird.getHeight() >= HumanGame.DIM_Y) {
            this.finished = true;
        }
    }

    /**
     * Makes the bird jump
     */
    public void makeBirdsJump() {
        this.bird.jump();
    }

    public void reset() {
        this.bird = new Bird(this.birdModel,
                new PVector(100, 100));
        this.pipes = new ArrayList<>();
        this.score.reset();
        this.finished = false;
    }
}
