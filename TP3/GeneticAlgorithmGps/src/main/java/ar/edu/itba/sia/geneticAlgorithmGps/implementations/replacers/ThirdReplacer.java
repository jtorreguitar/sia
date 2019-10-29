package ar.edu.itba.sia.geneticAlgorithmGps.implementations.replacers;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Replacer;
import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Selector;
import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.LinkedList;
import java.util.List;

public class ThirdReplacer implements Replacer {

    Selector selectionAlgorithm;

    public ThirdReplacer(Selector selectionAlgorithm){
        this.selectionAlgorithm = selectionAlgorithm;
    }


    @Override
    public List<Chromosome> replace(List<Chromosome> population, List<Chromosome> children, List<Chromosome> selected) {
        List<Chromosome> newGen = new LinkedList<>();
        List<Chromosome> pool = new LinkedList<>(population);
        pool.addAll(children);
        List<Chromosome> prevGen = new LinkedList<>(population);
        prevGen.removeAll(selected);
        newGen.addAll(prevGen);
        List<Chromosome> selection = selectionAlgorithm.select(pool);
        newGen.addAll(selection);

        return newGen;
    }
}
