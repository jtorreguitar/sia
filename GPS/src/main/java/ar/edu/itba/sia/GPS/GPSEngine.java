package ar.edu.itba.sia.GPS;

import java.util.Map;
import java.util.Deque;
import java.util.LinkedList;
import java.util.HashMap;

import ar.edu.itba.sia.GPS.searchAlgorithms.BFSAlgorithm;
import ar.edu.itba.sia.GPS.searchAlgorithms.SearchAlgorithm;
import ar.edu.itba.sia.interfaces.*;

public class GPSEngine {

	private final Deque<GPSNode> open = new LinkedList<>();
	private final Map<State, Integer> bestCosts = new HashMap<>();
	private final Problem problem;
	private boolean finished = false;
	private boolean failed = false;
	private GPSNode solutionNode;
	private final Heuristic heuristic;
	private final SearchAlgorithm algorithm;

	public GPSEngine(final Problem problem, final SearchStrategy strategy, final Heuristic heuristic) {
        this.problem = problem;
        this.heuristic = heuristic;
        this.algorithm = determineAlgorithm(strategy);
	}

	public void findSolution() {
		algorithm.findSolution();
	}

	private SearchAlgorithm determineAlgorithm(SearchStrategy strategy) {
		switch (strategy) {
			case BFS: return SearchAlgorithmFactory.newBFS(problem);
			case DFS: return SearchAlgorithmFactory.newDFS(problem);
		}
		return null;
	}
}
