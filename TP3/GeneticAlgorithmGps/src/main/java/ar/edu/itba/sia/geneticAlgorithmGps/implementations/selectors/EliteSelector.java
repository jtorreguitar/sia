package ar.edu.itba.sia.geneticAlgorithmGps.implementations.selectors;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Selector;
import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class EliteSelector implements Selector {

    private int quantity;

    public EliteSelector(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public List<Chromosome> select(List<Chromosome> population) {
        return population.stream().sorted((c1, c2) -> Double.compare(c2.getAptitude(), c1.getAptitude()))
                                .limit(Double.valueOf(quantity).longValue())
                                .collect(Collectors.toList());
    }
}
