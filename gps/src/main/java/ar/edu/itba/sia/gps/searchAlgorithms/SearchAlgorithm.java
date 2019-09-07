package ar.edu.itba.sia.gps.searchAlgorithms;

import ar.edu.itba.sia.gps.GPSNode;

import java.util.List;

public interface SearchAlgorithm {
    void findSolution(List<GPSNode> candidates, List<GPSNode> borderNodes);
    String toString();
}
