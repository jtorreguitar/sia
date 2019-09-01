package ar.edu.itba.sia.GPS;

import java.util.*;

import ar.edu.itba.sia.GPS.searchAlgorithms.*;
import ar.edu.itba.sia.interfaces.*;

public class GPSEngine {

    // test variables
    private long explosionCounter;
    private boolean isFailed;
    private boolean isFinished;
    private GPSNode solutionNode;
    private SearchStrategy strategy;
    private Map<GPSNode, Double> bestCosts;

    // useful variables
    private List<GPSNode> borderNodes;
    private Set<GPSNode> allNodes;
    private SearchAlgorithm algorithm;
    private Set<GPSNode> stopNodes;

    private Metrics metricGenerator = Metrics.getInstance();

    public GPSEngine(SearchStrategy strategy) {
        this.strategy = strategy;
        borderNodes = new LinkedList<>();
        allNodes = new HashSet<>();
        bestCosts = new HashMap<>();
        stopNodes = new HashSet<>();
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

        Double cost;
        State currentState = p.getInitState();
        GPSNode currentNode = new GPSNode(currentState, h);
        borderNodes.add(currentNode);
        allNodes.add(currentNode);

        try {
            while (!p.isGoal(currentState)) {
                List<Rule> rulesToApply = p.getRules();

                borderNodes.remove(0);

                List<GPSNode> candidates = expand(rulesToApply, currentNode, h);

                //System.out.println("CHANGE");

                algorithm.findSolution(candidates, borderNodes);

                //System.out.println("border nodes size> " + borderNodes.size());
                //System.out.println("all nodes size>" + allNodes.size());

                currentNode = borderNodes.get(0);
                currentState = currentNode.getState();
            }
            cost = metricGenerator.computeMetrics(allNodes.size(), borderNodes.size(), currentNode);
            if( bestCosts.containsKey(currentNode) && bestCosts.get(currentNode) < cost ) {
                // do nothing
            }
            else
                bestCosts.put(currentNode,cost);
            setTestVariables(true, currentNode);
        }

        catch (IndexOutOfBoundsException e) {
            System.out.println("El estado inicial no tiene solución");
            setTestVariables(false, null);
        }
    }

    public List<GPSNode> expand(List<Rule> toApply, GPSNode currentNode, Heuristic heuristic) {

        LinkedList<GPSNode> candidates = new LinkedList<>();

        State currentState = currentNode.getState();
        int counter = 0;
        //System.out.println("rules to apply:" + toApply.size());
        for (Rule r : toApply) {
            Optional<State> state = r.apply(currentState);
            if (state.isPresent() != false) {
                State newState = state.get();
                GPSNode newNode = new GPSNode(newState, currentNode.getDepth() + r.getCost(),
                        heuristic.getValue(newState), r, currentNode);
                //System.out.println(allNodes.contains(newNode) ? "is present" : "not present");
                if (!allNodes.contains(newNode)) {
                    candidates.add(newNode);
                    allNodes.add(newNode);
                } else
                    metricGenerator.repHit();
            }
            counter++;
            //System.out.println("applied rule:" + counter + " of " + toApply.size());
        }
        //System.out.println("candidate added : " + candidates.size());

        if ( candidates.size() == 0 && !allNodes.contains(currentNode) ) {allNodes.add(currentNode);}
        return candidates;
    }

    private void setTestVariables(boolean isFailed, GPSNode solutionNode) {
        this.explosionCounter = allNodes.size() - borderNodes.size();
        this.isFinished = true;
        this.isFailed = isFailed;
        this.solutionNode = solutionNode;
    }

    /* package */ long getExplosionCounter() {
        return this.explosionCounter;
    }

    /* package */ boolean isFailed() {
        return this.isFailed;
    }

    /* package */ boolean isFinished() {
        return this.isFinished;
    }

    /* package */ GPSNode getSolutionNode() {
        return this.solutionNode;
    }

    /* package */ SearchStrategy getStrategy() {
        return this.strategy;
    }

    /* package */ Map<GPSNode, Double> getBestCosts() {
        return this.bestCosts;
    }

    /* package */ List<GPSNode> getOpen() {
        return this.borderNodes;
    }
}
