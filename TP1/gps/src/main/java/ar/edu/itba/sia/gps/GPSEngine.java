package ar.edu.itba.sia.gps;

import java.util.*;

import ar.edu.itba.sia.gps.searchAlgorithms.*;
import ar.edu.itba.sia.interfaces.*;

public class GPSEngine {

    // test variables
    private long explosionCounter;
    private boolean isFailed;
    private boolean isFinished;
    private GPSNode solutionNode;
    private SearchStrategy strategy;
    private Map<GPSNode, Double> bestCosts;
    private Heuristic heuristic;
    private Problem problem;

    // useful variables
    private List<GPSNode> borderNodes;
    private Set<GPSNode> allNodes;
    private SearchAlgorithm algorithm;

    private Metrics metricGenerator = Metrics.getInstance();

    public GPSEngine(SearchStrategy strategy) {
        this.strategy = strategy;
        borderNodes = new LinkedList<>();
        allNodes = new HashSet<>();
        bestCosts = new HashMap<>();
        switch (strategy) {
            case BFS: this.algorithm = new BFSAlgorithm(); break;
            case DFS: this.algorithm = new DFSAlgorithm(); break;
            case IDDFS: this.algorithm = new IterativeDeepeningSearch(); break;
            case GREEDY: this.algorithm = new GreedySearch(); break;
            case ASTAR: this.algorithm = new AStarSearch(); break;
        }
    }

    public GPSEngine(Problem problem, SearchStrategy strategy, Heuristic heuristic) {
        this(strategy);
        this.problem = problem;
        this.heuristic = heuristic;
    }

    public void findSolution(Problem p) {
        Heuristic defaultHeuristic = t -> 0;
        this.genericSearch(p, defaultHeuristic);
    }

    public void findSolution(Problem p, Heuristic h) {
        heuristic = h;
        this.genericSearch(p, h);
    }

    public void findSolution() {
        if(heuristic == null) findSolution(problem);
        else findSolution(problem, heuristic);
    }

    private void genericSearch(Problem p, Heuristic h) {

        Double cost;
        State currentState = p.getInitState();
        GPSNode startNode = new GPSNode(currentState, h);
        GPSNode currentNode = startNode;
        borderNodes.add(currentNode);
        allNodes.add(currentNode);
        int previouslyExplored = 0;

        try {
            while (!p.isGoal(currentState)) {
                List<Rule> rulesToApply = p.getRules();

                borderNodes.remove(0);

                List<GPSNode> candidates = expand(rulesToApply, currentNode, h);

                if(algorithm.findSolution(candidates, borderNodes)) {
                    if(previouslyExplored == allNodes.size()) {
                        setFailed();
                        return;
                    }
                    previouslyExplored = resetSearch(startNode);
                }

                currentNode = borderNodes.get(0);
                currentState = currentNode.getState();
            }

            setSucceeded(currentNode);
        }
        catch (IndexOutOfBoundsException e) {
            setFailed();
        }
    }

    private int resetSearch(GPSNode startNode) {
        int previouslyExplored;
        previouslyExplored = allNodes.size();
        borderNodes.add(startNode);
        allNodes.clear();
        return previouslyExplored;
    }

    private void setFailed() {
        metricGenerator.computeMetrics(this);
        System.out.println("El estado inicial no tiene solución");
        setTestVariables(true, null);
    }

    private void setSucceeded(GPSNode currentNode) {
        setTestVariables(false, currentNode);
        Double cost;
        cost = metricGenerator.computeMetrics(this);
        if (!(bestCosts.containsKey(currentNode) && bestCosts.get(currentNode) < cost))
            bestCosts.put(currentNode, currentNode.getCost().doubleValue());
    }

    private List<GPSNode> expand(List<Rule> toApply, GPSNode currentNode, Heuristic heuristic) {
        explosionCounter++;
        LinkedList<GPSNode> candidates = new LinkedList<>();

        State currentState = currentNode.getState();
        for (Rule r : toApply) {
            Optional<State> state = r.apply(currentState);
            if (state.isPresent()) {
                State newState = state.get();
                GPSNode newNode = new GPSNode(newState, currentNode.getDepth() + 1, currentNode.getCost() + r.getCost(),
                        heuristic.getValue(newState), currentNode, r);
                if (!allNodes.contains(newNode) && noBetterCostFound(newNode)){
                    allNodes.add(newNode);
                    bestCosts.put(newNode, newNode.getCost().doubleValue());
                    candidates.add(newNode);
                } else
                    metricGenerator.repHit();
            }
        }
        return candidates;
    }

    private boolean noBetterCostFound(GPSNode newNode) {
        return bestCosts.get(newNode) == null || bestCosts.get(newNode) >= newNode.getCost().doubleValue();
    }

    private void setTestVariables(boolean isFailed, GPSNode solutionNode) {
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

    /* package */ Set<GPSNode> getBestCosts() {
        return this.allNodes;
    }

    /* package */ List<GPSNode> getOpen() {
        return this.borderNodes;
    }

    /* package */ Heuristic getHeuristic() {
        return this.heuristic;
    }
}
