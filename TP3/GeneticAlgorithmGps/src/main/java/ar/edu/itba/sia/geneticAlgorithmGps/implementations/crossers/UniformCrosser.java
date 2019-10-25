package ar.edu.itba.sia.geneticAlgorithmGps.implementations.crossers;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Crosser;
import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UniformCrosser implements Crosser {

    private static final double CROSS_RATE = 0.5;
    private final Random random;

    public UniformCrosser(final Random random) {
        this.random = random;
    }

    @Override
    public List<Chromosome> cross(final Chromosome c1, final Chromosome c2) {
        return c1.cross(indexList(c1.getAlleleCount()), c2);
    }

    private List<Integer> indexList(final int count) {
        return IntStream.range(0, count)
                        .filter(i -> random.nextDouble() < CROSS_RATE)
                        .mapToObj(Integer::valueOf)
                        .collect(Collectors.toList());
    }
}
