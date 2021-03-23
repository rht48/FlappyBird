package model.game.score;

public class Score {

    private int score;

    public Score() {
        this.score = 0;
    }

    public void incrementScore(final int dScore) {
        this.score += dScore;
    }

    public int getScore() {
        return this.score;
    }

}
