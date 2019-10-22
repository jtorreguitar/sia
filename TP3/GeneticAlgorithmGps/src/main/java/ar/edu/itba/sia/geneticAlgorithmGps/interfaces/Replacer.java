package ar.edu.itba.sia.geneticAlgorithmGps.interfaces;

import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.List;

public interface Replacer {
    List<Chromosome> replace(List<Chromosome> population, List<Chromosome> children);
}
