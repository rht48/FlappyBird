package model.game;

import graphics.TexturedModel;
import model.entity.Bird;
import model.entity.IABird;
import model.entity.Pipe;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class IAGame extends Game {

    private final List<Bird> birds = new ArrayList<>();

    public IAGame(final TexturedModel birdModel, final TexturedModel pipeModel) {
        super(birdModel, pipeModel);
    }

    @Override
    public List<Bird> getBirds() {
        return this.birds;
    }

    @Override
    public void reset() {
        this.birds.forEach(bird -> {
            final PVector position = bird.getPosition();
            position.x = 100;
            position.y = 100;
            final IABird b = (IABird) bird;
            b.setNext(true);
            b.getScore().reset();
        });
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
            }else {
                b.increasePosition((float) -Pipe.speed, 0, 0);
            }
        });

        // Add a new pipe on the right if necessary
        if(pipes.isEmpty()) {
            pipes.add(new Pipe(this.pipeModel, new PVector(DIM_X, 0), 0, 0, 0, 1f, 1f, 0));
            pipes.add(new Pipe(this.pipeModel, new PVector(DIM_X + 250, 0), 0, 0, 0, 1f, 1f, 0));
        } else if(pipes.get(pipes.size() - 1).distanceTravelled() > 250) {
            pipes.add(new Pipe(this.pipeModel, new PVector(DIM_X, 0), 0, 0, 0, 1f, 1f, 0));
        }

        // Remove a pipe getting off the window
        final Pipe firstPipe = pipes.get(0);
        if(firstPipe.dismiss()) {
            pipes.remove(firstPipe);
        }

        pipes.forEach(Pipe::update);

        final AtomicBoolean scoreIncremented = new AtomicBoolean(false);
        // Update pipes
        birds.forEach(bird ->  {
            final IABird b = (IABird) bird;
            if(bird.getPosition().y + bird.getHeight() >= Game.DIM_Y) {
                b.setNext(false);
            }
            pipes.forEach(pipe -> {
                if(b.next()) {
                    if (pipe.hitsBird(bird)) {
                        b.setNext(false);
                    } else if (pipe.birdHasPassed(bird)) {
                        b.incrementScore();
                        scoreIncremented.set(true);
                    }
                }
            });
        });

        if(scoreIncremented.get()) {
            score.incrementScore(1);
        }

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

    public float getHeightToPipe() {
        final Pipe pipe = getNextPipe();
        if (pipe != null) {
            return pipe.getBottom();
        }
        return 0;
    }

    public float getDistanceToPipe() {
        final Pipe pipe = getNextPipe();

        if(pipe != null) {
            return pipe.getPosition().x;
        }

        return 0;
    }

    public float getHeightSecondPipe() {
        final Pipe pipe = getNextPipe();
        int index = this.pipes.indexOf(pipe);
        if(index + 1 < this.pipes.size()) {
            final Pipe nextPipe = this.pipes.get(index + 1);
            return nextPipe.getBottom();
        }
        return -1000;
    }

    public Pipe getNextPipe() {
        final Pipe first = this.pipes.get(0);
        final Bird bird = getFurthestBird();
        if(first.getPosition().x + first.getWidth() > bird.getPosition().x) {
            return first;
        }
        return this.pipes.get(1);
    }

    public Bird getFurthestBird() {
        return this.birds.stream().max((b1, b2) -> {
            if(b1.getPosition().x == b2.getPosition().x) {
                return 0;
            }
            return  (b1.getPosition().x > b2.getPosition().x) ? 1 : -1;
        }).orElse(null);
    }

    @Override
    public void makeBirdsJump() {
        this.birds.forEach(Bird::jump);
    }
}
