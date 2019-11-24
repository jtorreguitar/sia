package ar.edu.itba.sia.geneticAlgorithmGps.implementations.replacers;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Replacer;
import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Selector;
import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.LinkedList;
import java.util.List;

public class FullReplacer implements Replacer {

    List<Selector> algos;
    int quant1;
    int quant2;

    public FullReplacer(List<Selector> selectionAlgorithms, int quant1){

        this.algos = selectionAlgorithms;
        this.quant1 = quant1;
        //this.quant2 = quant2;
    }

    @Override
    public List<Chromosome> replace(List<Chromosome> population, List<Chromosome> children, List<Chromosome> selected) {
        List<Chromosome> newGen = new LinkedList<>();
        double percent1 = ((double)quant1) / population.size();
        //double percent2 = ((double)quant2) / population.size();
        // Selection es toda la generacion t MENOS los padres mutados MAS los hijos
        List<Chromosome> selection = population;
        selection.removeAll(selected);
        selection.addAll(children);
        newGen.addAll(algos.get(0).select(selection, (int) Math.floor( population.size() * percent1 ) ));
        newGen.addAll(algos.get(1).select(selection, (int) Math.floor( population.size() * (1-percent1) ) ));
        return newGen;
    }
}
