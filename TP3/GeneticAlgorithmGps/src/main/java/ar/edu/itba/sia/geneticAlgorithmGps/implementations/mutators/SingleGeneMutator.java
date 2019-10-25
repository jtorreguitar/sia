package ar.edu.itba.sia.geneticAlgorithmGps.implementations.mutators;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Mutator;
import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class SingleGeneMutator implements Mutator {

    private final Random random;
    private double mutationRate;

    public SingleGeneMutator(final double mutationRate, final Random random) {
        this.mutationRate = mutationRate;
        this.random = random;
    }

    @Override
    public Chromosome mutate(final Chromosome chromosome) {
        return chromosome.mutate(Arrays.asList(random.nextInt(chromosome.getAlleleCount())));
    }

    @Override
    public double getMutationRate() {
        return mutationRate;
    }

    @Override
    public void setMutationRate(final double mutationRate) {
        this.mutationRate = mutationRate;
    }
}
