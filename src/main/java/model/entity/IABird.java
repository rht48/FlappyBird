package model.entity;

import graphics.TexturedModel;
import model.game.score.Score;
import processing.core.PVector;

public class IABird extends Bird {

    private final Score score;
    private boolean next = true;

    public IABird(final TexturedModel model, final PVector position, final float rotX, final float rotY, final float rotZ, final float scaleX, final float scaleY, final float scaleZ) {
        super(model, position, rotX, rotY, rotZ, scaleX, scaleY, scaleZ);
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
