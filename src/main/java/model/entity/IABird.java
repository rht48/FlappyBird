package model.entity;

import graphics.TexturedModel;
import model.game.score.Score;
import processing.core.PVector;

public class IABird extends Bird {

    private final Score score;
    private boolean next = true;

    public IABird(final TexturedModel model, final PVector position) {
        super(model, position);
        this.score = new Score();
    }

    public void incrementScore() {
        this.score.incrementScore(1);
    }

    public Score getScore() {
        return this.score;
    }

    public void setNext(final boolean bool) {
        this.next = bool;
    }

    public boolean next() {
        return this.next;
    }
}
