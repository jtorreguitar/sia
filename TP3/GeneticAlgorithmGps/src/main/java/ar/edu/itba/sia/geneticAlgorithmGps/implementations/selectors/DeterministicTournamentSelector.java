package ar.edu.itba.sia.geneticAlgorithmGps.implementations.selectors;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Selector;
import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DeterministicTournamentSelector implements Selector {

    private Random random;

    public DeterministicTournamentSelector(Random random, int m) {
        this.random = random;
        this.m = m;
    }

    @Override
    public List<Chromosome> select(List<Chromosome> population, int k) {
        
        if (k % 2 != 0 )
            k = k - 1;
            
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
