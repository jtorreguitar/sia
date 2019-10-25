package ar.edu.itba.sia.geneticAlgorithmGps.interfaces;

import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.List;

@FunctionalInterface
public interface Selector {
    List<Chromosome> select(final List<Chromosome> population);
}
