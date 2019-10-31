package ar.edu.itba.sia.geneticAlgorithmGps.implementations.selectors;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Selector;
import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProbabilisticTournamentSelector implements Selector {

    private double selectionPercentage;
    private Random random;

    public ProbabilisticTournamentSelector(double selectionPercentage, Random random) {
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

        while(k > 0){
            Chromosome X = population.get((int) random.nextDouble() * (k-1));
            Chromosome Y = population.get((int) random.nextDouble() * (k-1));
            
            double pickWinner = random.nextDouble();

            double compared = Double.compare(X.getAptitude(), Y.getAptitude());

            if((pickWinner < 0.75 && compared > 0) || (pickWinner >= 0.75 && compared < 0))
                winners.add(X);
            else
                winners.add(Y);

            k--;
        }
        return winners;
    }
}
