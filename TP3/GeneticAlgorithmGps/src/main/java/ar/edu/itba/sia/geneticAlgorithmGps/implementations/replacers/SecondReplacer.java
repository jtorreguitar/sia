package ar.edu.itba.sia.geneticAlgorithmGps.implementations.replacers;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Replacer;
import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Selector;
import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.LinkedList;
import java.util.List;

public class SecondReplacer implements Replacer {

    List<Selector> algos;

    public SecondReplacer(List<Selector> selectionAlgorithms){
        this.algos = selectionAlgorithms;
    }

    @Override
    public List<Chromosome> replace(List<Chromosome> population, List<Chromosome> children, List<Chromosome> selected) {
        List<Chromosome> newGen = new LinkedList<>();
        newGen.addAll(children);
        List<Chromosome> selection = population;
        // selection es N-K donde K son los padres seleccionados para mutar
        population.removeAll(selected);
        newGen.addAll(algos.get(0).select(selection));
        newGen.addAll(algos.get(1).select(selection));

        return newGen;
    }
}
