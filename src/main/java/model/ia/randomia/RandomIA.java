package model.ia.randomia;

import model.entity.IABird;
import model.game.IAGame;
import model.ia.IAPlayer;
import processing.core.PVector;

public class RandomIA implements IAPlayer {

    private final IABird bird;

    public RandomIA(final IAGame game) {
        this.bird = new IABird(game.getBirdModel(), new PVector(0, 0));
        game.addBird(bird);
    }

    @Override
    public void update() {
        if(Math.random() < 0.03) {
            this.bird.jump();
        }
    }

}
