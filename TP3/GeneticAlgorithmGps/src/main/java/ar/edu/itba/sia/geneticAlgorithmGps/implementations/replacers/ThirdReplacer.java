package ar.edu.itba.sia.geneticAlgorithmGps.implementations.replacers;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Replacer;
import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Selector;
import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.LinkedList;
import java.util.List;

public class ThirdReplacer implements Replacer {

    List<Selector> algos;
    Double percent;

    public ThirdReplacer(List<Selector> selectionAlgorithms, Double replacementPercentage){
        this.algos = selectionAlgorithms;
        this.percent = replacementPercentage;
    }

    @Override
    public List<Chromosome> replace(List<Chromosome> population, List<Chromosome> children, List<Chromosome> selected) {
        List<Chromosome> newGen = new LinkedList<>();
        int k = selected.size();
        int nMinK = population.size() - k;
        List<Chromosome> selection = population;
        selection.addAll(children);
        newGen.addAll(algos.get(0).select(population, (int) Math.floor( percent * nMinK ) ));
        newGen.addAll(algos.get(1).select(population, (int) Math.floor( (1-percent) * nMinK ) ));
        newGen.addAll(algos.get(0).select(selection, (int) Math.floor( percent * k )));
        newGen.addAll(algos.get(1).select(selection, (int) Math.floor( (1-percent) * k ) ));

        return newGen;
    }
}