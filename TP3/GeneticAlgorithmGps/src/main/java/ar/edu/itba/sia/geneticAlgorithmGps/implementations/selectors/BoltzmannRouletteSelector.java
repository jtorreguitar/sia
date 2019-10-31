package ar.edu.itba.sia.geneticAlgorithmGps.implementations.selectors;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Selector;
import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.*;

public class BoltzmannRouletteSelector implements Selector {

    private Random random;
    private double temperature;
    private double slope;
    private int x;

    public BoltzmannRouletteSelector(Random random, double temperature, double slope) {
        this.random = random;
        this.temperature = temperature;
        this.slope = slope;
        this.x = 1;
    }

    @Override
    public List<Chromosome> select(List<Chromosome> population, int k) {

        //debe ser par
        if (k % 2 != 0 )
            k = k - 1;

        List<Chromosome> winners = new LinkedList<>();

        double boltzmannValue[] = new double[k];

        for (int j = 0; j < k; j++){
            boltzmannValue[j] = Math.pow(Math.E, (population.get(j).getAptitude())*temperature);
        }

        double totalBoltzmann = Arrays.stream(boltzmannValue).sum();
        double averageBoltzmann = totalBoltzmann/k; 
        
        for (int j = 0; j < k; j++){
            boltzmannValue[j] = boltzmannValue[j]/averageBoltzmann;
        }

        int i = 0;

        while(i < k){

            double accumulated = 0;

            double pickWinner = random.nextDouble();

            int t = 0;

            while(accumulated + boltzmannValue[t]/totalBoltzmann < pickWinner){
                accumulated += boltzmannValue[t]/totalBoltzmann;
                t++;
            }

            winners.add(population.get(t));

        }
        temperature = Math.pow(Math.E, -1*slope*x);
        x ++;
        return winners;
    }
}