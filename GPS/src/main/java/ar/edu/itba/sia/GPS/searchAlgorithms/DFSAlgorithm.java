package ar.edu.itba.sia.GPS.searchAlgorithms;

import ar.edu.itba.sia.GPS.GPSNode;
import ar.edu.itba.sia.interfaces.Heuristic;
import ar.edu.itba.sia.interfaces.Problem;
import ar.edu.itba.sia.interfaces.Rule;
import ar.edu.itba.sia.interfaces.State;

import java.util.*;

public class DFSAlgorithm implements SearchAlgorithm {
    @Override
    public void findSolution(List<GPSNode> candidates, List<GPSNode> borderNodes) {
        borderNodes.addAll(0, candidates);
    }
}
