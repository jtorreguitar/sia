package ar.edu.itba.sia.GPS.searchAlgorithms;

import ar.edu.itba.sia.GPS.GPSNode;
import ar.edu.itba.sia.interfaces.Heuristic;
import ar.edu.itba.sia.interfaces.Problem;
import ar.edu.itba.sia.interfaces.Rule;

import java.util.List;
import java.util.Optional;
import java.util.Queue;

public interface SearchAlgorithm {
    void findSolution(List<GPSNode> candidates, List<GPSNode> borderNodes);
    String toString();
}
