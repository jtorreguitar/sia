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
    private final List<Selector> replacerSelectors;
    private final Crosser crosser;
    private final Mutator mutator;
    private final Replacer replacer;

    private Selector selectAlgorithmA;
    private Selector selectAlgorithmB;

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

        double replacementPercent = configurationParser.getReplacementPercent();
        double selectionPercent = configurationParser.getSelectionPercent();
        int selectionCant = configurationParser.getSelectionCant();
        int selectionCantA = (int) Math.floor( selectionCant * selectionPercent );
        int selectionCantB = selectionCant - selectionCantA;

        replacer = configurationParser.determineReplacer(configuration);
        int replacementCant = 0;
        switch ( ReplacerType.getReplacementMethod(replacer.toString()) ){
            case SECOND:
                replacementCant = population.size() - selectionCant;
                break;
            case THIRD:
                replacementCant = population.size();
                break;
        }

        int replacementCantA = (int) Math.floor(replacementPercent * replacementCant);
        int replacementCantB = replacementCant - replacementCantA;

        selectors = configurationParser.determineSelectors(configuration);
        Selector selectionAlgorithmA = selectors.get(0);
        Selector selectionAlgorithmB = selectors.get(1);

        replacerSelectors = configurationParser.determineSelectorsForReplacer(configuration);
        Selector selectionReplacementAlgorithmA = replacerSelectors.get(0);
        Selector selectionReplacementAlgorithmB = replacerSelectors.get(1);

        crosser = configurationParser.determineCrosser(configuration);
        mutator = configurationParser.determineMutator(configuration);
    }

    public void solve() {
        while(!configuration.stopConditionIsMet(getCondition())) {
            final List<Chromosome> selected = select(population);
            final List<Chromosome> children = cross(selected);
            final List<Chromosome> mutatedChildren = mutate(children);
            final List<Chromosome> newPopulation = replacer.replace(population, mutatedChildren,
                                                                        selectAlgorithmA, selectAlgorithmB);
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
