package ar.edu.itba.sia.geneticAlgorithmGps.implementations.replacers;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Replacer;
import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Selector;
import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.LinkedList;
import java.util.List;

public class ThirdReplacer implements Replacer {


    @Override
    public List<Chromosome> replace(List<Chromosome> population, List<Chromosome> children, Selector selectionAlgorithmA, Selector selectionAlgorithmB) {
        List<Chromosome> newGen = new LinkedList<>();
        newGen.addAll(population);
        newGen.addAll(selectionAlgorithmA.select(population));
        newGen.addAll(selectionAlgorithmB.select(population));

        return newGen;
    }
}
