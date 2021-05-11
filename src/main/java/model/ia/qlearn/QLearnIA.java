package model.ia.qlearn;

import model.entity.IABird;
import model.entity.Pipe;
import model.game.IAGame;
import model.ia.IAPlayer;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class QLearnIA implements IAPlayer {

    private final IABird bird;
    private final IAGame game;

    private double lambda = 0.01;
    private double epsilon = 0.05;
    private final double gamma = 1;
    private final QTable table;

    private int genNumber = 0;
    private int maxScore = 0;

    private State prevState;
    private Action prevAction;

    private int numberJumps = 0;
    private int randoms = 0;

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
//        final State currentState = new State(distanceToPipe, heightToPipe, 0, false, 0);
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
//
//    private void updateTable() {
//        Move lastMove = null;
//        while(!this.history.isEmpty()) {
//            final Move currentMove = this.history.remove(0);
//            final double reward = calculateReward(currentMove, this.history.size());
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
        final int birdHeight = (int) (this.bird.getPosition().y + this.bird.getHeight());
        final int heightToPipe = (int) (this.game.getHeightToPipe() - (this.bird.getPosition().y + this.bird.getHeight()));
        final int heightSecondPipe = (int) (this.game.getHeightSecondPipe() - (this.bird.getPosition().y + this.bird.getHeight()));
//        System.out.println(distanceToPipe);
        final State currentState = new State(distanceToPipe, heightToPipe, 0, false, 0);
        final Action action;

        if(this.prevState != null && this.prevAction != null) {
            final double reward = calculateReward(prevState, prevAction);
            final double lr = getLearningRate(prevState, prevAction);
            final double value = lr * (reward + gamma * table.max(currentState)) + (1.0 - lr) * table.get(prevState, prevAction);
            table.set(prevState, prevAction, value);

        }

//        System.out.println(distanceToPipe + " " + heightToPipe);

//        final double randomChance = epsilon * getMultiplier(prevState, prevAction);
//        if(Math.random() < epsilon) {
//            action = Action.getRandom();
//            randoms++;
//        }else {
//            action = table.argmax(currentState);
//        }


        action = table.argmax(currentState);

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
            if(Math.random() < 0.2) {
                epsilon *= 0.95;
            }
            game.reset();
            genNumber++;
            System.out.print("Generation: " + genNumber + " Max score: " + maxScore +
                    " Current score: " + score + " Randoms: " + randoms + "\r");
            randoms = 0;
            this.prevState = null;
            this.prevAction = null;

            if(genNumber % 10000 == 0) {
                this.table.save(genNumber);
            }
        }
    }

//    public double calculateReward() {
//        return bird.next() ? 20 : -1000;
//    }

    public double calculateReward(final State state, final Action action) {
        if(state.getHeightToPipe() > Pipe.spacing && action.equals(Action.JUMP)) {
            return -100;
        }
        if(state.getDistanceToPipe() < 50 && state.getHeightToPipe() < -50) {
            return -100;
        }
        return bird.next() ? 20 : -1000;
    }

//    public double calculateReward(final Move move, int moveFromLast) {
//        if(move.getAction().equals(Action.JUMP)) {
//            numberJumps--;
//            if(numberJumps == 0) {
//                return -100;
//            }
//        }
//        if(moveFromLast < 10 && moveFromLast > 0) {
//            return -1000;
//        }
//        return moveFromLast != 0 ? 15 : - 1000;
////        if(move.getAction().equals(Action.JUMP)) {
////            numberJumps --;
////        }
////        if(numberJumps == 0 || !move.getState().isAlive()) {
////            return -1000;
////        }
////        return 10;
////        if(numberJumps == 0) {
////            return -1000;
////        }
////        return 15;
//    }

    public double getMultiplier(final State state, final Action action) {
        final double number = table.getNumber(state, action);
        return 2.0 / (1.0 + Math.exp(0.1 * number));
    }

    public double getLearningRate(final State state, final Action action) {
        final double number = table.getNumber(state, action);
//        return lambda;
//        return 1.0 / (1.0 + number);
//        return 0.8;
//        System.out.println(number);
        if(number < 10000) {
            return 1.0 / (1.0 + Math.exp(0.001 * (number - 10000)));
        }else {
            return 0.001;
        }
    }

    public int getGenNumber() {
        return this.genNumber;
    }
}
