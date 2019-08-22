package ar.edu.itba.sia.GPS.searchAlgorithms;

import ar.edu.itba.sia.GPS.GPSNode;
import ar.edu.itba.sia.interfaces.Problem;

import java.util.Deque;
import java.util.LinkedList;

public class SearchAlgorithmFactory {

    public static SearchAlgorithm newBFS(Problem problem) {
        final Deque<GPSNode> deque = new LinkedList<>();
        return new CollectionBasedAlgorithm(problem, deque, deque::pollFirst, deque::offerLast);
    }

    public static SearchAlgorithm newDFS(Problem problem) {
        final Deque<GPSNode> deque = new LinkedList<>();
        return new CollectionBasedAlgorithm(problem, deque, deque::pop, deque::push);
    }
}
