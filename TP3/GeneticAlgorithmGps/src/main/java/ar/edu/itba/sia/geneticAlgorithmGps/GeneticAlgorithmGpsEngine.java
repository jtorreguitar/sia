package ar.edu.itba.sia.geneticAlgorithmGps;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.*;
import ar.edu.itba.sia.interfaces.Chromosome;
import ar.edu.itba.sia.interfaces.Configuration;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class GeneticAlgorithmGpsEngine {

    private List<Chromosome> population;

    /**
     * configuration
     */
    private final ConfigurationParser configurationParser = new ConfigurationParser();
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
    private Integer generations = 0;

    public GeneticAlgorithmGpsEngine(List<Chromosome> population, Configuration configuration) {
        this.population = population;
        this.configuration = configuration;
        selectors = configurationParser.determineSelectors(configuration.getSelectors(), configuration.getSelectionPercentages());
        replacer = configurationParser.determineReplacer(configuration.getReplacer(), configuration.getReplacementSelectors(), configuration.getReplacementPercentages());
        crosser = configurationParser.determineCrosser(configuration.getCrosser());
        mutator = configurationParser.determineMutator(configuration.getMutator(), configuration.getMutationRate());
    }

    public void solve() {
        while(!configuration.stopConditionIsMet(getCondition())) {
            final List<Chromosome> selected = select(population);
            final List<Chromosome> children = cross(selected);
            final List<Chromosome> mutatedChildren = mutate(children);
            final List<Chromosome> newPopulation = replacer.replace(population, mutatedChildren);
        }
    }

    private List<Chromosome> select(List<Chromosome> population) {
        List<Chromosome> selected = new LinkedList<>();
        selectors.forEach(s -> selected.addAll(s.select(population)));
        return selected;
    }

    // TODO: checkear bien como se seleccionan los padres porque si el nro aca no es par cagamo.
    private List<Chromosome> cross(List<Chromosome> selected) {
        List<Chromosome> children = new LinkedList<>();
        for (int i = 0; i < selected.size(); i += 2)
            children.addAll(crosser.cross(selected.get(i), selected.get(i + 1)));
        return children;
    }

    private List<Chromosome> mutate(List<Chromosome> children) {
        return children.stream().map(mutator::mutate).collect(Collectors.toList());
    }

    private double getCondition() {
        switch (configuration.getStopCondition()) {
            case CONTENT: return repeatIndividuals.get(repeatIndividuals.size() - 1).doubleValue();
            case STRUCTURE:
            case OPTIMUM: return fittestIndividualForEachGeneration.get(repeatIndividuals.size() - 1).getAptitude();
            case GENERATIONS: return generations.doubleValue();
            default: throw new IllegalArgumentException("invalid stop condition");
        }
    }
}
