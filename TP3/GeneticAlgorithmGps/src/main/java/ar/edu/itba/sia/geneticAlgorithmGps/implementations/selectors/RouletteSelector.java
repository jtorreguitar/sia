package ar.edu.itba.sia.geneticAlgorithmGps.implementations.selectors;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Selector;
import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.*;

public class RouletteSelector implements Selector {

    private Random random;

    public RouletteSelector (Random random){
        this.random = random;
    }

    @Override
    public List<Chromosome> select(List<Chromosome> population, int quantity) {
       if (quantity % 2 != 0 )
            quantity = quantity - 1;

        List<Chromosome> winners = new LinkedList<>();

        double totalFitness = 0;
        for (Chromosome c : population)
            totalFitness += c.getAptitude();

        int i = 0;

        while(i < quantity){

            i++;

            double pickWinner = random.nextDouble();;

            double accumulated = 0;

            int t = 0;

            while(accumulated + (population.get(t).getAptitude())/totalFitness < pickWinner){
                accumulated += (population.get(t).getAptitude())/totalFitness;
                t++;
            }

            winners.add(population.get(t));

        }
        return winners;
    }
}
