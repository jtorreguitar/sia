package ar.edu.itba.sia.main;

import ar.edu.itba.sia.gps.GPSEngine;
import ar.edu.itba.sia.gps.SearchStrategy;
import ar.edu.itba.sia.interfaces.Heuristic;
import ar.edu.itba.sia.problem.*;

import java.io.FileNotFoundException;

public class Main {

    /**
     *
     * @param args: the first element corresponds to the search strategy, the second to the board json you want to run
     *            and the third is an optional argument corresponding to the heuristic.
     */
    public static void main(String[] args) {
        try {
            SearchStrategy strategy = SearchStrategy.valueOf(args[0]);
            GPSEngine engine = new GPSEngine(strategy);
            SSProblem problem = new SSProblem(new BoardParser().parse(args[1]));
            if(args.length > 2)
                engine.findSolution(problem, parseHeuristic(args[2]));
            else
                engine.findSolution(problem);
        }
        catch (FileNotFoundException e) {
            System.out.println("the given board file does not exist");
        }
    }

    private static Heuristic parseHeuristic(String heuristic) {
        switch (heuristic) {
            case "Distance": return new HeuristicDistance();
            case "InPlace": return new HeuristicInPlace();
            case "WeightedDistance": return new HeuristicWeightedDistance();
        }
        throw new IllegalArgumentException("There is no heuristic with the given name");
    }
}
