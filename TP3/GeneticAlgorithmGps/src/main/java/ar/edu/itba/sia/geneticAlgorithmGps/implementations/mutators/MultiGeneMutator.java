package ar.edu.itba.sia.geneticAlgorithmGps.implementations.mutators;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Mutator;
import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MultiGeneMutator implements Mutator {

    private final Random random;
    private double mutationRate;

    public MultiGeneMutator(final double mutationRate, final Random random) {
        this.mutationRate = mutationRate;
        this.random = random;
    }

    @Override
    public Chromosome mutate(Chromosome chromosome) {
        return chromosome.mutate(IntStream.range(0, chromosome.getAlleleCount())
                                        .filter(i -> random.nextDouble() < mutationRate)
                                        .boxed()
                                        .collect(Collectors.toList()));
    }

    @Override
    public double getMutationRate() {
        return mutationRate;
    }

    @Override
    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }
}
