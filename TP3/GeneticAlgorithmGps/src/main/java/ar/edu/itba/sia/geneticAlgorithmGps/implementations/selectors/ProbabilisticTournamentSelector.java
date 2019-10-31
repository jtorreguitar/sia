package ar.edu.itba.sia.geneticAlgorithmGps.implementations.selectors;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Selector;
import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.*;


public class ProbabilisticTournamentSelector implements Selector {

    private Random random;

    public ProbabilisticTournamentSelector(Random random) {
        this.random = random;
    }

    @Override
    public List<Chromosome> select(List<Chromosome> population, int k) {
       
        if (k % 2 != 0 )
            k = k - 1;

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
