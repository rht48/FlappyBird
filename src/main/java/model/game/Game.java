package model.game;


import model.entity.Bird;
import model.entity.Entity;
import model.entity.Pipe;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Bird bird;
    private Entity pipe;
    private final List<Entity> pipes;

    public Game(final Bird bird) {
        this.bird = bird;
        this.pipes = new ArrayList<>();
    }

    public void update() {
        this.bird.update();
    }

    public void makeBirdJump() {
        this.bird.jump();
    }

    public Entity getBird() {
        return this.bird;
    }

    public List<Entity> getPipes() {
        return this.pipes;
    }
}
