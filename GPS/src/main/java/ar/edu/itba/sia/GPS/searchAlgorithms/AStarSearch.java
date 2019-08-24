package ar.edu.itba.sia.GPS.searchAlgorithms;

import ar.edu.itba.sia.GPS.GPSNode;

import java.util.Comparator;
import java.util.List;

public class AStarSearch implements SearchAlgorithm {
    @Override
    public void findSolution(List<GPSNode> candidates, List<GPSNode> borderNodes) {
        Comparator<GPSNode> comp = (n1, n2) -> ( ( n1.getCost() + n1.getDepth() ) - ( n2.getCost() + n2.getDepth() ) );

        new CriteriaSearch().findSolution(candidates, borderNodes, comp);
    }
}
