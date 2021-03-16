package model.game;


import graphics.TexturedModel;
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
    private final Bird bird;
    private final List<Pipe> pipes;

    public Game(final TexturedModel birdModel, final TexturedModel pipeModel) {
        this.birdModel = birdModel;
        this.pipeModel = pipeModel;

        this.bird = new Bird(this.birdModel,
                new PVector(100, 100),
                0, 0, 0,
                0.1f, 0.1f, 0);
        this.pipes = new ArrayList<>();
    }

    /**
     * Updates the game
     */
    public void update() {
        this.bird.update();
    }

    /**
     * Makes the bird jump
     */
    public void makeBirdJump() {
        this.bird.jump();
    }

    public Entity getBird() {
        return this.bird;
    }

    public List<Pipe> getPipes() {
        return this.pipes;
    }
}
