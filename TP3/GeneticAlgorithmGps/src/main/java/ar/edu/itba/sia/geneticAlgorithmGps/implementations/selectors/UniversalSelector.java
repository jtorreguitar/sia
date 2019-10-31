package ar.edu.itba.sia.geneticAlgorithmGps.implementations.selectors;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Selector;
import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UniversalSelector implements Selector {

    private double selectionPercentage;
    private Random random;

    public UniversalSelector(double selectionPercentage, Random random) {
        this.selectionPercentage = selectionPercentage;
        this.random = random;
    }

    @Override
    public List<Chromosome> select(List<Chromosome> population) {
        if (Double.valueOf(Math.floor(population.size()*selectionPercentage)) % 2 == 0)
           double k = Double.valueOf(Math.floor(population.size()*selectionPercentage));
        else 
           double k = Double.valueOf(Math.ceil(population.size()*selectionPercentage));

        List<Chromosome> winners = new LinkedList<>();

        double totalFitness = 0;
        for (Chromosome c : chromosomes)
            totalFitness += c.getAptitude();

        double r = random.nextDouble();

        int i = 0;

        while(i < k){

            i++;

            double pickWinner = (r + i -1)/k;

            double accumulated = 0;

            for(t = 0; accumulated + ((population.get(t).getAptitude)/totalFitness) < pickWinner; t++){
                accumulated += (population.get(t).getAptitude)/totalFitness);
            }

            winners.add(population.get(t));

        }
        return winners;
    }
}
