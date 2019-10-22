package ar.edu.itba.sia.geneticAlgorithmGps.interfaces;

import ar.edu.itba.sia.interfaces.Chromosome;

public interface Mutator {
    Chromosome mutate(Chromosome chromosome);
    double getMutationRate();
    void setMutationRate(double mutationRate);
}
