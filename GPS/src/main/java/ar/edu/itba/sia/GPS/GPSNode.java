package ar.edu.itba.sia.GPS;

import ar.edu.itba.sia.interfaces.Heuristic;
import ar.edu.itba.sia.interfaces.Rule;
import ar.edu.itba.sia.interfaces.State;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.Optional;

public class GPSNode {

    private State state;
    private Integer cost;
    private Integer depth;
    private double heuristicValue;
    private Rule rule;
    private GPSNode parent;

    public GPSNode(State initialState, Heuristic h) {
        state = initialState;
        cost = Integer.valueOf(0);
        depth = Integer.valueOf(0);
        heuristicValue = h.getValue(initialState);
    }

    public GPSNode(State state, Integer depth, Integer cost, double heuristic, Rule rule,
                   GPSNode parent) {
        this.state = state;
        this.depth = depth + 1;
        this.cost = cost + rule.getCost();
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

    public Integer getDepth() {
        return depth;
    }

    public Integer getCost() {
        return cost;
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


}
