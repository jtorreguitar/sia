package ar.edu.itba.sia.geneticAlgorithmGps.implementations.replacers;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Replacer;
import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Selector;
import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.LinkedList;
import java.util.List;

public class ThirdReplacer implements Replacer {

    List<Selector> algos;
    int quant1;
    int quant2;

    public ThirdReplacer(List<Selector> selectionAlgorithms, int quant1, int quant2){
        this.algos = selectionAlgorithms;
        this.quant1 = quant1;
        this.quant2 = quant2;
    }

    @Override
    public List<Chromosome> replace(List<Chromosome> population, List<Chromosome> children, List<Chromosome> selected) {
        List<Chromosome> newGen = new LinkedList<>();
        double percent1 = ((double)quant1) / population.size();
        double percent2 = ((double)quant2) / population.size();
        int k = selected.size();
        int nMinK = population.size() - k;
        List<Chromosome> selection = new LinkedList<>(population);
        selection.addAll(children);
        newGen.addAll(algos.get(0).select(population, (int) Math.floor( percent1 * nMinK ) ));
        newGen.addAll(algos.get(1).select(population, (int) Math.floor(percent2  * nMinK ) ));
        newGen.addAll(algos.get(0).select(selection, (int) Math.floor( percent1 * k )));
        newGen.addAll(algos.get(1).select(selection, (int) Math.floor( percent2 * k ) ));

        return newGen;
    }
}