package ar.edu.itba.sia.geneticAlgorithmGps.implementations.selectors;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Selector;
import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Collectors;



public class RankingSelector implements Selector {

    private Random random;

    public RankingSelector(Random random) {
        this.random = random;
    }

    @Override
    public List<Chromosome> select(List<Chromosome> population, int k) {

        if (k % 2 != 0 )
            k = k - 1;

        List<Chromosome> winners = new LinkedList<>();

        int total = (IntStream.range(1, population.size())).sum();

        List<Chromosome> ranking = population.stream()
            .sorted((c1, c2) -> Double.compare(c2.getAptitude(), c1.getAptitude()))
            .collect(Collectors.toList());    

        int i = 0;

        while(i < k){

            double accumulated = 0;

            double pickWinner = random.nextDouble();

            int t = 0;

            while((accumulated + (k-t)/total) < pickWinner){
                accumulated += (k-t)/total;
                t++;
            }

            winners.add(population.get(t));

            i++;

        }
        return winners;
    }
}