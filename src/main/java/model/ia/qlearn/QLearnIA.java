package model.ia.qlearn;

import model.entity.IABird;
import model.game.IAGame;
import model.ia.IAPlayer;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class QLearnIA implements IAPlayer {

    private final IABird bird;
    private final IAGame game;

    private double lambda = 0.05;
    private double epsilon = 0.01;
    private final double gamma = 1;
    private final QTable table;

    private int genNumber = 0;
    private int maxScore = 0;

    private State prevState;
    private Action prevAction;

    private int numberJumps = 0;

    private final List<Move> history;
    private static final int NUMBER_MOVES = 100;

    public QLearnIA(final IAGame game) {
        this.bird = new IABird(game.getBirdModel(), new PVector(0, 0));
        this.game = game;
        game.addBird(bird);
        this.table = new QTable();
        this.history = new ArrayList<>();
    }

//    @Override
//    public void update() {
//        final int distanceToPipe = (int) (this.game.getDistanceToPipe() - (this.bird.getPosition().x + this.bird.getWidth()));
//        final int heightToPipe = (int) (this.game.getHeightToPipe() - (this.bird.getPosition().y + this.bird.getHeight()));
//        final State currentState = new State(distanceToPipe, heightToPipe, (float) bird.getVelocity(), bird.next());
//        final Action action = this.table.argmax(currentState);
//
//        if(action.equals(Action.JUMP)) {
//            this.bird.jump();
//            numberJumps++;
//        }
//
//        this.history.add(new Move(currentState, action));
//
//        if(!bird.next()) {
//            updateTable();
//            int score = bird.getScore().getScore();
//            if(score > maxScore) {
//                maxScore = score;
//            }
//            game.reset();
//            genNumber++;
//            System.out.print("Generation: " + genNumber + " Max score: " + maxScore + "\r");
//        }
//    }

//    private void updateTable() {
//        Move lastMove = null;
//        while(!this.history.isEmpty()) {
//            final Move currentMove = this.history.remove(0);
//            final double reward = calculateReward(currentMove);
//            if(lastMove != null) {
//
//                final double lr = getLearningRate(lastMove.getState(), lastMove.getAction());
//                final double value = lr * (reward + gamma * table.max(currentMove.getState()))
//                        + (1.0 - lr) * table.get(lastMove.getState(), lastMove.getAction());
//                table.set(lastMove.getState(), lastMove.getAction(), value);
//            }
//            lastMove = currentMove;
//        }
//    }

    @Override
    public void update() {
        final int distanceToPipe = (int) (this.game.getDistanceToPipe() - (this.bird.getPosition().x + this.bird.getWidth()));
        final int heightToPipe = (int) (this.game.getHeightToPipe() - (this.bird.getPosition().y + this.bird.getHeight()));
        final State currentState = new State(distanceToPipe, heightToPipe, (float) bird.getVelocity());
        final Action action;

        if(this.prevState != null && this.prevAction != null) {
            final double reward = calculateReward();
            final double lr = getLearningRate(prevState, prevAction);
            final double value = lr * (reward + gamma * table.max(currentState)) + (1.0 - lr) * table.get(prevState, prevAction);
            table.set(prevState, prevAction, value);
        }

        if(Math.random() < epsilon) {
            action = Action.getRandom();
        }else {
            action = table.argmax(currentState);
        }

        if(action.equals(Action.JUMP)) {
            bird.jump();
        }

        this.prevState = currentState;
        this.prevAction = action;

        if(!bird.next()) {
            final int score = bird.getScore().getScore();
            if(score > maxScore) {
                maxScore = score;
            }
            game.reset();
            genNumber++;
            System.out.print("Generation: " + genNumber + " Max score: " + maxScore + "\r");
            this.prevState = null;
            this.prevAction = null;
            epsilon *= 0.99999;
        }
    }

    public double calculateReward() {
        return bird.next() ? 1 : -1000;
    }

//    public double calculateReward(final Move move) {
//
//        if(move.getAction().equals(Action.JUMP)) {
//            numberJumps --;
//        }
//        if(numberJumps == 0 || !move.getState().isAlive()) {
//            return -10;
//        }
//        return 10;
////        if(numberJumps == 0) {
////            return -1000;
////        }
////        return 15;
//    }

    public double getLearningRate(final State state, final Action action) {
        final double number = table.getNumber(state, action);
        return 1 / (1.0 + number);
    }

    public int getGenNumber() {
        return this.genNumber;
    }
}
