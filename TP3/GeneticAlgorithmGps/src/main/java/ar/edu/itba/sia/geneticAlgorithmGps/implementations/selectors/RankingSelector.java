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
    public List<Chromosome> select(List<Chromosome> population, int quantity) {

        if (quantity % 2 != 0 )
            quantity = quantity - 1;

        List<Chromosome> winners = new LinkedList<>();

        double total = ((population.size()+1)*population.size())/2;

        double rankingValue[] = new double[population.size()];

        for(int k = 0; k < population.size(); k++){
            rankingValue[k] = (population.size() - k)/total;
        }

        List<Chromosome> ranking = population.stream()
            .sorted((c1, c2) -> Double.compare(c2.getAptitude(), c1.getAptitude()))
            .collect(Collectors.toList());    

        int i = 0;

        while(i < quantity){

            double pickWinner = random.nextDouble();

            double accumulated = 0;

            int t = 0;

            while(accumulated + rankingValue[t] < pickWinner){
                accumulated += rankingValue[t];
                t++;
            }

            winners.add(ranking.get(t));

            i++;

        }
        return winners;
    }
}
