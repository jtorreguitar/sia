package ar.edu.itba.sia.geneticAlgorithmGps.implementations.selectors;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Selector;
import ar.edu.itba.sia.interfaces.Chromosome;
import javafx.util.Pair;

import java.util.*;

public class RouletteSelector implements Selector {

    Random r;

    public RouletteSelector (Random r){
        this.r = r;
    }

    public List<Chromosome> select(List<Chromosome> chromosomes, int cant) {

        int selectionCant = cant;

        //debe ser par
        if (selectionCant % 2 != 0 )
            selectionCant= selectionCant - 1;


        List<Chromosome> selected = new LinkedList<>();
        double accumToMatch[] = new double[selectionCant];

        for (int i = 0 ; i < selectionCant; i++) {
            double random = r.nextDouble();
            accumToMatch[i] = random;
        }

        double totalFitness = 0;
        for (Chromosome c : chromosomes)
            totalFitness += c.getAptitude();

        // armar la ruleta
        // Cada pair es el chromosome y el maximo double de su rango
        List<Pair<Chromosome,Double>> roulette = new ArrayList<>();
        double prevChromosomeAccum = 0;
        for(int t = 0 ; t < chromosomes.size() ; t ++ ){
            Chromosome currentChromosome = chromosomes.get(t);
            double currentChromosomeAccum = prevChromosomeAccum + (currentChromosome.getAptitude() / totalFitness);
            roulette.add(new Pair<>(currentChromosome, currentChromosomeAccum));
            prevChromosomeAccum = currentChromosomeAccum;
        }

        int j = 0;
        for (int i = 0; i < accumToMatch.length ; i ++) {
            if( accumToMatch[i] < roulette.get(j).getValue() ){
                selected.add( roulette.get(j).getKey() );
                i++;
                j = 0;
            } else {
                j++;
            }
        }

        return selected;
    }
}
