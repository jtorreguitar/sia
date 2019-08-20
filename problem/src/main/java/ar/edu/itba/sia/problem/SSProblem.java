package ar.edu.itba.sia.problem;

import ar.edu.itba.sia.interfaces.Problem;
import ar.edu.itba.sia.interfaces.Rule;
import ar.edu.itba.sia.interfaces.State;

import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;

public class SSProblem implements Problem {

    private final SSState initialState;
    private final SSRule[] rules;

    public SSProblem(SSState initialState) {
        this.initialState = initialState;
        this.rules = initializeRules(initialState);
    }

    @Override
    public State getInitState() {
        return initialState;
    }

    @Override
    public boolean isGoal(State state) {
        if(!(state instanceof SSState))
            return false;
        return Arrays.stream(((SSState) state).getSquares())
                        .allMatch(s -> s.getPosition().equals(s.getGoal()));
    }

    @Override
    public List<Rule> getRules() {
        return null;
    }

    private SSRule[] initializeRules(SSState state) {
        final LinkedList<SSRule> rules = new LinkedList<>();
        for(int[] row : state.getBoard())
            for(int element : row)
                if(element != SSState.EMPTY_CELL)
                    rules.addAll(rulesForSquare(state.getSquare(element)));
        return rules.toArray(new SSRule[] { });
    }

    private List<SSRule> rulesForSquare(Square square) {
        final LinkedList<SSRule> rules = new LinkedList<>();
        rules.add(new SSRule(Direction.UP, square));
        rules.add(new SSRule(Direction.DOWN, square));
        rules.add(new SSRule(Direction.LEFT, square));
        rules.add(new SSRule(Direction.RIGHT, square));
        return rules;
    }
}
