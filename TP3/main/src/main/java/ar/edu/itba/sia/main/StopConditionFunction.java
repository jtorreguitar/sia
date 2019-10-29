package ar.edu.itba.sia.main;

import ar.edu.itba.sia.interfaces.StoppingData;

@FunctionalInterface
public interface StopConditionFunction {
    boolean stopConditionIsMet(StoppingData stoppingData);
}
