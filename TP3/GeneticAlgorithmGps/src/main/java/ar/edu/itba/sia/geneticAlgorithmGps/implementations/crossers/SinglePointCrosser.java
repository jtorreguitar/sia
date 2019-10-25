package ar.edu.itba.sia.geneticAlgorithmGps.implementations.crossers;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Crosser;
import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.List;
import java.util.Random;

public class SinglePointCrosser implements Crosser {

    private Random random;

    public SinglePointCrosser(final Random random) {
        this.random = random;
    }

    @Override
    public List<Chromosome> cross(final Chromosome c1, final Chromosome c2) {
        return c1.cross(random.nextInt(c1.getAlleleCount()), c1.getAlleleCount(), c2);
    }
}
