package ar.edu.itba.sia.gps.searchAlgorithms;

import ar.edu.itba.sia.gps.GPSNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IterativeDeepeningSearch implements SearchAlgorithm {

    private static final int HEIGHT_INCREMENT = 10;

    private Map<GPSNode, Integer> nodesHeight;

    private int currentHeight = 0;
    private int finalHeight = HEIGHT_INCREMENT + 1;

    public IterativeDeepeningSearch() {
        nodesHeight = new HashMap<>();
    }

    @Override
    public String toString() {
        return "IDDFS";
    }

    @Override
    public void findSolution(List<GPSNode> candidates, List<GPSNode> borderNodes) {

        currentHeight++;
        for (GPSNode n : candidates)
            nodesHeight.put(n, currentHeight);

        if (currentHeight == finalHeight) {
            borderNodes.addAll(candidates);
            GPSNode nextNodeToExplore = borderNodes.get(0);
            currentHeight = nodesHeight.get(nextNodeToExplore);

            boolean expandHeight = true;
            for (GPSNode n : borderNodes) {
                if (nodesHeight.get(n) != finalHeight) {
                    expandHeight = false;
                    break;
                }
            }
            if (expandHeight)
                finalHeight += HEIGHT_INCREMENT;
        }
        else
            borderNodes.addAll(0, candidates);

    }
}
