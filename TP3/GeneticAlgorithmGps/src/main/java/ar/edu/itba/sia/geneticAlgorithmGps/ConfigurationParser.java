package ar.edu.itba.sia.geneticAlgorithmGps;

import ar.edu.itba.sia.geneticAlgorithmGps.implementations.crossers.AnnularCrosser;
import ar.edu.itba.sia.geneticAlgorithmGps.implementations.crossers.TwoPointCrosser;
import ar.edu.itba.sia.geneticAlgorithmGps.implementations.crossers.UniformCrosser;
import ar.edu.itba.sia.geneticAlgorithmGps.implementations.crossers.SinglePointCrosser;
import ar.edu.itba.sia.geneticAlgorithmGps.implementations.mutators.MultiGeneMutator;
import ar.edu.itba.sia.geneticAlgorithmGps.implementations.mutators.SingleGeneMutator;
import ar.edu.itba.sia.geneticAlgorithmGps.implementations.replacers.FullReplacer;
import ar.edu.itba.sia.geneticAlgorithmGps.implementations.replacers.SecondReplacer;
import ar.edu.itba.sia.geneticAlgorithmGps.implementations.replacers.ThirdReplacer;
import ar.edu.itba.sia.geneticAlgorithmGps.implementations.selectors.EliteSelector;
import ar.edu.itba.sia.geneticAlgorithmGps.implementations.selectors.RouletteSelector;
import ar.edu.itba.sia.geneticAlgorithmGps.implementations.selectors.BoltzmannRouletteSelector;
import ar.edu.itba.sia.geneticAlgorithmGps.implementations.selectors.ProbabilisticTournamentSelector;
import ar.edu.itba.sia.geneticAlgorithmGps.implementations.selectors.DeterministicTournamentSelector;
import ar.edu.itba.sia.geneticAlgorithmGps.implementations.selectors.RankingSelector;
import ar.edu.itba.sia.geneticAlgorithmGps.implementations.selectors.UniversalSelector;
import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Crosser;
import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Mutator;
import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Replacer;
import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Selector;
import ar.edu.itba.sia.interfaces.Configuration;
import ar.edu.itba.sia.interfaces.enums.SelectorType;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/* package */ class ConfigurationParser {

    /* package */ List<Selector> determineSelectors(final Configuration configuration) {
        return IntStream.range(0, configuration.getSelectors().size())
                        .mapToObj(i -> determineSelector(configuration.getSelectors().get(i),
                                                            configuration.getRandom(),
                                                            configuration.getSelectionCompetitors().get(i)))
                        .collect(Collectors.toList());
    }

    /* package */ Selector determineSelector(SelectorType selectorType, Random random, Integer competitors) {
        switch (selectorType) {
            case ELITE: return new EliteSelector();
            case ROULETTE: return new RouletteSelector(random);
            case PTOURNAMENT: return new ProbabilisticTournamentSelector(random);
            case DTOURNAMENT: return new DeterministicTournamentSelector(random, competitors);
            case UNIVERSAL: return new UniversalSelector(random);
            case RANKING: return new RankingSelector(random);
            case BOLTZMANN: return new BoltzmannRouletteSelector(random);
            default: throw new IllegalArgumentException("invalid selector provided");
        }
    }

    /* package */ List<Selector> determineSelectorsForReplacer(final Configuration configuration) {
        return IntStream.range(0, configuration.getSelectors().size())
                .mapToObj(i -> determineSelector(configuration.getReplacementSelectors().get(i),
                        configuration.getRandom(),
                        configuration.getReplacementCompetitors().get(i)))
                .collect(Collectors.toList());
    }

    //TODO no se si el constructor del third replacer deberia manejarse de otra manera
    /* package */ Replacer determineReplacer(Configuration configuration) {
        switch (configuration.getReplacer()) {
            case FULL_REPLACEMENT: return new FullReplacer(determineSelectorsForReplacer(configuration));
            case SECOND: return new SecondReplacer(determineSelectorsForReplacer(configuration),
                                                    configuration.getReplacementQuantities().get(0));
            case THIRD: return new ThirdReplacer( determineSelectorsForReplacer(configuration),
                                                    configuration.getReplacementQuantities().get(0));
            default: throw new IllegalArgumentException("invalid replacer provided");
        }
    }

    /* package */ Crosser determineCrosser(Configuration configuration) {
        switch (configuration.getCrosser()) {
            case SINGLE_POINT: return new SinglePointCrosser(configuration.getRandom());
            case TWO_POINT: return new TwoPointCrosser(configuration.getRandom());
            case UNIFORM: return new UniformCrosser(configuration.getRandom());
            case ANNULAR: return new AnnularCrosser(configuration.getRandom());
            default: throw new IllegalArgumentException("invalid crosser provided");
        }
    }

    /* package */ Mutator determineMutator(Configuration configuration) {
        switch (configuration.getMutator()) {
            case SINGLE_GENE: return new SingleGeneMutator(configuration.getMutationRate(), configuration.getRandom());
            case MULTI_GENE: return new MultiGeneMutator(configuration.getMutationRate(), configuration.getRandom());
            default: throw new IllegalArgumentException("invalid mutator provided");
        }
    }
}
