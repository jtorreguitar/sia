package ar.edu.itba.sia.geneticAlgorithmGps.implementations.replacers;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Replacer;
import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Selector;
import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.LinkedList;
import java.util.List;

public class FullReplacer implements Replacer {

    List<Selector> algos;
    int quant;

    public FullReplacer(List<Selector> selectionAlgorithms, int quant){

        this.algos = selectionAlgorithms;
        this.quant = quant;
    }

    @Override
    public List<Chromosome> replace(List<Chromosome> population, List<Chromosome> children, List<Chromosome> selected) {
        List<Chromosome> newGen = new LinkedList<>();
        double percent = ((double)quant) / population.size();
        // Selection es toda la generacion t MENOS los padres mutados MAS los hijos
        List<Chromosome> selection = population;
        selection.removeAll(selected);
        selection.addAll(children);
        newGen.addAll(algos.get(0).select(selection, (int) Math.floor( population.size() * percent ) ));
        newGen.addAll(algos.get(1).select(selection, (int) Math.floor( population.size() * (1-percent) ) ));
        return newGen;
    }
}
