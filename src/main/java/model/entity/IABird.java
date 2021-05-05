package model.entity;

import graphics.TexturedModel;
import model.game.score.Score;
import processing.core.PVector;

public class IABird extends Bird {

    private final int id;
    private final Score score;
    private boolean next = true;
    private static int ID_GENERATOR = 0;

    public IABird(final TexturedModel model, final PVector position) {
        super(model, position);
        this.score = new Score();
        this.id = ID_GENERATOR++;
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

    public int getId() { return this.id; };

    @Override
    public String toString() {
        return "b" + id;
    }

    @Override
    public boolean equals(final Object o) {
        if(o instanceof IABird) {
            final IABird b = (IABird) o;
            return this.id == b.id;
        }
        return false;
    }
}
