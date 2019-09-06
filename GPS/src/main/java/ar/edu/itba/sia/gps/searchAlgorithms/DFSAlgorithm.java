package ar.edu.itba.sia.gps.searchAlgorithms;

import ar.edu.itba.sia.gps.GPSNode;

import java.util.*;

public class DFSAlgorithm implements SearchAlgorithm {
    @Override
    public void findSolution(List<GPSNode> candidates, List<GPSNode> borderNodes) {
        borderNodes.addAll(0, candidates);
    }

    @Override
    public String toString() {
        return "DFS";
    }
}
