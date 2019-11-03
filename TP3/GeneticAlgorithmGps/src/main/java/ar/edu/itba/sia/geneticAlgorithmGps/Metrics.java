package ar.edu.itba.sia.geneticAlgorithmGps;

import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.List;
import java.util.stream.Collectors;

public class Metrics {

    private List<Integer> repeatIndividuals;
    private List<Double> fittestIndividualForEachGeneration;
    private List<Double> meanFitness;
    private List<Double> mutationRates;

    public Metrics(List<Integer> repeatIndividuals, List<Chromosome> fittestIndividualForEachGeneration, List<Double> meanFitness, List<Double> mutationRates) {
        this.repeatIndividuals = repeatIndividuals;
        this.fittestIndividualForEachGeneration = fittestIndividualForEachGeneration.stream().mapToDouble(c -> c.getAptitude()).boxed().collect(Collectors.toList());
        this.meanFitness = meanFitness;
        this.mutationRates = mutationRates;
    }

    public List<Integer> getRepeatIndividuals() {
        return repeatIndividuals;
    }

    public List<Double> getFittestIndividualForEachGeneration() {
        return fittestIndividualForEachGeneration;
    }

    public List<Double> getMeanFitness() {
        return meanFitness;
    }

    public List<Double> getMutationRates() {
        return mutationRates;
    }
}
