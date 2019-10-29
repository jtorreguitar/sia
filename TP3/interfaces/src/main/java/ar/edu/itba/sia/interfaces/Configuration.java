package ar.edu.itba.sia.interfaces;

import ar.edu.itba.sia.interfaces.enums.*;

import java.util.List;
import java.util.Random;

public interface Configuration {
    /**
     * Selection
     */
    List<SelectorType> getSelectors();
    List<Double> getSelectionPercentages();

    /**
     * cross over
     */
    CrosserType getCrosser();

    /**
     * mutation
     */
    double getMutationRate();
    MutatorType getMutator();

    /**
     * replacement
     */
    ReplacerType getReplacer();
    List<SelectorType> getReplacementSelectors();
    List<Double> getReplacementPercentages();

    /**
     * stop condition
     */
    boolean stopConditionIsMet(final StoppingData stoppingData);

    /**
     * random for ensuring constant seed if needed.
     */
    Random getRandom();
}
