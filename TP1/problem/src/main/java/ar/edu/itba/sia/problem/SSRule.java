package ar.edu.itba.sia.problem;

import java.util.Optional;

import ar.edu.itba.sia.interfaces.Rule;
import ar.edu.itba.sia.interfaces.State;

public class SSRule implements Rule {

    private static final int SINGLE_MOVE_COST = 1;

    private final int squareId;

    public SSRule(int squareId) {
        this.squareId = squareId;
    }

    @Override
    public Integer getCost() {
        return SINGLE_MOVE_COST;
    }

    @Override
    public String getName() {
        return "move " + squareId;
    }

    @Override
    public Optional<State> apply(State state) {
        if(!(state instanceof SSState))
            return Optional.empty();
        return ((SSState)state).moveSquare(squareId);
    }

    @Override
    public String toString() {
        return "move square " + squareId;
    }
}
