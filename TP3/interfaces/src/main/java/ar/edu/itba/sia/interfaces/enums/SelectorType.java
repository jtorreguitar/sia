package ar.edu.itba.sia.interfaces.enums;

import ar.edu.itba.sia.geneticAlgorithmGps.implementations.selectors.EliteSelector;
import ar.edu.itba.sia.geneticAlgorithmGps.implementations.selectors.RouletteSelector;
import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Selector;

import javax.management.AttributeNotFoundException;

public enum SelectorType {
    ELITE,
    ROULETTE,
    UNIVERSAL,
    BOLTZMANN,
    TOURNAMENT,
    RANKING;

    public static SelectorType getSelectionMethod(final String method) throws AttributeNotFoundException {
        if(method.equals(ELITE.toString())) {
            return ELITE;
        } else if(method.equals(ROULETTE.toString())) {
            return ROULETTE;
        } else if(method.equals(UNIVERSAL.toString())) {
            return UNIVERSAL;
        } else if(method.equals(BOLTZMANN.toString())) {
            return BOLTZMANN;
        } else if(method.equals(TOURNAMENT.toString())) {
            return TOURNAMENT;
        } else if(method.equals(RANKING.toString())) {
            return RANKING;
        } else throw new AttributeNotFoundException("selection method doesn\'t exist");
    }

}
