package ar.edu.itba.sia.GPS.searchAlgorithms;

import ar.edu.itba.sia.GPS.GPSNode;

import java.util.Comparator;
import java.util.List;

public class AStarSearch implements SearchAlgorithm {
    @Override
    public void findSolution(List<GPSNode> candidates, List<GPSNode> borderNodes) {

        Comparator<GPSNode> comparator = (n1, n2) -> (((int) n1.getHeuristicValue() + (int) n1.getAccum())
                - ((int) n2.getHeuristicValue() + (int) n2.getAccum()));

        new CriteriaSearch().findSolution(candidates, borderNodes, comparator);
    }
}
