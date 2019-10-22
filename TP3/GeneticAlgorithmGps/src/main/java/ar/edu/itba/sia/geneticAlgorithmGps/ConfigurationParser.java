package ar.edu.itba.sia.geneticAlgorithmGps;

import ar.edu.itba.sia.geneticAlgorithmGps.implementations.mutators.MultiGeneMutator;
import ar.edu.itba.sia.geneticAlgorithmGps.implementations.mutators.SingleGeneMutator;
import ar.edu.itba.sia.geneticAlgorithmGps.implementations.replacers.FullReplacer;
import ar.edu.itba.sia.geneticAlgorithmGps.implementations.selectors.EliteSelector;
import ar.edu.itba.sia.geneticAlgorithmGps.implementations.crossers.SinglePointCrosser;
import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Crosser;
import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Mutator;
import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Replacer;
import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Selector;
import ar.edu.itba.sia.interfaces.enums.CrosserType;
import ar.edu.itba.sia.interfaces.enums.MutatorType;
import ar.edu.itba.sia.interfaces.enums.ReplacerType;
import ar.edu.itba.sia.interfaces.enums.SelectorType;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/* package */ class ConfigurationParser {

    /* package */ List<Selector> determineSelectors(List<SelectorType> selectors, List<Double> selectionPercentages) {
        return IntStream.range(0, selectors.size())
                        .mapToObj(i -> determineSelector(selectors.get(i), selectionPercentages.get(i)))
                        .collect(Collectors.toList());
    }

    /* package */ Selector determineSelector(SelectorType selectorType, double selectionPercentage) {
        switch (selectorType) {
            case ELITE: return new EliteSelector(selectionPercentage);
            default: throw new IllegalArgumentException("invalid selector provided");
        }
    }

    /* package */ Replacer determineReplacer(ReplacerType replacerType, List<SelectorType> selectors, List<Double> selectionPercentage) {
        switch (replacerType) {
            case FULL_REPLACEMENT: return new FullReplacer(determineSelectors(selectors, selectionPercentage));
            default: throw new IllegalArgumentException("invalid replacer provided");
        }
    }

    /* package */ Crosser determineCrosser(CrosserType crosser) {
        switch (crosser) {
            case SINGLE_POINT: return new SinglePointCrosser();
            default: throw new IllegalArgumentException("invalid crosser provided");
        }
    }

    /* package */ Mutator determineMutator(MutatorType mutator, double mutationRate) {
        switch (mutator) {
            case SINGLE_GENE: return new SingleGeneMutator(mutationRate);
            case MULTI_GENE: return new MultiGeneMutator(mutationRate);
            default: throw new IllegalArgumentException("invalid mutator provided");
        }
    }
}
