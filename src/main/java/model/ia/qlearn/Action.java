package model.ia.qlearn;

public enum Action {
    JUMP, NOT_JUMP;

    public static Action getRandom() {
        final Action[] actions = Action.values();
        return actions[(int) (Math.random() * actions.length)];
    }
}
