package ar.edu.itba.sia.GPS.searchAlgorithms;

import ar.edu.itba.sia.GPS.GPSNode;

import java.util.List;

public interface SearchAlgorithm {
    void findSolution(List<GPSNode> candidates, List<GPSNode> borderNodes);
}
