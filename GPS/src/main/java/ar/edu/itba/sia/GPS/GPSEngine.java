package ar.edu.itba.sia.GPS;

import java.util.Map;
import java.util.Deque;
import java.util.LinkedList;
import java.util.HashMap;

import ar.edu.itba.sia.interfaces.*;

public class GPSEngine {

	private final Deque<GPSNode> open = new LinkedList<>();
	private final Map<State, Integer> bestCosts = new HashMap<>();
	private final Problem problem;
	private long explosionCounter = 0;
	private boolean finished = false;
	private boolean failed = false;
	private GPSNode solutionNode;
	private final Heuristic heuristic;

	protected SearchStrategy strategy;

	public GPSEngine(final Problem problem, final SearchStrategy strategy, final Heuristic heuristic) {
        this.problem = problem;
        this.strategy = strategy;
        this.heuristic = heuristic;
	}

	public void findSolution() {
	}

}
