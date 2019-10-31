package ar.edu.itba.sia.geneticAlgorithmGps.implementations.selectors;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Selector;
import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BoltzmannRouletteSelector implements Selector {

    private double selectionPercentage;
    private Random random;
    private int temperature;
    private int slope;
    private int x;

    public BoltzmannRouletteSelector(double selectionPercentage, Random random, int temperature, int slope) {
        this.selectionPercentage = selectionPercentage;
        this.random = random;
        this.temperature = temperature;
        this.slope = slope;
        this.x = 1;
    }

    @Override
    public List<Chromosome> select(List<Chromosome> population) {

        int selectionCant = (int) Math.floor( selectionPercentage * population.size() );

        //debe ser par
        if (selectionCant % 2 != 0 )
            selectionCant= selectionCant - 1;

        List<Chromosome> winners = new LinkedList<>();

        double boltzmannValue[] = new double[selectionCant];

        for (int i = 0; i < selectionCant; i++){
            boltzmannValue[i] = Math.pow(Math.E, population.get(i))*temperature);
        }

        double totalBoltzmann = Arrays.stream(boltzmannValue).sum();
        double averageBoltzmann = totalBoltzmann/k; 
        boltzmannValue = Arrays.stream(boltzmannValue).map(v -> v/averageBoltzmann).collect(Collectors.toAR);

        while(i < k){

            double accumulated = 0;

            double pickWinner = random.nextDouble();

            for(t = 0; (accumulated + boltzmannValue[t]/totalBoltzmann) < pickWinner; t++){
                accumulated += boltzmannValue[t]/totalBoltzmann;
            }

            winners.add(population.get(t));

        }
        temperature = Math.pow(Math.E, -1*n*x);
        x ++;
        return winners;
    }
}