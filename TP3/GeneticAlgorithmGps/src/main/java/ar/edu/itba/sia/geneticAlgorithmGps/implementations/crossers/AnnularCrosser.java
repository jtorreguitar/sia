package ar.edu.itba.sia.geneticAlgorithmGps.implementations.crossers;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Crosser;
import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.List;
import java.util.Random;

public class AnnularCrosser implements Crosser {

    private final Random random;

    public AnnularCrosser(Random random) {
        this.random = random;
    }

    @Override
    public List<Chromosome> cross(Chromosome c1, Chromosome c2) {
        return c1.cross(random.nextInt(c1.getAlleleCount()), random.nextInt(c1.getAlleleCount()), c2);
    }
}
