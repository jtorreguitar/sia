package ar.edu.itba.sia.GPS.searchAlgorithms;

import ar.edu.itba.sia.GPS.GPSNode;

import java.util.Comparator;
import java.util.List;

public class AStarSearch implements SearchAlgorithm {
    @Override
    public void findSolution(List<GPSNode> candidates, List<GPSNode> borderNodes) {

        Comparator<GPSNode> comparator = Comparator.comparingInt(n -> ((int) n.getCost() + (int) n.getHeuristicValue()));

        new CriteriaSearch().findSolution(candidates, borderNodes, comparator);
    }

    @Override
    public String toString() {
        return "ASTAR";
    }
}
