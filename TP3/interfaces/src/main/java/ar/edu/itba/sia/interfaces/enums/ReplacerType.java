package ar.edu.itba.sia.interfaces.enums;


import ar.edu.itba.sia.geneticAlgorithmGps.implementations.replacers.FullReplacer;
import ar.edu.itba.sia.geneticAlgorithmGps.implementations.replacers.SecondReplacer;
import ar.edu.itba.sia.geneticAlgorithmGps.implementations.replacers.ThirdReplacer;
import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Replacer;

import javax.management.AttributeNotFoundException;

public enum ReplacerType {
    FULL_REPLACEMENT, SECOND, THIRD;

    public static ReplacerType getReplacementMethod(final String method) throws AttributeNotFoundException {
        if(method.equals(FULL_REPLACEMENT.toString())) {
            return FULL_REPLACEMENT;
        } else if(method.equals(SECOND.toString())) {
            return SECOND;
        } else if(method.equals(THIRD.toString())) {
            return THIRD;
        } else throw new AttributeNotFoundException("replacement method doesn\'t exist");
    }

    public static Replacer getReplacementAlgorithm(final ReplacerType method) {
        if(method.equals(FULL_REPLACEMENT)) {
            return new FullReplacer();
        } else if(method.equals(SECOND)) {
            return new SecondReplacer();
        } else {
            return new ThirdReplacer();
        }
    }


}
