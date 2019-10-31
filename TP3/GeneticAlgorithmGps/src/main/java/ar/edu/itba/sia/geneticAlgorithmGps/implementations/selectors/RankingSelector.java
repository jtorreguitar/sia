package ar.edu.itba.sia.geneticAlgorithmGps.implementations.selectors;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Selector;
import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.Comparator;
import java.util.List;
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

        int total = IntStream.range(1, population.size()).sum();

        List<Chromosome> ranking = population.stream().sorted((c1, c2) -> Double.compare(c2.getAptitude(), c1.getAptitude()));        

        while(i < k){

            double accumulated = 0;

            double pickWinner = random.nextDouble();

            for(t = 0; (accumulated + (k-t)/total) < pickWinner; t++){
                accumulated += (k-t)/total;
            }

            winners.add(population.get(t));
            
            i++;

        }
        return winners;
    }
}