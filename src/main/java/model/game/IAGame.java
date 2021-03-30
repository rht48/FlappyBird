package model.game;

import graphics.TexturedModel;
import model.entity.Bird;
import model.entity.IABird;
import model.entity.Pipe;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class IAGame extends Game {

    private final List<Bird> birds;

    public IAGame(final TexturedModel birdModel, final TexturedModel pipeModel) {
        super(birdModel, pipeModel);
        this.birds = new ArrayList<>();
    }

    @Override
    public List<Bird> getBirds() {
        return this.birds;
    }

    @Override
    public void reset() {
        this.pipes = new ArrayList<>();
        this.score.reset();
        this.finished = false;
    }

    @Override
    public void update() {
        this.birds.forEach(bird -> {
            final IABird b = (IABird) bird;
            if(b.next()) {
                b.update();
            }
        });

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

        pipes.forEach(Pipe::update);

        // Update pipes
        birds.forEach(bird -> pipes.forEach(pipe -> {
            final IABird b = (IABird) bird;
            if(b.next()) {
                if (pipe.hitsBird(bird)) {
                    b.setNext(false);
                } else if (pipe.birdHasPassed(bird)) {
                    b.incrementScore();
                }
            }
        }));


        this.finished = true;
        for(var bird : birds) {
            final IABird b = (IABird) bird;
            if(b.next()) {
                this.finished = false;
                break;
            }
        }
    }

    public void addBird(final Bird bird) {
        this.birds.add(bird);
    }

    @Override
    public void makeBirdsJump() {
        this.birds.forEach(Bird::jump);
    }
}
