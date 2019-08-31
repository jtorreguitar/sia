package ar.edu.itba.sia.GPS;

import ar.edu.itba.sia.interfaces.Heuristic;
import ar.edu.itba.sia.interfaces.Rule;
import ar.edu.itba.sia.interfaces.State;

public class GPSNode {

    private State state;
    private double accum;
    private double heuristicValue;
    private Rule rule;
    private GPSNode parent;

    public GPSNode(State initialState, Heuristic h) {
        state = initialState;
        accum = 0;
        heuristicValue = h.getValue(initialState);
    }

    public GPSNode(State state, double accum, double heuristic, Rule rule,
                       GPSNode parent) {
        this.state = state;
        this.accum = accum;
        this.heuristicValue = heuristic;
        this.rule = rule;
        this.parent = parent;
    }

    public GPSNode getParent() {
        return parent;
    }

    public State getState() {
        return state;
    }

    public double getDepth() {
        return accum;
    }

    public double getHeuristicValue() {
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

    public Integer getCost() {
        return rule.getCost();
    }
}
