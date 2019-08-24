package ar.edu.itba.sia.GPS.searchAlgorithms;

import ar.edu.itba.sia.GPS.GPSNode;

import java.util.Comparator;
import java.util.List;

public class GreedySearch implements SearchAlgorithm {
    @Override
    public void findSolution(List<GPSNode> candidates, List<GPSNode> borderNodes) {
        Comparator<GPSNode> comp = (n1, n2) -> ( n1.getCost() - n2.getCost() );

        new CriteriaSearch().findSolution(candidates, borderNodes, comp);
    }


}
