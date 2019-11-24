package ar.edu.itba.sia.geneticAlgorithmGps.implementations.selectors;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Selector;
import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.*;

public class BoltzmannRouletteSelector implements Selector {

    private Random random;
    private int x;

    public BoltzmannRouletteSelector(Random random) {
        this.random = random;
        this.x = 1;
    }

    @Override
    public List<Chromosome> select(List<Chromosome> population, int quantity) {

        //debe ser par
        if (quantity % 2 != 0 )
            quantity = quantity - 1;

        List<Chromosome> winners = new LinkedList<>();

        double boltzmannValue[] = new double[population.size()];

        double temperature = 1/(0.002*x + 1);

        double totalBoltzmann = 0;

        for (int j = 0; j < population.size(); j++){
            boltzmannValue[j] = Math.pow(Math.E, (population.get(j).getAptitude())*temperature);
            totalBoltzmann += boltzmannValue[j];
        }

        double averageBoltzmann = totalBoltzmann/population.size();
        
        double totalBoltzmannFitness = 0;

        for (int k = 0; k < population.size(); k++){
            boltzmannValue[k] = boltzmannValue[k]/averageBoltzmann;
            totalBoltzmannFitness += boltzmannValue[k];
        }


        int i = 0;

        while(i < quantity){

            double accumulated = 0;

            double pickWinner = random.nextDouble();

            int t = 0;

            while(accumulated + boltzmannValue[t]/totalBoltzmannFitness < pickWinner){
                accumulated += boltzmannValue[t]/totalBoltzmannFitness;
                t++;
            }

            winners.add(population.get(t));

            i++;

        }
        x ++;
        return winners;
    }
}
