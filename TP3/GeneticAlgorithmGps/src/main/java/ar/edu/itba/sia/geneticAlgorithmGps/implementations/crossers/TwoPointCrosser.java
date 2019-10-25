package ar.edu.itba.sia.geneticAlgorithmGps.implementations.crossers;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Crosser;
import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.List;
import java.util.Random;

public class TwoPointCrosser implements Crosser {

    private final Random random;

    public TwoPointCrosser(Random random) {
        this.random = random;
    }

    @Override
    public List<Chromosome> cross(Chromosome c1, Chromosome c2) {
        int greaterAllele = 1 + random.nextInt(c1.getAlleleCount());
        int lowerAllele = random.nextInt(greaterAllele);
        return c1.cross(lowerAllele, greaterAllele, c2);
    }
}
