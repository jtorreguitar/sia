package ar.edu.itba.sia.geneticAlgorithmGps.implementations.selectors;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Selector;
import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DeterministicTournamentSelector implements Selector {

    private double selectionPercentage;
    private Random random;

    public DeterministicTournamentSelector(double selectionPercentage, Random random, int m) {
        this.selectionPercentage = selectionPercentage;
        this.random = random;
        this.m = m;
    }

    @Override
    public List<Chromosome> select(List<Chromosome> population) {
        if (Double.valueOf(Math.floor(population.size()*selectionPercentage)) % 2 == 0)
           double k = Double.valueOf(Math.floor(population.size()*selectionPercentage));
        else 
           double k = Double.valueOf(Math.ceil(population.size()*selectionPercentage));

        List<Chromosome> winners = new LinkedList<>();

        while(k > 0){
            Chromosome winner = population.get((int) random.nextDouble() * (k-1));
            int i = 0;

            while(i < m-1){
                Chromosome candidate = population.get((int) (Math.random() * (k-1)));
                if(Double.compare(winner.getAptitude(), candidate.getAptitude()) < 0){
                    winner = candidate;
                }
                i ++;
            }
            winners.add(winner);
            k--;
        }
        return winners;
    }
}
