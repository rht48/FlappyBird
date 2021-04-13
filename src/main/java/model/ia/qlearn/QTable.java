package model.ia.qlearn;

import java.util.HashMap;
import java.util.Map;

public class QTable {

    private final Map<State, Map<Action, Double>> table;
    private final Map<State, Map<Action, Integer>> counter;

    public QTable() {
        this.table = new HashMap<>();
        this.counter = new HashMap<>();
    }

    public double get(final State state, final Action action) {
        if(table.containsKey(state)) {
            final Map<Action, Double> map = this.table.get(state);
            if(map.containsKey(action)) {
                return map.get(action);
            }
        }
        return 0.0;
    }

    public Action argmax(final State state) {
        if(table.containsKey(state)) {
            double max = -Double.MAX_VALUE;
            Action a = Action.NOT_JUMP;
            for(var action : Action.values()) {
                final double val = get(state, action);
//                System.out.print(action + " " + val + " ");
                if(val > max) {

                    max = val;
                    a = action;
                }
            }
//            System.out.println();
            return a;
        }
        return Action.NOT_JUMP;
    }

    public double max(final State state) {
        if(table.containsKey(state)) {
            double max = 0;
            for(var action : Action.values()) {
                final double val = get(state, action);
                if(val > max) {
                    max = val;
                }
            }
            return max;
        }
        return Double.MIN_VALUE;
    }

    public void set(final State state, final Action action, final double value) {
        if(!this.table.containsKey(state)) {
            this.table.put(state, new HashMap<>());
            this.counter.put(state, new HashMap<>());
        }
        this.table.get(state).put(action, value);
        if(!this.counter.get(state).containsKey(action)) {
            this.counter.get(state).put(action, 0);
        }
        this.counter.get(state).put(action, this.counter.get(state).get(action) + 1);
    }

    public void print() {
        for(var state : table.keySet()) {
            for(var action : table.get(state).keySet()) {
                System.out.print(state + " " + action + " : " + table.get(state).get(action) + " ");
            }
            System.out.println();
        }
    }

    public int getNumber(final State state, final Action action) {
        if(this.counter.containsKey(state) && this.counter.get(state).containsKey(action)) {
            return counter.get(state).get(action);
        }
        return 0;
    }
}
