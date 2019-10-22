package ar.edu.itba.sia.geneticAlgorithmGps.implementations.mutators;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Mutator;
import ar.edu.itba.sia.interfaces.Chromosome;

public class MultiGeneMutator implements Mutator {

    private double mutationRate;

    public MultiGeneMutator(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    @Override
    public Chromosome mutate(Chromosome chromosome) {
        return null;
    }

    @Override
    public double getMutationRate() {
        return 0;
    }

    @Override
    public void setMutationRate(double mutationRate) {

    }
}
