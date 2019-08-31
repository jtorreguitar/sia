package ar.edu.itba.sia.GPS;

import ar.edu.itba.sia.GPS.eightpuzzle.E8HeuristicB;
import ar.edu.itba.sia.GPS.eightpuzzle.E8Problem;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.MessageFormat;

import static ar.edu.itba.sia.GPS.utils.TestUtils.runEngineTiming;

public class UnitCostGPSTests {
	
	private static GPSEngine bfsEngine;
	private static GPSEngine dfsEngine;
	private static GPSEngine iddfsEngine;
	private static GPSEngine aStarEngine;
	private static GPSEngine greedyEngine;
	
	@BeforeClass
	public static void setUp(){
		E8Problem problem = new E8Problem(false);
		
		bfsEngine = new GPSEngine(SearchStrategy.BFS);
		dfsEngine = new GPSEngine(SearchStrategy.DFS);
		iddfsEngine = new GPSEngine(SearchStrategy.IDDFS);
		aStarEngine = new GPSEngine(SearchStrategy.ASTAR);
		greedyEngine = new GPSEngine(SearchStrategy.GREEDY);

		System.out.println("Running UnitCost engines");
		runEngineTiming(problem, bfsEngine, "bfs", null);
		runEngineTiming(problem, dfsEngine, "dfs", null);
		runEngineTiming(problem, iddfsEngine, "iddfs", null);
		runEngineTiming(problem, aStarEngine, "aStar", E8HeuristicB.instance());
		runEngineTiming(problem, greedyEngine, "greedy", E8HeuristicB.instance());
		System.out.println("All engine ran, running the tests");
	}
	
	@Test
	public void findSolution(){
		GenericTests.solutionFound(bfsEngine);
		GenericTests.solutionFound(dfsEngine);
		GenericTests.solutionFound(iddfsEngine);
		GenericTests.solutionFound(aStarEngine);
		GenericTests.solutionFound(greedyEngine);
	}
	
	private static void sameSolutions(GPSEngine engine1, GPSEngine engine2){
		GPSNode engine1SolutionNode = engine1.getSolutionNode();
		GPSNode engine2SolutionNode = engine2.getSolutionNode();
		assert engine1SolutionNode.getCost().equals(engine2SolutionNode.getCost()) :
			MessageFormat.format("{0} solution must have same cost as {1} solution. {0} cost: {2}, {1} cost: {3}",
				engine1.getStrategy().name(), engine2.getStrategy().name(),
				engine1SolutionNode.getCost(), engine2SolutionNode.getCost());
		assert engine1SolutionNode.getCost().equals(engine1SolutionNode.getDepth()) :
			MessageFormat.format("{0} solution must have same cost and depth. {0} cost: {1}, {0} depth: {2}",
				engine1.getStrategy(),
				engine1SolutionNode.getCost(), engine1SolutionNode.getDepth());
	}
	
	private static void hasNoSameSolution(GPSEngine optimalEngine, GPSEngine nonOptimalEngine){
		GPSNode optimalSolutionNode = optimalEngine.getSolutionNode();
		GPSNode nonOptimalSolutionNode = nonOptimalEngine.getSolutionNode();
		assert optimalSolutionNode.getCost() < nonOptimalSolutionNode.getCost() :
			MessageFormat.format("{0} solution must have less cost than {1} solution. {0} cost: {2}, {1} cost: {3}",
				optimalEngine.getStrategy().name(), nonOptimalEngine.getStrategy(),
				optimalSolutionNode.getCost(), nonOptimalSolutionNode.getCost());
	}
	
	@Test
	public void sameSolutionAStarVsBFS(){
		sameSolutions(aStarEngine,bfsEngine);
	}
	
	@Test
	public void sameSolutionAStarVsIDDFS() {
		sameSolutions(aStarEngine,iddfsEngine);
	}
	
	@Test
	public void noSameSolutionAStarvsGreedy(){
		hasNoSameSolution(aStarEngine, greedyEngine);
		
	}
	
	@Test
	public void noSameSolutionAStarVsDFS(){
		hasNoSameSolution(aStarEngine,dfsEngine);
	}
	
	@AfterClass
	public static void tearDown(){
		System.out.println("Unit cost OK");
	}
}
