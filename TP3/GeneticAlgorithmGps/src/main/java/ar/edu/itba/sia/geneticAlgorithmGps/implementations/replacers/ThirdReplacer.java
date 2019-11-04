package ar.edu.itba.sia.geneticAlgorithmGps.implementations.replacers;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Replacer;
import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Selector;
import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.LinkedList;
import java.util.List;

public class ThirdReplacer implements Replacer {

    List<Selector> algos;

    public ThirdReplacer(List<Selector> selectionAlgorithms){
        this.algos = selectionAlgorithms;
    }

    @Override
    public List<Chromosome> replace(List<Chromosome> population, List<Chromosome> children, List<Chromosome> selected) {
        List<Chromosome> newGen = new LinkedList<>();
        List<Chromosome> selection = new LinkedList<>(population);
        selection.addAll(children);
        newGen.addAll(algos.get(0).select(population));
        newGen.addAll(algos.get(1).select(population));
        newGen.addAll(algos.get(0).select(selection));
        newGen.addAll(algos.get(1).select(selection));

        return newGen;
    }
}
