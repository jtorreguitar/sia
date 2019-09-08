package ar.edu.itba.sia.gps.searchAlgorithms;

import ar.edu.itba.sia.gps.GPSNode;

import java.util.Comparator;
import java.util.List;

public class AStarSearch implements SearchAlgorithm {
    @Override
    public boolean findSolution(List<GPSNode> candidates, List<GPSNode> borderNodes) {
        Comparator<GPSNode> comparator = Comparator.comparingInt(n -> (n.getCost() + n.getHeuristicValue()));
        new CriteriaSearch().findSolution(candidates, borderNodes, comparator);
        return false;
    }

    @Override
    public String toString() {
        return "ASTAR";
    }
}
