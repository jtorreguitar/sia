package ar.edu.itba.sia.GPS;

import java.util.*;

import ar.edu.itba.sia.GPS.eightpuzzle.E8Problem;
import ar.edu.itba.sia.GPS.eightpuzzle.E8State;
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
            case BFS: this.algorithm = new BFSAlgorithm(); break;
            case DFS: this.algorithm = new DFSAlgorithm(); break;
            case IDDFS: this.algorithm = new IterativeDeepeningSearch(); break;
            case GREEDY: this.algorithm = new GreedySearch(); break;
            case ASTAR: this.algorithm = new AStarSearch(); break;
        }
    }

    public void findSolution(Problem p) {
        Heuristic defaultHeuristic = t -> 0;
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
        Map<GPSNode, Integer> minNodeHeight = new HashMap<>();


        try {
            while (!p.isGoal(currentState)) {
                List<Rule> rulesToApply = p.getRules();

                borderNodes.remove(0);

                if(algorithm.getClass() == IterativeDeepeningSearch.class){
                    List<GPSNode> candidates = idsExpand(rulesToApply, currentNode, h, minNodeHeight);
                        ((IterativeDeepeningSearch)algorithm).idsSolution(candidates, borderNodes,minNodeHeight);
                        if( borderNodes.size() == 0){
                            currentState = p.getInitState();
                            currentNode = new GPSNode(currentState, h);
                            borderNodes.add(currentNode);
                            allNodes.add(currentNode);
                        }

                }
                else{
                    List<GPSNode> candidates = expand(rulesToApply, currentNode, h);


                    algorithm.findSolution(candidates, borderNodes);

                }

               currentNode = borderNodes.get(0);
               currentState = currentNode.getState();
            }
            cost = metricGenerator.computeMetrics(allNodes.size(), borderNodes.size(), currentNode);
            setTestVariables(false, currentNode);
            if( bestCosts.containsKey(currentNode) && bestCosts.get(currentNode) < cost ) {
                // do nothing
            }
            else
                bestCosts.put(currentNode,cost);
        }

        catch (IndexOutOfBoundsException e) {
            System.out.println("El estado inicial no tiene solución");
            setTestVariables(true, null);
        } catch (OutOfMemoryError e) {
            System.out.println("El estado inicial no tiene solución");
            setTestVariables(true, null);
        }
    }

    private List<GPSNode> idsExpand(List<Rule> toApply, GPSNode currentNode, Heuristic heuristic, Map<GPSNode, Integer> minNodeHeight) {

        LinkedList<GPSNode> candidates = new LinkedList<>();
        explosionCounter++;

        State currentState = currentNode.getState();
        for (Rule r : toApply) {
            Optional<State> state = r.apply(currentState);
            if ( state.isPresent() ) {
                State newState = state.get();
                GPSNode newNode = new GPSNode(newState, currentNode.getDepth(), currentNode.getCost(),
                        heuristic.getValue(newState), r, currentNode);
                candidates.add(newNode);
                allNodes.add(newNode);
            }
        }

        if ( candidates.size() == 0 && !allNodes.contains(currentNode) ) {
            allNodes.add(currentNode);
        }
        return candidates;
    }

    public List<GPSNode> expand(List<Rule> toApply, GPSNode currentNode, Heuristic heuristic) {

        LinkedList<GPSNode> candidates = new LinkedList<>();
        explosionCounter++;

        State currentState = currentNode.getState();
        //System.out.println("rules to apply:" + toApply.size());
        for (Rule r : toApply) {
            Optional<State> state = r.apply(currentState);
            if ( state.isPresent() ) {
                State newState = state.get();
                GPSNode newNode = new GPSNode(newState, currentNode.getDepth(), currentNode.getCost(),
                        heuristic.getValue(newState), r, currentNode);
                if (!allNodes.contains(newNode)) {
                    candidates.add(newNode);
                    allNodes.add(newNode);
                } else
                    metricGenerator.repHit();
            }
        }
        if ( candidates.size() == 0 && !allNodes.contains(currentNode) ) {
            allNodes.add(currentNode);
        }
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

    /* package */ Set<GPSNode> getBestCosts() {
        return this.allNodes;
    }

    /* package */ List<GPSNode> getOpen() {
        return this.borderNodes;
    }
}
