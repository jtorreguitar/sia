package ar.edu.itba.sia.geneticAlgorithmGps.implementations.selectors;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Selector;
import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.*;

public class BoltzmannRouletteSelector implements Selector {

    private Random random;
    private int x;
    private int quantity;

    public BoltzmannRouletteSelector(Random random, int quantity) {
        this.random = random;
        this.x = 1;
        this.quantity = quantity;
    }

    @Override
    public List<Chromosome> select(List<Chromosome> population) {

        //debe ser par
        if (quantity % 2 != 0 )
            quantity = quantity - 1;

        List<Chromosome> winners = new LinkedList<>();

        double boltzmannValue[] = new double[quantity];

        double temperature = 1/(0.002*x + 1);

        for (int j = 0; j < quantity; j++){
            boltzmannValue[j] = Math.pow(Math.E, (population.get(j).getAptitude())*temperature);
        }

        double totalBoltzmann = Arrays.stream(boltzmannValue).sum();
        double averageBoltzmann = totalBoltzmann/quantity;
        
        for (int j = 0; j < quantity; j++){
            boltzmannValue[j] = boltzmannValue[j]/averageBoltzmann;
        }

        int i = 0;

        while(i < quantity){

            double accumulated = 0;

            double pickWinner = random.nextDouble();

            int t = 0;

            while(accumulated + boltzmannValue[t]/totalBoltzmann < pickWinner){
                accumulated += boltzmannValue[t]/totalBoltzmann;
                t++;
            }

            winners.add(population.get(t));

        }
        x ++;
        return winners;
    }
}
