package ar.edu.itba.sia.geneticAlgorithmGps.implementations.selectors;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Selector;
import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Collectors;



public class RankingSelector implements Selector {

    private Random random;
    private int quantity;

    public RankingSelector(Random random, int quantity) {
        this.random = random;
        this.quantity = quantity;
    }

    @Override
    public List<Chromosome> select(List<Chromosome> population) {

        if (quantity % 2 != 0 )
            quantity = quantity - 1;

        List<Chromosome> winners = new LinkedList<>();

        int total = (IntStream.range(1, population.size())).sum();

        List<Chromosome> ranking = population.stream()
            .sorted((c1, c2) -> Double.compare(c2.getAptitude(), c1.getAptitude()))
            .collect(Collectors.toList());    

        int i = 0;

        while(i < quantity){

            double accumulated = 0;

            double pickWinner = random.nextDouble();

            int t = 0;

            while((accumulated + (quantity-t)/total) < pickWinner){
                accumulated += (quantity-t)/total;
                t++;
            }

            winners.add(population.get(t));

            i++;

        }
        return winners;
    }
}
