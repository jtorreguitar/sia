package ar.edu.itba.sia.problem;

import java.util.Optional;

import ar.edu.itba.sia.interfaces.Rule;
import ar.edu.itba.sia.interfaces.State;

public class SSRule implements Rule {

    private static final int SINGLE_MOVE_COST = 1;

    private final Direction direction;
    private final Square square;

    public SSRule(final Direction direction, final Square square) {
        this.direction = direction;
        this.square = square;
    }
    @Override
    public Integer getCost() {
        return SINGLE_MOVE_COST;
    }

    @Override
    public String getName() {
        return square.getPosition().toString() + direction.toString();
    }

    @Override
    public Optional<State> apply(State state) {
        if(!(state instanceof SSState))
            return Optional.empty();
        return ((SSState)state).moveSquare(square, direction);
    }
}
