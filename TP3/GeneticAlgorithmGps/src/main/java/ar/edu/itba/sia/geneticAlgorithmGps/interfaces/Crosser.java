package ar.edu.itba.sia.geneticAlgorithmGps.interfaces;

import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.List;

@FunctionalInterface
public interface Crosser {
    List<Chromosome> cross(Chromosome c1, Chromosome c2);
}
