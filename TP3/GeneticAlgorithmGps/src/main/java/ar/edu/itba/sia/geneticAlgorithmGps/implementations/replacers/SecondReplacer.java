package ar.edu.itba.sia.geneticAlgorithmGps.implementations.replacers;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Replacer;
import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Selector;
import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.LinkedList;
import java.util.List;

public class SecondReplacer implements Replacer {

    List<Selector> algos;
    int quant1;
    int quant2;

    public SecondReplacer(List<Selector> selectionAlgorithms, int quant1){
        this.algos = selectionAlgorithms;
        this.quant1 = quant1;
        //this.quant2 = quant2;
    }

    @Override
    public List<Chromosome> replace(List<Chromosome> population, List<Chromosome> children, List<Chromosome> selected) {
        List<Chromosome> newGen = new LinkedList<>();
        double percent1 = ((double)quant1) / population.size();
        //double percent2 = ((double)quant2) / population.size();
        System.out.println("population: "+ population.size());
        System.out.println("1.replacing "+quant1+" percent "+percent1);
        //System.out.println("2.replacing "+quant2+" percent "+percent2);
        newGen.addAll(children);
        //List<Chromosome> selection = new LinkedList<>(population);
        //selection es N-K donde K son los padres seleccionados para mutar
        //selection.removeAll(selected);
        int k = selected.size();
        int nMinK = population.size() - k;
        newGen.addAll(algos.get(0).select(population, (int) Math.floor( percent1 * nMinK ) ));
        newGen.addAll(algos.get(1).select(population, (int) Math.floor( (1-percent1) * nMinK ) ));


        return newGen;
    }
}
