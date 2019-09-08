package ar.edu.itba.sia.gps;

import ar.edu.itba.sia.interfaces.Heuristic;
import ar.edu.itba.sia.interfaces.Rule;
import ar.edu.itba.sia.interfaces.State;


public class GPSNode {

    private final State state;
    private final Integer cost;
    private final Integer depth;
    private final Integer heuristicValue;
    private GPSNode parent;

    public GPSNode(State initialState, Heuristic h) {
        state = initialState;
        cost = 0;
        depth = 0;
        heuristicValue = h.getValue(initialState);
    }

    public GPSNode(State state, Integer depth, Integer cost, Integer heuristic, GPSNode parent) {
        this.state = state;
        this.depth = depth;
        this.cost = cost;
        this.heuristicValue = heuristic;
        this.parent = parent;
    }

    public GPSNode getParent() {
        return parent;
    }

    public State getState() {
        return state;
    }

    public Integer getDepth() {
        return depth;
    }

    public Integer getCost() {
        return cost;
    }

    public Integer getHeuristicValue() {
        return heuristicValue;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GPSNode that = (GPSNode) o;

        return this.getState().equals(that.getState());
    }

    @Override
    public int hashCode() {
        return this.getState().hashCode();
    }


}
