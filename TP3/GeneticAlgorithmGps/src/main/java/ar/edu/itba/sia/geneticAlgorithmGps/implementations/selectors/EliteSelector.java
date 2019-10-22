package ar.edu.itba.sia.geneticAlgorithmGps.implementations.selectors;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Selector;
import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.List;

public class EliteSelector implements Selector {

    private double selectionPercentage;

    public EliteSelector(double selectionPercentage) {
        this.selectionPercentage = selectionPercentage;
    }

    @Override
    public List<Chromosome> select(List<Chromosome> population) {
        return null;
    }
}
