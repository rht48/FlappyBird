package model.ia.qlearn;

import java.util.Objects;

public class State {

    private final float distanceToPipe;
    private final float heightToPipe;
    private final float velocity;


    public State(final float distanceToPipe, final float heightToPipe, final float velocity) {
        this.distanceToPipe = distanceToPipe;
        this.heightToPipe = heightToPipe;
        this.velocity = velocity;

    }

    public float getDistanceToPipe() {
        return distanceToPipe;
    }

    public float getHeightToPipe() {
        return heightToPipe;
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final State state = (State) o;
        return  Math.abs(state.distanceToPipe - distanceToPipe) < 0.001  &&
                Math.abs(state.heightToPipe - heightToPipe) < 0.001 &&
                Math.abs(state.velocity - velocity) < 0.001;
    }

    public float getVelocity() {
        return velocity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(distanceToPipe, heightToPipe, velocity);
    }

    @Override
    public String toString() {
        return "State{" +
                "distanceToPipe=" + distanceToPipe +
                ", heightToPipe=" + heightToPipe +
                ", velocity=" + velocity +
                '}';
    }
}
