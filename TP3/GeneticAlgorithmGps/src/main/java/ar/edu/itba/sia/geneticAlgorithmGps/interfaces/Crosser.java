package ar.edu.itba.sia.geneticAlgorithmGps.interfaces;

import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.List;

@FunctionalInterface
public interface Crosser {
    List<Chromosome> cross(final Chromosome c1, final Chromosome c2);
}
