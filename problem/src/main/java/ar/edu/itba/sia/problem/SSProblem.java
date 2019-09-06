package ar.edu.itba.sia.problem;

import ar.edu.itba.sia.interfaces.Problem;
import ar.edu.itba.sia.interfaces.Rule;
import ar.edu.itba.sia.interfaces.State;

import java.util.Arrays;
import java.util.Collections;
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
        return Arrays.asList(rules);
    }

    private SSRule[] initializeRules(SSState state) {
        final LinkedList<SSRule> rules = new LinkedList<>();
        for(Square square : state.getSquares())
            rules.add(new SSRule(square.getId()));
        return rules.toArray(new SSRule[] { });
    }
}
