package model.ia.qlearn;

public class Move {

    private final State state;
    private final Action action;

    public Move(final State state, final Action action) {
        this.state = state;
        this.action = action;
    }

    public State getState() {
        return state;
    }

    public Action getAction() {
        return action;
    }
}
