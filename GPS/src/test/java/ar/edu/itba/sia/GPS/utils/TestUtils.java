package ar.edu.itba.sia.GPS.utils;

import ar.edu.itba.sia.GPS.GPSEngine;
import ar.edu.itba.sia.interfaces.Heuristic;
import ar.edu.itba.sia.interfaces.Problem;

public class TestUtils {

    public static void runEngineTiming(Problem problem, GPSEngine engine, String name, Heuristic heuristic){
        System.out.println("Finding " + name + " solution");
        long start = System.currentTimeMillis();
        engine.findSolution(problem, heuristic != null ? heuristic : t -> 0);
        double time = (System.currentTimeMillis()- start)/ 1000.0;
        System.out.println(name + " time "+ time + "s");
    }
}
