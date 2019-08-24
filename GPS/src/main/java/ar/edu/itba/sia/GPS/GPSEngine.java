package ar.edu.itba.sia.GPS;

import java.util.*;

import ar.edu.itba.sia.GPS.searchAlgorithms.BFSAlgorithm;
import ar.edu.itba.sia.GPS.searchAlgorithms.SearchAlgorithm;
import ar.edu.itba.sia.GPS.searchAlgorithms.SearchAlgorithmFactory;
import ar.edu.itba.sia.interfaces.*;

public class GPSEngine {

	private final List<GPSNode> borderNodes = new LinkedList<>();
	private final Set<GPSNode> allNodes = new HashSet<>();
	private final Map<State, Integer> bestCosts = new HashMap<>();
	private final Problem problem;
	private final Heuristic heuristic;
	private final SearchAlgorithm algorithm;

	public GPSEngine(final Problem problem, final SearchStrategy strategy, final Heuristic heuristic) {
        this.problem = problem;
        this.heuristic = heuristic;
        this.algorithm = determineAlgorithm(strategy);
	}

	public void findSolution(Rule rule) {

		State currentState = problem.getInitState();
		// Como vamos a manejar el tema de las reglas?
		GPSNode currentNode = new GPSNode(currentState, 0, rule);
		borderNodes.add(currentNode);
		allNodes.add(currentNode);

		try{
			while( !problem.isGoal(currentState) ){
				List<Rule> rules = problem.getRules();

				borderNodes.remove(0);

				List<GPSNode> candidates = expand(rules, currentNode, heuristic);

				algorithm.findSolution(candidates, borderNodes);

				currentNode = borderNodes.get(0);
				currentState = currentNode.getState();
			}

			// Aca habria que calcular la metrica o score del algoritmo?
		}

		catch (ArrayIndexOutOfBoundsException e){
			System.out.println("No tiene solucion");
		}

		catch (IllegalArgumentException e){
			System.out.println("No tiene solucion");
		}

	}

	private List<GPSNode> expand(List<Rule> rules, GPSNode currentNode, Heuristic heuristic) {

		LinkedList<GPSNode> candidates = new LinkedList<>();
		State currentState = currentNode.getState();

		for(Rule r: rules){
			State newState = r.apply(currentState).get();

			GPSNode newNode = new GPSNode(newState, r.getCost() + currentNode.getDepth(), r);
			if (!allNodes.contains(newNode)) {
				candidates.add(newNode);
				allNodes.add(newNode);
			}
			else
				System.out.println("metrica + 1");//aca habria que sumar la metrica
		}
		return candidates;
	}

	private SearchAlgorithm determineAlgorithm(SearchStrategy strategy) {
		switch (strategy) {
			case BFS: return SearchAlgorithmFactory.newBFS(problem);
			case DFS: return SearchAlgorithmFactory.newDFS(problem);
			case IDDFS: return SearchAlgorithmFactory.newIDDFS(problem);
			case GREEDY: return SearchAlgorithmFactory.newGREEDY(problem);
			case ASTAR: return SearchAlgorithmFactory.newASTAR(problem);

		}
		return null;
	}
}
