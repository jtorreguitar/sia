package ar.edu.itba.sia.geneticAlgorithmGps.implementations.replacers;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Replacer;
import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Selector;
import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SecondReplacer implements Replacer {

    @Override
    public List<Chromosome> replace(List<Chromosome> population, List<Chromosome> children, List<Chromosome> selected) {
        List<Chromosome> newGen = new LinkedList<>();
        List<Chromosome> subpopulation = new LinkedList<>(population);
        subpopulation.removeAll(selected);
        newGen.addAll(children);
        newGen.addAll(subpopulation);

        return newGen;
    }
}
