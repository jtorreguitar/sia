package ar.edu.itba.sia.GPS.searchAlgorithms;

import ar.edu.itba.sia.GPS.GPSNode;
import ar.edu.itba.sia.GPS.SearchStrategy;
import ar.edu.itba.sia.interfaces.Problem;
import ar.edu.itba.sia.interfaces.Rule;
import ar.edu.itba.sia.interfaces.State;

import java.util.List;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Optional;

public class DFSAlgorithm implements SearchAlgorithm {

    @Override
    public void findSolution(List<GPSNode> candidates, List<GPSNode> borderNodes) {
        borderNodes.addAll(0, candidates);
    }
}
