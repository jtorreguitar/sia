package ar.edu.itba.sia.geneticAlgorithmGps.implementations.selectors;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Selector;
import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.*;


public class DeterministicTournamentSelector implements Selector {

    private Random random;
    private int m;
    private int quantity;

    public DeterministicTournamentSelector(Random random, int m, int quantity) {
        this.random = random;
        this.m = m;
        this.quantity = quantity;
    }

    @Override
    public List<Chromosome> select(List<Chromosome> population) {
        
        if (quantity % 2 != 0 )
            quantity = quantity - 1;
            
        List<Chromosome> winners = new LinkedList<>();

        while(quantity > 0){
            Chromosome winner = population.get((int) random.nextDouble() * (quantity-1));
            int i = 0;

            while(i < m-1){
                Chromosome candidate = population.get((int) (Math.random() * (quantity-1)));
                if(Double.compare(winner.getAptitude(), candidate.getAptitude()) < 0){
                    winner = candidate;
                }
                i ++;
            }
            winners.add(winner);
            quantity--;
        }
        return winners;
    }
}
