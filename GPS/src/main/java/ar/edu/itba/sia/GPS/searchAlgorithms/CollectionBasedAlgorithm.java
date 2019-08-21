package ar.edu.itba.sia.GPS.searchAlgorithms;

import ar.edu.itba.sia.GPS.GPSNode;
import ar.edu.itba.sia.GPS.SearchStrategy;
import ar.edu.itba.sia.interfaces.Problem;
import ar.edu.itba.sia.interfaces.Rule;
import ar.edu.itba.sia.interfaces.State;

import java.util.Deque;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CollectionBasedAlgorithm implements SearchAlgorithm {

    private final Deque<GPSNode> open;
    private final Supplier<GPSNode> getter;
    private final Consumer<GPSNode> adder;
    private final Problem problem;
    private GPSNode solutionNode;

        public CollectionBasedAlgorithm(Problem problem,
                                        Deque<GPSNode> open,
                                        Supplier<GPSNode> getter,
                                        Consumer<GPSNode> adder) {
            this.open = open;
            this.getter = getter;
            this.adder = adder;
            this.problem = problem;
    }

    @Override
    public void findSolution() {
        final GPSNode initialNode = new GPSNode(problem.getInitState(), 0, null);
        if(problem.isGoal(problem.getInitState())) {
            solutionNode = initialNode;
            return;
        }
        adder.accept(initialNode);
        while(!open.isEmpty()) {
            final GPSNode currentNode = getter.get();
            if(problem.isGoal(currentNode.getState())) {
                solutionNode = currentNode;
                return;
            }
            for(Rule rule : problem.getRules()) {
                final Optional<State> maybeState = rule.apply(currentNode.getState());
                if(maybeState.isPresent()) {
                    final State currentState = maybeState.get();
                    adder.accept(new GPSNode(currentState, currentNode.getCost() + rule.getCost(), rule));
                }
            }
        }
    }

    public GPSNode getSolutionNode() {
        return solutionNode;
    }
}
