package ar.edu.itba.sia.geneticAlgorithmGps;

import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.*;
import ar.edu.itba.sia.interfaces.Chromosome;
import ar.edu.itba.sia.interfaces.Configuration;
import ar.edu.itba.sia.interfaces.enums.ReplacerType;
import ar.edu.itba.sia.interfaces.enums.SelectorType;

import javax.management.AttributeNotFoundException;
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
    private Integer generations = 0;

    public GeneticAlgorithmGpsEngine(final List<Chromosome> population, final Configuration configuration)
                                        throws AttributeNotFoundException {
        this.population = population;
        this.configuration = configuration;
        ConfigurationParser configurationParser = new ConfigurationParser();

        selectors = configurationParser.determineSelectors(configuration);
        replacer = configurationParser.determineReplacer(configuration);
        crosser = configurationParser.determineCrosser(configuration);
        mutator = configurationParser.determineMutator(configuration);
    }

    //TODO: Cuando tomamos los parametros, si hacemos FULLREPLACE el pctg de reemplazo debe ser 100, sino puede ser cualquiera.
    public void solve() {
        while(!configuration.stopConditionIsMet(getCondition())) {
            final List<Chromosome> selected = select(population);
            final List<Chromosome> children = cross(selected);
            final List<Chromosome> mutatedChildren = mutate(children);
            final List<Chromosome> newPopulation = replacer.replace(population, mutatedChildren, selected);
            updateMetrics(newPopulation);
            population = newPopulation;
        }
    }

    private double getCondition() {
        switch (configuration.getStopCondition()) {
            case CONTENT: return repeatIndividuals.get(repeatIndividuals.size() - 1).doubleValue();
            case STRUCTURE:
            case OPTIMUM: return fittestIndividualForEachGeneration.get(fittestIndividualForEachGeneration.size() - 1).getAptitude();
            case GENERATIONS: return generations.doubleValue();
            default: throw new IllegalArgumentException("invalid stop condition");
        }
    }

    private List<Chromosome> select(final List<Chromosome> population) {
        return selectors.stream().flatMap(s -> s.select(population).stream())
                                .collect(Collectors.toList());
    }

    // TODO: checkear bien como se seleccionan los padres porque si el nro aca no es par cagamo.
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
        fittestIndividualForEachGeneration.add(newPopulation.stream().min((c1, c2) -> (int) (c1.getAptitude() - c2.getAptitude())).get());
        repeatIndividuals.add(new Long(newPopulation.stream().filter(c -> !population.contains(c)).count()).intValue());
        generations++;
    }
}
