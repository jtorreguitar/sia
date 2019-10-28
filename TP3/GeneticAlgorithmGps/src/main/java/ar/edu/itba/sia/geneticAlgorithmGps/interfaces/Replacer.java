package ar.edu.itba.sia.geneticAlgorithmGps.interfaces;

import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.List;

public interface Replacer {
    List<Chromosome> replace(final List<Chromosome> population, final List<Chromosome> children
                                , final Selector selectionAlgorithmA, final Selector selectionAlgorithmB);
}
