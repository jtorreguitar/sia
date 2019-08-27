package ar.edu.itba.sia.GPS;

import java.util.*;

import ar.edu.itba.sia.GPS.searchAlgorithms.*;
import ar.edu.itba.sia.interfaces.*;

public class GPSEngine {

    private List<GPSNode> borderNodes;
    private Set<GPSNode> allNodes;
    private SearchAlgorithm algorithm;

    private Metrics metricGenerator = Metrics.getInstance();

    public GPSEngine(SearchStrategy strategy) {
        borderNodes = new LinkedList<>();
        allNodes = new HashSet<>();
        switch (strategy) {
            case BFS: this.algorithm = new BFSAlgorithm();
            case DFS: this.algorithm = new DFSAlgorithm();
            case IDDFS: this.algorithm = new IterativeDeepeningSearch();
            case GREEDY: this.algorithm = new GreedySearch();
            case ASTAR: this.algorithm = new AStarSearch();

        }
    }

    public void findSolution(Problem p) {
        Heuristic defaultHeuristic = (t) -> 0;
        this.genericSearch(p, defaultHeuristic);
    }

    public void findSolution(Problem p, Heuristic h) {
        this.genericSearch(p, h);
    }

    private void genericSearch(Problem p, Heuristic h) {

        State currentState = p.getInitState();
        GPSNode currentNode = new GPSNode(currentState, h);
        borderNodes.add(currentNode);
        allNodes.add(currentNode);

        try {
            while (!p.isGoal(currentState)) {
                List<Rule> rulesToApply = p.getRules();

                borderNodes.remove(0);

                List<GPSNode> candidates = expand(rulesToApply, currentNode, h);

                algorithm.findSolution(candidates, borderNodes);

                currentNode = borderNodes.get(0);
                currentState = currentNode.getState();
            }
            metricGenerator.computeMetrics(allNodes.size(), borderNodes.size(), currentNode);
        }

        catch (IndexOutOfBoundsException e) {
            System.out.println("El estado inicial no tiene solución");
        }
    }

    public List<GPSNode> expand(List<Rule> toApply, GPSNode currentNode, Heuristic heuristic) {

        LinkedList<GPSNode> candidates = new LinkedList<>();

        State currentState = currentNode.getState();

        for (Rule r : toApply) {
            State newState = r.apply(currentState).get();
            GPSNode newNode = new GPSNode(newState, currentNode.getAccum() + r.getCost(),
                    heuristic.getValue(newState), r, currentNode);
            if (!allNodes.contains(newNode)) {
                candidates.add(newNode);
                allNodes.add(newNode);
            }
            else
                metricGenerator.repHit();
        }
        return candidates;
    }

}
