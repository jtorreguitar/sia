package ar.edu.itba.sia.geneticAlgorithmGps;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.*;
import ar.edu.itba.sia.interfaces.Chromosome;
import ar.edu.itba.sia.interfaces.Configuration;
import ar.edu.itba.sia.interfaces.StoppingData;
import ar.edu.itba.sia.interfaces.enums.ReplacerType;
import ar.edu.itba.sia.interfaces.enums.SelectorType;

import javax.management.AttributeNotFoundException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class GeneticAlgorithmGpsEngine {

    private List<Chromosome> population;

    private final Configuration configuration;

    /**
     * genetic operators
     */
    private final List<Selector> selectors;
    private final Crosser crosser;
    private final Mutator mutator;
    private final Replacer replacer;


    /**
     * metric params
     */
    private final List<Chromosome> fittestIndividualForEachGeneration = new LinkedList<>();
    private final List<Integer> repeatIndividuals = new LinkedList<>();
    private final List<Double> meanFitness = new LinkedList<>();
    private final List<Double> mutationRates = new LinkedList<>();
    private Integer generations = 0;
    private GeneticAlgorithmStoppingData stoppingData;

    /**
     * Grapher
     */
    private Grapher graph = new Grapher();

    public GeneticAlgorithmGpsEngine(final List<Chromosome> population, final Configuration configuration) {
        this.population = population;
        this.configuration = configuration;
        ConfigurationParser configurationParser = new ConfigurationParser();
        selectors = configurationParser.determineSelectors(configuration);
        replacer = configurationParser.determineReplacer(configuration);
        crosser = configurationParser.determineCrosser(configuration);
        mutator = configurationParser.determineMutator(configuration);
        stoppingData = new GeneticAlgorithmStoppingData();
    }

    //TODO: Cuando tomamos los parametros, si hacemos FULLREPLACE el pctg de reemplazo debe ser 100, sino puede ser cualquiera.
    public Metrics solve() {
        do {
            final List<Chromosome> selected = select(population);
            final List<Chromosome> children = cross(selected);
            final List<Chromosome> mutatedChildren = mutate(children);
            final List<Chromosome> newPopulation = replacer.replace(population, mutatedChildren, selected);
            updateMutationRate(mutator);
            updateMetrics(newPopulation);
            population = newPopulation;
        }
        while(!configuration.stopConditionIsMet(stoppingData));
        return new Metrics(repeatIndividuals, fittestIndividualForEachGeneration, meanFitness, mutationRates);
    }

    private List<Chromosome> select(final List<Chromosome> population) {
        List<Chromosome> selected = new LinkedList<>();
        for(int i = 0; i < selectors.size(); i++)
            selected.addAll(selectors.get(i).select(population, configuration.getSelectionQuantities().get(i)));
        return selected;
    }

    private List<Chromosome> cross(final List<Chromosome> selected) {
        List<Chromosome> children = new LinkedList<>();
        for (int i = 0; i < selected.size(); i += 2)
            children.addAll(crosser.cross(selected.get(i), selected.get(i + 1)));
        return children;
    }

    private List<Chromosome> mutate(final List<Chromosome> children) {
        return children.stream().map(mutator::mutate).collect(Collectors.toList());
    }

    private void updateMetrics(final List<Chromosome> newPopulation) {
        Chromosome fittestIndividual = newPopulation.stream().max(Comparator.comparing(Chromosome::getAptitude)).get();
        graph.updateChart(fittestIndividual.getAptitude());
        stoppingData.setBestAptitude(fittestIndividual.getAptitude());
        fittestIndividualForEachGeneration.add(fittestIndividual);
        stoppingData.setRepeatIndividuals(new Long(newPopulation.stream().filter(c -> population.contains(c)).count()).intValue());
        repeatIndividuals.add(stoppingData.getRepeatIndividuals());
        generations++;
        stoppingData.setGenerations(generations);
        stoppingData.setBestAptitudeIncrease(fittestIndividualForEachGeneration.size() > 1 ?
                                            fittestIndividual.getAptitude() - fittestIndividualForEachGeneration.get(fittestIndividualForEachGeneration.size() - 2).getAptitude() :
                                            fittestIndividual.getAptitude());
        meanFitness.add(newPopulation.stream().mapToDouble(c -> c.getAptitude()).average().getAsDouble());
        mutationRates.add(mutator.getMutationRate());
    }

    private void updateMutationRate(Mutator mutator) {
        mutator.setMutationRate(mutator.getMutationRate()*Math.pow(Math.E, -configuration.getMutationRateChangeRate()));
    }
}
