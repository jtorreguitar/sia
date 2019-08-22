package ar.edu.itba.sia.GPS.searchAlgorithms;

import ar.edu.itba.sia.GPS.GPSNode;
import ar.edu.itba.sia.GPS.SearchStrategy;
import ar.edu.itba.sia.interfaces.Problem;
import ar.edu.itba.sia.interfaces.Rule;
import ar.edu.itba.sia.interfaces.State;

import java.util.Stack;
import java.util.LinkedList;
import java.util.Optional;

public class DFSAlgorithm implements SearchAlgorithm {

    private final Stack<GPSNode> open = new Stack<>();
    private final Problem problem;
    private GPSNode solutionNode;

    public DFSAlgorithm(Problem problem) {
        this.problem = problem;
    }

    @Override
    public void findSolution() {
        final GPSNode initialNode = new GPSNode(problem.getInitState(), 0, null);
        if(problem.isGoal(problem.getInitState())) {
            solutionNode = initialNode;
            return;
        }
        open.push(initialNode);
        while(!open.isEmpty()) {
            final GPSNode currentNode = open.pop();
            if(problem.isGoal(currentNode.getState())) {
                solutionNode = currentNode;
                return;
            }
            for(Rule rule : problem.getRules()) {
                final Optional<State> maybeState = rule.apply(currentNode.getState());
                if(maybeState.isPresent()) {
                    final State currentState = maybeState.get();
                    open.push(new GPSNode(currentState, currentNode.getCost() + rule.getCost(), rule));
                }
            }
        }
    }

    @Override
    public String toString() {
        return SearchStrategy.BFS.toString();
    }
}
