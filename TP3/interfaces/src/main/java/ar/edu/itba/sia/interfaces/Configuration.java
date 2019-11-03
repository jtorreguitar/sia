package ar.edu.itba.sia.interfaces;

import ar.edu.itba.sia.interfaces.enums.*;

import java.util.List;
import java.util.Random;

public interface Configuration {
    /**
     * Selection
     */
    List<SelectorType> getSelectors();
    List<Integer> getSelectionQuantities();
    List<Integer> getSelectionCompetitors();

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
    List<Integer> getReplacementQuantities();
    List<Integer> getReplacementCompetitors();

    /**
     * stop condition
     */
    boolean stopConditionIsMet(final StoppingData stoppingData);

    /**
     * random for ensuring constant seed if needed.
     */
    Random getRandom();
}
