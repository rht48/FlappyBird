package model.game;

import model.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Entity bird;
    private final List<Entity> pipes;

    public Game() {
        this.pipes = new ArrayList<>();
    }

    public void update() {

    }

    public Entity getBird() {
        return this.bird;
    }

    public List<Entity> getPipes() {
        return this.pipes;
    }
}
